package com.iris.sys.base.api;

import com.iris.sys.storage.service.StorageService;
import com.iris.sys.base.service.StorageApi;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * 存储服务Api
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class StorageApiImpl implements StorageApi {

    private final StorageService storageService;

    public StorageApiImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public String getNewFileName(String fileName) {
        return storageService.getNewFileName(fileName);
    }

    @Override
    public String getPath() {
        return storageService.getPath();
    }

    @Override
    public String getPath(String fileName) {
        return storageService.getPath(fileName);
    }

    @Override
    public String upload(byte[] data, String path) {
        return storageService.upload(data, path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        return storageService.upload(inputStream, path);
    }
}
