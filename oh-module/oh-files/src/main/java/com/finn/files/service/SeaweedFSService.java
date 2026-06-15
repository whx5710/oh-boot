package com.finn.files.service;

import com.finn.files.config.SeaweedFSProperties;
import com.finn.framework.exception.ServerException;
import com.finn.framework.utils.Tools;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * SeaweedFS 服务
 */
@Service
public class SeaweedFSService {

    private final S3Client s3Client;

    private final SeaweedFSProperties properties;

    public SeaweedFSService(S3Client s3Client, SeaweedFSProperties properties){
        this.s3Client = s3Client;
        this.properties = properties;
    }

    /**
     * 上传文件
     * @param file 上传的文件
     * @return 文件唯一标识 key
     */
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if(fileName == null){
                fileName = "no_file_name";
            }
            String suffix = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
            String key = Tools.generator() + suffix;
            System.out.println(key);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(properties.getBucket())
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return key;
        } catch (IOException e) {
            throw new ServerException("文件上传失败", e);
        }
    }

    /**
     * 上传字节数组
     */
    public String uploadFile(byte[] data, String originalFilename, String contentType) {
        if(originalFilename == null){
            originalFilename = "no_file_name";
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
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
     * 下载文件
     * @param key 文件 key
     * @return 文件字节数组
     */
    public byte[] downloadFile(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(key)
                .build();
        return s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
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
}
