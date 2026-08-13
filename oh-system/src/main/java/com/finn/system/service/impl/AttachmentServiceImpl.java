package com.finn.system.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.common.entity.HashDto;
import com.finn.common.entity.PageResult;
import com.finn.common.utils.AssertUtils;
import com.finn.common.utils.ExceptionUtils;
import com.finn.common.utils.NamedDaemonThreadFactory;
import com.finn.system.convert.AttachmentConvert;
import com.finn.system.entity.AttachmentEntity;
import com.finn.system.mapper.AttachmentMapper;
import com.finn.system.query.AttachmentQuery;
import com.finn.system.service.AttachmentService;
import com.finn.system.vo.AttachmentVO;
import jakarta.annotation.PostConstruct;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.finn.common.constant.Constant.ASC;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final static Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    private final AttachmentMapper attachmentMapper;
    private final RedisCache redisCache;

    @Value("${finn.storage.enabled:true}")
    private boolean storageEnabled;

    @Value("${finn.storage.cache-record:false}")
    private boolean cacheRecord;

    /**
     * 连续满批次计数，用于检测 Redis 文件队列是否拥挤
     */
    private int consecutiveFullBatchCount = 0;
    /**
     * 临时文件删除计数，用于判断是否需要查询临时文件
     */
    private int tmpFileDelCount = 3;

    /**
     * 触发队列拥挤警告的连续满批次阈值
     */
    private static final int QUEUE_CONGESTION_THRESHOLD = 5;

    /**
     * 每批次处理数量
     */
    private static final int BATCH_SIZE = 300;

    public AttachmentServiceImpl(AttachmentMapper attachmentMapper, RedisCache redisCache) {
        this.attachmentMapper = attachmentMapper;
        this.redisCache = redisCache;
    }

    @Override
    public PageResult<AttachmentVO> page(AttachmentQuery query) {
        List<AttachmentEntity> list = attachmentMapper.getList(query);
        return new PageResult<>(AttachmentConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public Long save(AttachmentVO vo) {
        AttachmentEntity entity = AttachmentConvert.INSTANCE.convert(vo);
        attachmentMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void update(AttachmentVO vo) {
        AssertUtils.isNull(vo.getId(), "ID");
        AttachmentEntity entity = AttachmentConvert.INSTANCE.convert(vo);
        attachmentMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            AttachmentEntity param = new AttachmentEntity();
            param.setId(id);
            param.setDbStatus(0);
            attachmentMapper.updateById(param);
        });
    }

    /**
     * 启动项目时，从Redis队列获取附件信息保存数据库<br/>
     * 删除48小时前的临时文件
     * fileId 文件id、url
     * name 文件名
     * size 文件大小
     * platform 存储平台
     */
    @PostConstruct
    public void saveAttachment() {
        if (!storageEnabled) {
            log.info("文件存储未启用，跳过附件定时任务");
            return;
        }
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1, new NamedDaemonThreadFactory("attachment-save"));
        // 每隔180秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            // 只有开启缓存记录时，才从 Redis 队列读取并保存文件信息
            if (cacheRecord) {
                try {
                    String key = RedisKeys.getFileCacheKey();
                    List<AttachmentEntity> list = new ArrayList<>();
                    for (int i = 0; i < BATCH_SIZE; i++) {
                        Object object = redisCache.rightPop(key);
                        if(object == null){
                            break;
                        }
                        AttachmentEntity attachment = getAttachment((HashDto) object);
                        list.add(attachment);
                    }
                    if(!list.isEmpty()){
                        attachmentMapper.insertBatch(list);
                    }
                    // 队列拥挤检测：连续达到满批次时打印警告
                    if (list.size() == BATCH_SIZE) {
                        consecutiveFullBatchCount++;
                        if (consecutiveFullBatchCount >= QUEUE_CONGESTION_THRESHOLD) {
                            log.warn("Redis 文件记录队列出现拥挤，已连续 {} 次每次插入 {} 条记录，建议检查消费速度或调整定时任务频率", consecutiveFullBatchCount, BATCH_SIZE);
                            consecutiveFullBatchCount = 0;
                        }
                    } else {
                        consecutiveFullBatchCount = 0;
                    }
                } catch (Exception e) {
                    log.error("保存文件异常：{}", ExceptionUtils.getExceptionMessage(e));
                }
            }

            // 更新已删除的文件状态(文件服务已将文件删除)
            if(redisCache.getListSize(RedisKeys.getTmpFileDelKey()) > 0){
                List<Long> ids = new ArrayList<>();
                for(int i = 0; i < 50; i++){
                    Object object = redisCache.rightPop(RedisKeys.getTmpFileDelKey());
                    if(object == null){
                        break;
                    }
                    HashDto hashDto = (HashDto) object;
                    ids.add(hashDto.getLong("id"));
                }
                if(!ids.isEmpty()){
                    attachmentMapper.updateByWrapper(UpdateWrapper.of(AttachmentEntity.class)
                            .set(AttachmentEntity::getDbStatus, 0)
                            .eq(AttachmentEntity::getDbStatus, 1).in(AttachmentEntity::getId, ids));
                }
            }

            // 缓存临时文件队列，在文件服务中读取，删除临时文件
            if(tmpFileDelCount > 0){
                if(redisCache.getListSize(RedisKeys.getTmpFileCacheKey()) == 0){
                    int pageSize = 50;
                    // 查询48小时前的临时文件
                    LocalDateTime time = LocalDateTime.now();
                    time = time.minusHours(48);
                    QueryWrapper<AttachmentEntity> queryWrapper = QueryWrapper.of(AttachmentEntity.class);
                    queryWrapper.eq(AttachmentEntity::getDbStatus, 1).eq(AttachmentEntity::getTmpFlag, 1)
                            .le(AttachmentEntity::getCreateTime, time).orderBy(AttachmentEntity::getCreateTime, ASC)
                            .page(1, pageSize);
                    List<AttachmentEntity> list = attachmentMapper.listByWrapper(queryWrapper);
                    if(list != null && !list.isEmpty()){
                        if(list.size() == pageSize){
                            tmpFileDelCount = 3;
                        }else{
                            tmpFileDelCount--;
                        }
                        for (AttachmentEntity item: list){
                            HashDto hashDto = new HashDto();
                            hashDto.put("id", item.getId());
                            hashDto.put("fileId", item.getUrl());
                            redisCache.leftPush(RedisKeys.getTmpFileCacheKey(), hashDto, 60*60*24); // 缓存24小时
                        }
                    }else{
                        tmpFileDelCount--;
                    }
                }
            }else{
                tmpFileDelCount--;
            }
            if(tmpFileDelCount < -20){
                tmpFileDelCount = 1;
            }
        }, 30, 180, TimeUnit.SECONDS);
    }

    /**
     * 组装实体类
     * @param file
     * @return
     */
    private static @NonNull AttachmentEntity getAttachment(HashDto file) {
        AttachmentEntity attachment = new AttachmentEntity();
        attachment.setUrl(file.getStr("fileId"));
        attachment.setName(file.getStr("name"));
        attachment.setSize(file.getLong("size"));
        attachment.setPlatform(file.getStr("platform"));
        Integer tmpFlag = file.getInt("tmpFlag");
        if(tmpFlag == null){
            tmpFlag = 0;
        }
        attachment.setTmpFlag(tmpFlag);
        attachment.setContentType(file.getStr("contentType"));
        attachment.setCreator(file.getLong("creator"));
        attachment.setCreateTime(file.getLocalDateTime("createTime"));
        return attachment;
    }

}