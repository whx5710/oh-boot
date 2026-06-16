package com.finn.files.service;

import com.finn.files.config.SeaweedFSProperties;
import com.finn.files.vo.PartInfoVO;
import com.finn.files.vo.PresignedUrlVO;
import com.finn.files.vo.MultipartUploadInitVO;
import com.finn.framework.exception.ServerException;
import com.finn.framework.utils.Tools;
import org.springframework.stereotype.Service;
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
@Service
public class SeaweedFSService {

    private final S3Client s3Client;

    private final S3Presigner s3Presigner;

    private final SeaweedFSProperties properties;

    /**
     * 分片大小：10MB
     */
    private static final long PART_SIZE = 10 * 1024 * 1024;

    /**
     * 内存上传阈值：5MB，小于此值直接读内存，避免磁盘 I/O
     */
    private static final long MEMORY_UPLOAD_THRESHOLD = 5 * 1024 * 1024;

    public SeaweedFSService(S3Client s3Client, S3Presigner s3Presigner, SeaweedFSProperties properties) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.properties = properties;
    }

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 文件唯一标识 key
     */
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                fileName = "no_file_name";
            }
            String suffix = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
            if (whitelistVerification(suffix)) {
                throw new ServerException("文件不合法");
            }
            String key = Tools.generator() + suffix;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(properties.getBucket())
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            // 小文件直接读内存；大文件使用 ContentStreamProvider，
            // 利用 Spring 已创建的临时文件流，避免二次磁盘写
            if (file.getSize() <= MEMORY_UPLOAD_THRESHOLD) {
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
                        file.getSize(),
                        Objects.requireNonNull(file.getContentType())
                ));
            }
            return key;
        } catch (IOException e) {
            throw new ServerException("文件上传失败", e);
        }
    }

    /**
     * 上传字节数组
     */
    public String uploadFile(byte[] data, String originalFilename, String contentType) {
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
        return key;
    }

    /**
     * 下载文件（字节数组，适合小文件）
     *
     * @param key 文件 key
     * @return 文件字节数组
     */
    public byte[] downloadFile(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();
        try {
            return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
        } catch (NoSuchKeyException e) {
            throw new ServerException("文件不存在");
        }
    }

    /**
     * 获取文件输入流（适合大文件）
     */
    public InputStream getFileInputStream(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();
        return s3Client.getObject(getObjectRequest);
    }

    /**
     * 流式下载文件到输出流（支持 Range 断点续传）
     *
     * @param key        文件 key
     * @param outputStream 输出流
     * @param rangeStart 起始字节（-1 表示从头开始）
     * @param rangeEnd   结束字节（-1 表示到文件末尾）
     */
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

    /**
     * 获取文件元数据
     */
    public HeadObjectResponse getFileMetadata(String key) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();
        try {
            return s3Client.headObject(headObjectRequest);
        } catch (NoSuchKeyException e) {
            throw new ServerException("文件不存在");
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    /**
     * 判断文件是否存在
     */
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
