package com.finn.sys.base.api;

import com.finn.sys.base.service.StorageApi;
import com.finn.sys.storage.service.StorageService;
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
