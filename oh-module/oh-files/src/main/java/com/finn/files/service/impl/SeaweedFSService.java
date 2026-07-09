package com.finn.files.service.impl;

import com.finn.files.config.SeaweedFSProperties;
import com.finn.files.config.StorageProperties;
import com.finn.files.service.StorageService;
import com.finn.files.utils.MediaTypeUtils;
import com.finn.files.vo.FileMetadata;
import com.finn.files.vo.PartInfoVO;
import com.finn.files.vo.PresignedUrlVO;
import com.finn.files.vo.MultipartUploadInitVO;
import com.finn.framework.cache.RedisCache;
import com.finn.framework.exception.ServerException;
import com.finn.framework.utils.Tools;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SeaweedFS 服务
 * windows启动： weed.exe server -dir=D:\data\seaweedfs\data -s3 -s3.config=D:\data\seaweedfs\s3-config.json
 * {
 *   "identities": [
 *     {
 *       "name": "admin",
 *       "credentials": [
 *         {
 *           "accessKey": "admin",
 *           "secretKey": "admin123"
 *         }
 *       ],
 *       "actions": [
 *         "Admin",
 *         "Read",
 *         "Write"
 *       ]
 *     }
 *   ]
 * }
 */
public class SeaweedFSService extends StorageService {

    private final S3Client s3Client;

    private final S3Presigner s3Presigner;

    private final SeaweedFSProperties properties;

    /**
     * 内存上传阈值：5MB，小于此值直接读内存，避免磁盘 I/O
     */
    private static final long MEMORY_UPLOAD_THRESHOLD = 5 * 1024 * 1024;

    public SeaweedFSService(S3Client s3Client, S3Presigner s3Presigner, SeaweedFSProperties properties,
                            StorageProperties storageProperties, RedisCache redisCache) {
        super(storageProperties);
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.properties = properties;
        this.redisCache = redisCache;
    }

    @Override
    public String upload(MultipartFile file, Boolean isTmp) {
        if (isTmp == null) {
            isTmp = false;
        }
        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                fileName = "no_file_name";
            }
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            if (whitelistVerification(suffix)) {
                throw new ServerException(suffix + " 文件不合法");
            }
            String key = Tools.generator() + suffix;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(properties.getBucket())
                    .key(key)
                    .contentType(file.getContentType())
                    .build();
            long fileSize = file.getSize();
            // 小文件直接读内存；大文件使用 ContentStreamProvider，
            // 利用 Spring 已创建的临时文件流，避免二次磁盘写
            if (fileSize <= MEMORY_UPLOAD_THRESHOLD) {
                s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            } else {
                s3Client.putObject(putObjectRequest, RequestBody.fromContentProvider(
                        () -> {
                            try {
                                return file.getInputStream();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        fileSize,
                        Objects.requireNonNull(file.getContentType())
                ));
            }
            // 缓存文件信息
            cacheFile(key, fileName, fileSize, file.getContentType(), "SeaweedFS", isTmp);
            return key;
        } catch (IOException e) {
            throw new ServerException("文件上传失败", e);
        }
    }

    @Override
    public String upload(byte[] data, String originalFilename, String contentType) {
        if (originalFilename == null) {
            originalFilename = "no_file_name";
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (whitelistVerification(suffix)) {
            throw new ServerException("文件不合法");
        }
        String key = Tools.generator() + suffix;
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .contentType(contentType)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(data));
        cacheFile(key, originalFilename, data.length, contentType, "SeaweedFS", false);
        return key;
    }

    public String upload(byte[] data, String path) {
        return upload(data, path, null);
    }

    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = inputStream.readAllBytes();
            return upload(data, path);
        } catch (IOException e) {
            throw new ServerException("文件上传失败", e);
        }
    }

    @Override
    public void streamFile(String key, OutputStream outputStream, long rangeStart, long rangeEnd) {
        GetObjectRequest.Builder builder = GetObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key);
        if (rangeStart >= 0 || rangeEnd >= 0) {
            String range = "bytes=" + (rangeStart >= 0 ? rangeStart : "") + "-" + (rangeEnd >= 0 ? rangeEnd : "");
            builder.range(range);
        }
        try (InputStream inputStream = s3Client.getObject(builder.build())) {
            byte[] buffer = new byte[65536];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (NoSuchKeyException e) {
            throw new ServerException("文件不存在");
        } catch (IOException e) {
            throw new ServerException("文件下载失败", e);
        }
    }

    @Override
    public FileMetadata getMetadata(String key) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();
        try {
            HeadObjectResponse response = s3Client.headObject(headObjectRequest);
            FileMetadata metadata = new FileMetadata();
            metadata.setContentLength(response.contentLength());
            String contentType = response.contentType();
            if (contentType == null || contentType.isEmpty()) {
                contentType = MediaTypeUtils.getMimeType(key);
            }
            metadata.setContentType(contentType);
            metadata.setFilename(key);
            return metadata;
        } catch (NoSuchKeyException e) {
            throw new ServerException("文件不存在");
        }
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    @Override
    public boolean exists(String key) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(properties.getBucket())
                    .key(key)
                    .build();
            s3Client.headObject(headObjectRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            }
            throw e;
        }
    }

    // ===================== 预签名 URL =====================

    /**
     * 生成预签名上传 URL
     *
     * @param key         文件 key（为空则自动生成）
     * @param contentType 文件类型
     * @param duration    有效期
     * @return 预签名 URL 信息
     */
    @Override
    public PresignedUrlVO generatePresignedUploadUrl(String key, String contentType, Duration duration) {
        if (key == null || key.isEmpty()) {
            key = Tools.generator();
        }
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .contentType(contentType)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(req -> req
                .signatureDuration(duration)
                .putObjectRequest(putObjectRequest)
        );

        PresignedUrlVO vo = new PresignedUrlVO();
        vo.setUrl(presignedRequest.url().toString());
        vo.setKey(key);
        vo.setExpirationSeconds(duration.getSeconds());
        return vo;
    }

    /**
     * 生成预签名下载 URL
     *
     * @param key      文件 key
     * @param duration 有效期
     * @return 预签名 URL 信息
     */
    @Override
    public PresignedUrlVO generatePresignedDownloadUrl(String key, Duration duration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(req -> req
                .signatureDuration(duration)
                .getObjectRequest(getObjectRequest)
        );

        PresignedUrlVO vo = new PresignedUrlVO();
        vo.setUrl(presignedRequest.url().toString());
        vo.setKey(key);
        vo.setExpirationSeconds(duration.getSeconds());
        return vo;
    }

    // ===================== 分片上传 =====================

    /**
     * 初始化分片上传
     *
     * @param key         文件 key（为空则自动生成）
     * @param contentType 文件类型
     * @return 分片上传初始化信息
     */
    @Override
    public MultipartUploadInitVO initiateMultipartUpload(String key, String contentType) {
        if (key == null || key.isEmpty()) {
            key = Tools.generator();
        }
        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .contentType(contentType)
                .build();
        CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createRequest);

        MultipartUploadInitVO vo = new MultipartUploadInitVO();
        vo.setUploadId(response.uploadId());
        vo.setKey(key);
        return vo;
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
    @Override
    public String uploadPart(String key, String uploadId, int partNumber, MultipartFile file) {
        try {
            UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                    .bucket(properties.getBucket())
                    .key(key)
                    .uploadId(uploadId)
                    .partNumber(partNumber)
                    .contentLength(file.getSize())
                    .build();
            // 分片大小可控（通常 5~10MB），直接读内存避免磁盘 I/O
            UploadPartResponse response = s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(file.getBytes()));
            return response.eTag();
        } catch (IOException e) {
            throw new ServerException("分片上传失败", e);
        }
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
    @Override
    public PresignedUrlVO generatePresignedUploadPartUrl(String key, String uploadId, int partNumber, Duration duration) {
        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .uploadId(uploadId)
                .partNumber(partNumber)
                .build();

        PresignedUploadPartRequest presignedRequest = s3Presigner.presignUploadPart(req -> req
                .signatureDuration(duration)
                .uploadPartRequest(uploadPartRequest)
        );

        PresignedUrlVO vo = new PresignedUrlVO();
        vo.setUrl(presignedRequest.url().toString());
        vo.setKey(key);
        vo.setExpirationSeconds(duration.getSeconds());
        return vo;
    }

    /**
     * 完成分片上传
     *
     * @param key      文件 key
     * @param uploadId 上传 ID
     * @param parts    分片信息列表
     */
    @Override
    public void completeMultipartUpload(String key, String uploadId, List<PartInfoVO> parts) {
        List<CompletedPart> completedParts = new ArrayList<>();
        for (PartInfoVO part : parts) {
            completedParts.add(CompletedPart.builder()
                    .partNumber(part.getPartNumber())
                    .eTag(part.getEtag())
                    .build());
        }
        CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .uploadId(uploadId)
                .multipartUpload(CompletedMultipartUpload.builder().parts(completedParts).build())
                .build();
        s3Client.completeMultipartUpload(completeRequest);
    }

    /**
     * 取消分片上传
     *
     * @param key      文件 key
     * @param uploadId 上传 ID
     */
    @Override
    public void abortMultipartUpload(String key, String uploadId) {
        AbortMultipartUploadRequest abortRequest = AbortMultipartUploadRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .uploadId(uploadId)
                .build();
        s3Client.abortMultipartUpload(abortRequest);
    }

    /**
     * 文件合法性验证,非法返回true
     *
     * @return bool 返回false表示满足上传条件
     */
    private boolean whitelistVerification(String suffix) {
        if (properties.getFileSuffix() == null || properties.getFileSuffix().isEmpty()) {
            return false;
        }
        boolean flag = true;
        for (String item : properties.getFileSuffix()) {
            if (suffix.equalsIgnoreCase(item)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
