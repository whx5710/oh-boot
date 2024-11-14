package com.iris.sys.storage.service;//package com.iris.storage.service;
//
//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import com.iris.core.common.exception.ServerException;
//import com.iris.storage.properties.StorageProperties;
//import org.springframework.http.MediaType;
//import org.springframework.http.MediaTypeFactory;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.Optional;
//
///**
// * Minio存储
// *
// * @author 王小费 whx5710@qq.com
// *
// */
//public class MinioStorageService extends StorageService {
//    private final MinioClient minioClient;
//
//    public MinioStorageService(StorageProperties properties) {
//        this.properties = properties;
//
//        minioClient = MinioClient.builder().endpoint(properties.getMinio().getEndPoint())
//                .credentials(properties.getMinio().getAccessKey(), properties.getMinio().getSecretKey()).build();
//    }
//
//    @Override
//    public String upload(byte[] data, String path) {
//        return upload(new ByteArrayInputStream(data), path);
//    }
//
//    @Override
//    public String upload(InputStream inputStream, String path) {
//        try {
//            //如果BucketName不存在，则创建
//            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getMinio().getBucketName()).build());
//            if (!found) {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getMinio().getBucketName()).build());
//            }
//
//            String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(path);
//            if (mediaType.isPresent()) {
//                contentType = mediaType.get().toString();
//            }
//
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(properties.getMinio().getBucketName())
//                            .contentType(contentType)
//                            .object(path)
//                            .stream(inputStream, inputStream.available(), -1)
//                            .build()
//            );
//
//        } catch (Exception e) {
//            throw new ServerException("上传文件失败：", e);
//        }
//
//        return properties.getMinio().getEndPoint() + "/" + properties.getMinio().getBucketName() + "/" + path;
//    }
//}
