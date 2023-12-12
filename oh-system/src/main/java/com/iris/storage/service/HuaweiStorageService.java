//package com.iris.storage.service;
//
//import com.obs.services.ObsClient;
//import com.iris.framework.common.exception.ServerException;
//import com.iris.storage.properties.StorageProperties;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
///**
// * 华为云存储
// *
// * @author 王小费 whx5710@qq.com
// *
// */
//public class HuaweiStorageService extends StorageService {
//
//    public HuaweiStorageService(StorageProperties properties) {
//        this.properties = properties;
//    }
//
//    @Override
//    public String upload(byte[] data, String path) {
//        return upload(new ByteArrayInputStream(data), path);
//    }
//
//    @Override
//    public String upload(InputStream inputStream, String path) {
//        ObsClient client = new ObsClient(properties.getHuawei().getAccessKey(),
//                properties.getHuawei().getSecretKey(), properties.getHuawei().getEndPoint());
//        try {
//            client.putObject(properties.getHuawei().getBucketName(), path, inputStream);
//            client.close();
//        } catch (Exception e) {
//            throw new ServerException("上传文件失败：", e);
//        }
//
//        return properties.getConfig().getDomain() + "/" + path;
//    }
//
//}
