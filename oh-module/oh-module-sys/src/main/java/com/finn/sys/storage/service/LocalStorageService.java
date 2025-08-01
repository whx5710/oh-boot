package com.finn.sys.storage.service;

import com.finn.core.exception.ServerException;
import com.finn.sys.storage.properties.StorageProperties;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 本地存储
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class LocalStorageService extends StorageService {

    public LocalStorageService(StorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }


    @Override
    public String upload(InputStream inputStream, String path) {

        try {
            File file = new File(properties.getLocal().getPath() + File.separator + path);

            // 没有目录，则自动创建目录
            File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new ServerException("目录 '" + parent + "' 创建失败");
            }

            FileCopyUtils.copy(inputStream, Files.newOutputStream(file.toPath()));
        } catch (Exception e) {
            throw new ServerException("上传文件失败", e);
        }

        return properties.getConfig().getDomain() + "/" + properties.getLocal().getUrl() + "/" + path;
    }
}
