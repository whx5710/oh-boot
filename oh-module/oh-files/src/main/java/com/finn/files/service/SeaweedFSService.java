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
 * # 1. 启动 Master
 * weed master -mdir=/data/master
 *
 * # 2. 启动 Volume
 * weed volume -dir=/data/volume -max=7 -mserver=localhost:9333
 *
 * # 3. 启动 Filer（可选，S3 网关不依赖 filer）
 * weed filer -master=localhost:9333
 *
 * # 4. 启动 S3 网关（关键）
 * weed s3 -port=8333
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
            if(whitelistVerification(suffix)){
                throw new ServerException("文件不合法");
            }
            String key = Tools.generator() + suffix;
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
        if(whitelistVerification(suffix)){
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

    /**
     * 文件合法性验证,非法返回true
     * @return bool 返回false表示满足上传条件
     */
    private boolean whitelistVerification(String suffix){
        if(properties.getFileSuffix() == null || properties.getFileSuffix().isEmpty()){
            return false;
        }
        boolean flag = true;
        for(String item : properties.getFileSuffix()){
            if(suffix.equalsIgnoreCase(item)){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
