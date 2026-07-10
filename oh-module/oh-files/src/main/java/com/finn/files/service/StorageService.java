package com.finn.files.service;

import com.finn.files.config.StorageProperties;
import com.finn.files.vo.FileMetadata;
import com.finn.files.vo.MultipartUploadInitVO;
import com.finn.files.vo.PartInfoVO;
import com.finn.files.vo.PresignedUrlVO;
import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.framework.entity.HashDto;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.utils.DateUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 存储服务抽象类
 *
 * @author 王小费 whx5710@qq.com
 */
public abstract class StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    protected StorageProperties properties;

    protected RedisCache redisCache;

    public StorageService(StorageProperties properties) {
        this.properties = properties;
    }

    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 根据文件名，生成带时间戳的新文件名
     *
     * @param fileName 文件名
     * @return 返回带时间戳的文件名
     */
    public String getNewFileName(String fileName) {
        // 把当天HH:mm:ss，转换成秒
        Date date = new Date();
        String today = DateUtils.format(date, DateUtils.DATE_PATTERN) + " 00:00:00";
        Date d = DateUtils.parse(today, DateUtils.DATE_TIME_PATTERN);
        long time = (date.getTime() - d.getTime()) / 1000;

        int i = fileName.lastIndexOf(".");
        if (i > 0) {
            // 主文件名，不包含扩展名
            String prefix = fileName.substring(0, i);
            // 文件扩展名
            String suffix = fileName.substring(i);
            // 新文件名
            return prefix + "_" + time + suffix;
        } else {
            return time + "_" + fileName;
        }
    }

    /**
     * 生成路径，不包含文件名
     *
     * @return 返回生成的路径
     */
    public String getPath() {
        // 文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd");
        // 如果有前缀，则也带上
        if (properties.getLocal() != null && StringUtils.hasText(properties.getLocal().getUrl())) {
            path = properties.getLocal().getUrl() + "/" + path;
        }
        return path;
    }

    /**
     * 根据文件名，生成路径
     *
     * @param fileName 文件名
     * @return 生成文件路径
     */
    public String getPath(String fileName) {
        return getPath() + "/" + getNewFileName(fileName);
    }

    /**
     * 文件上传
     *
     * @param file  上传的文件
     * @param isTmp 是否临时文件，临时文件可删除
     * @return 返回文件标识 key
     */
    public abstract String upload(MultipartFile file, Boolean isTmp);

    /**
     * 文件上传
     *
     * @param data             文件字节数组
     * @param originalFilename 原始文件名
     * @param contentType      文件类型
     * @return 返回文件标识 key
     */
    public abstract String upload(byte[] data, String originalFilename, String contentType);

    /**
     * 流式下载文件到输出流（支持 Range 断点续传）
     *
     * @param key          文件 key
     * @param outputStream 输出流
     * @param rangeStart   起始字节（-1 表示从头开始）
     * @param rangeEnd     结束字节（-1 表示到文件末尾）
     */
    public abstract void streamFile(String key, OutputStream outputStream, long rangeStart, long rangeEnd);

    /**
     * 获取文件元数据
     *
     * @param key 文件 key
     * @return 文件元数据
     */
    public abstract FileMetadata getMetadata(String key);

    /**
     * 删除文件
     *
     * @param key 文件 key
     */
    public abstract void delete(String key);

    /**
     * 判断文件是否存在
     *
     * @param key 文件 key
     * @return 是否存在
     */
    public abstract boolean exists(String key);

    // ===================== 预签名 URL（默认不支持） =====================

    /**
     * 生成预签名上传 URL
     *
     * @param key         文件 key（为空则自动生成）
     * @param contentType 文件类型
     * @param duration    有效期
     * @return 预签名 URL 信息
     */
    public PresignedUrlVO generatePresignedUploadUrl(String key, String contentType, Duration duration) {
        throw new UnsupportedOperationException("当前存储类型不支持预签名上传 URL");
    }

    /**
     * 生成预签名下载 URL
     *
     * @param key      文件 key
     * @param duration 有效期
     * @return 预签名 URL 信息
     */
    public PresignedUrlVO generatePresignedDownloadUrl(String key, Duration duration) {
        throw new UnsupportedOperationException("当前存储类型不支持预签名下载 URL");
    }

    // ===================== 分片上传（默认不支持） =====================

    /**
     * 初始化分片上传
     *
     * @param key         文件 key（可选）
     * @param contentType 文件类型
     * @return 分片上传初始化信息
     */
    public MultipartUploadInitVO initiateMultipartUpload(String key, String contentType) {
        throw new UnsupportedOperationException("当前存储类型不支持分片上传");
    }

    /**
     * 上传分片（服务端代理）
     *
     * @param key        文件 key
     * @param uploadId   上传 ID
     * @param partNumber 分片编号（从 1 开始）
     * @param file       分片文件
     * @return 分片 ETag
     */
    public String uploadPart(String key, String uploadId, int partNumber, MultipartFile file) {
        throw new UnsupportedOperationException("当前存储类型不支持分片上传");
    }

    /**
     * 生成预签名分片上传 URL（前端直传）
     *
     * @param key        文件 key
     * @param uploadId   上传 ID
     * @param partNumber 分片编号
     * @param duration   有效期
     * @return 预签名 URL 信息
     */
    public PresignedUrlVO generatePresignedUploadPartUrl(String key, String uploadId, int partNumber, Duration duration) {
        throw new UnsupportedOperationException("当前存储类型不支持预签名分片上传 URL");
    }

    /**
     * 完成分片上传
     *
     * @param key      文件 key
     * @param uploadId 上传 ID
     * @param parts    分片信息列表
     */
    public void completeMultipartUpload(String key, String uploadId, List<PartInfoVO> parts) {
        throw new UnsupportedOperationException("当前存储类型不支持分片上传");
    }

    /**
     * 取消分片上传
     *
     * @param key      文件 key
     * @param uploadId 上传 ID
     */
    public void abortMultipartUpload(String key, String uploadId) {
        throw new UnsupportedOperationException("当前存储类型不支持分片上传");
    }

    /**
     * 缓存文件,在oh-system中的AttachmentService保存文件信息
     *
     * @param fileId      文件id、url
     * @param fileName    文件名
     * @param fileSize    文件大小
     * @param contentType 文件类型
     * @param platform    存储平台
     * @param isTmp       是否临时文件，临时文件可以定期删除
     */
    protected void cacheFile(String fileId, String fileName, long fileSize, String contentType,
                             String platform, Boolean isTmp) {
        if (properties.isCacheRecord() && redisCache != null) {
            HashDto hashDto = new HashDto();
            hashDto.put("fileId", fileId);
            hashDto.put("name", fileName);
            hashDto.put("size", fileSize);
            hashDto.put("contentType", contentType);
            hashDto.put("platform", platform);
            hashDto.put("tmpFlag", isTmp != null && isTmp ? 1 : 0);
            hashDto.put("createTime", LocalDateTime.now());
            if (SecurityUser.getUserId() != null) {
                hashDto.put("creator", SecurityUser.getUserId());
            }
            String key = RedisKeys.getFileCacheKey();
            // 保存到Redis队列,存5天
            redisCache.leftPush(key, hashDto, 432000); // 60*60*24*5
        }
    }

    /**
     * 删除临时文件定时任务
     */
    @PostConstruct
    public void initTmpFileCleanup() {
        if (redisCache == null) {
            return;
        }
        ScheduledThreadPoolExecutor scheduledService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "tmp-file-cleanup-" + counter.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            }
        });
        // 每隔180秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                if (redisCache.getListSize(RedisKeys.getTmpFileCacheKey()) > 0) {
                    for (int i = 0; i < 50; i++) {
                        Object object = redisCache.rightPop(RedisKeys.getTmpFileCacheKey());
                        if (object == null) {
                            break;
                        }
                        try {
                            HashDto hashDto = (HashDto) object;
                            String fileId = hashDto.getStr("fileId");
                            // 删除文件
                            if (fileId != null) {
                                if (this.exists(fileId)) {
                                    this.delete(fileId);
                                }
                                redisCache.leftPush(RedisKeys.getTmpFileDelKey(), hashDto);
                            }
                        } catch (Exception e) {
                            // 单个文件处理失败不应中断本次任务，也不应阻止后续定时调度
                            log.warn("临时文件清理单项处理失败：{}", e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("临时文件清理定时任务异常：{}", e.getMessage(), e);
            }
        }, 40, 180, TimeUnit.SECONDS);
    }
}
