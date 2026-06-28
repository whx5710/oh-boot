package com.finn.system.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.framework.entity.HashDto;
import com.finn.framework.entity.PageResult;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.utils.ExceptionUtils;
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

import static com.finn.framework.common.constant.Constant.ASC;

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

    @Value("${seaweedfs.s3.enabled:true}")
    private boolean seaweedfsEnabled;

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
        if (!seaweedfsEnabled) {
            log.info("SeaweedFS 未启用，跳过附件定时任务");
            return;
        }
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1);
        // 每隔150秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                String key = RedisKeys.getFileCacheKey();
                // 每次插入200条
                int count = 200;
                List<AttachmentEntity> list = new ArrayList<>();
                for (int i = 0; i < count; i++) {
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
            } catch (Exception e) {
                log.error("保存文件异常：{}", ExceptionUtils.getExceptionMessage(e));
            }

            // 缓存临时文件队列，在文件服务中读取，删除临时文件
            if(redisCache.getListSize(RedisKeys.getTmpFileCacheKey()) == 0){
                // 查询48小时前的临时文件
                LocalDateTime time = LocalDateTime.now();
                time = time.minusHours(48);
                QueryWrapper<AttachmentEntity> queryWrapper = QueryWrapper.of(AttachmentEntity.class);
                queryWrapper.eq(AttachmentEntity::getDbStatus, 1).eq(AttachmentEntity::getTmpFlag, 1)
                        .le(AttachmentEntity::getCreateTime, time).orderBy(AttachmentEntity::getCreateTime, ASC)
                        .page(1, 10);
                List<AttachmentEntity> list = attachmentMapper.listByWrapper(queryWrapper);
                if(list != null && !list.isEmpty()){
                    for (AttachmentEntity item: list){
                        HashDto hashDto = new HashDto();
                        hashDto.put("id", item.getId());
                        hashDto.put("url", item.getUrl());
                        redisCache.leftPush(RedisKeys.getTmpFileCacheKey(), hashDto, 60*60*24); // 缓存24小时
                    }
                }
            }

            // 更新已删除的文件状态(文件服务已将文件删除)
            if(redisCache.getListSize(RedisKeys.getTmpFileDelKey()) > 0){
                List<Long> ids = new ArrayList<>();
                for(int i = 0; i < 10; i++){
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
        }, 10, 180, TimeUnit.SECONDS);
    }

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
        attachment.setContentType(file.getStr("contentType"));
        attachment.setTmpFlag(tmpFlag);
        attachment.setCreator(file.getLong("creator"));
        return attachment;
    }

}