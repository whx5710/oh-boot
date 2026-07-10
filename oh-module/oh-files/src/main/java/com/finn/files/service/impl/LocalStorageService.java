package com.finn.files.service.impl;

import com.finn.files.config.StorageProperties;
import com.finn.files.service.StorageService;
import com.finn.files.utils.MediaTypeUtils;
import com.finn.files.vo.FileMetadata;
import com.finn.framework.exception.ServerException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 本地存储
 *
 * @author 王小费 whx5710@qq.com
 */
public class LocalStorageService extends StorageService {

    public LocalStorageService(StorageProperties properties) {
        super(properties);
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
            String path = getPath(fileName);
            upload(file.getInputStream(), path);
            // 缓存文件信息
            cacheFile(path, fileName, file.getSize(), file.getContentType(), "Local", isTmp);
            return path;
        } catch (IOException e) {
            throw new ServerException("上传文件失败", e);
        }
    }

    @Override
    public String upload(byte[] data, String originalFilename, String contentType) {
        if (originalFilename == null) {
            originalFilename = "no_file_name";
        }
        String path = getPath(originalFilename);
        upload(data, path);
        cacheFile(path, originalFilename, data.length, contentType, "Local", false);
        return path;
    }

    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    public String upload(InputStream inputStream, String path) {
        try {
            Path file = resolveFile(path);

            // 没有目录，则自动创建目录
            Path parent = file.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            FileCopyUtils.copy(inputStream, Files.newOutputStream(file));
        } catch (Exception e) {
            throw new ServerException("上传文件失败", e);
        }
        return path;
    }

    @Override
    public void streamFile(String key, OutputStream outputStream, long rangeStart, long rangeEnd) {
        Path file = resolveFile(key);
        if (!Files.exists(file)) {
            throw new ServerException("文件不存在");
        }
        long fileSize;
        try {
            fileSize = Files.size(file);
        } catch (IOException e) {
            throw new ServerException("文件读取失败", e);
        }
        long start = rangeStart >= 0 ? rangeStart : 0;
        long end = rangeEnd >= 0 ? Math.min(rangeEnd, fileSize - 1) : fileSize - 1;
        if (start > end || start >= fileSize) {
            start = 0;
            end = fileSize - 1;
        }
        long length = end - start + 1;

        try (InputStream inputStream = Files.newInputStream(file)) {
            // 跳过起始位置
            if (start > 0) {
                long skipped = inputStream.skip(start);
                if (skipped < start) {
                    throw new ServerException("文件读取失败");
                }
            }
            byte[] buffer = new byte[65536];
            long remaining = length;
            int len;
            while (remaining > 0 && (len = inputStream.read(buffer, 0, (int) Math.min(buffer.length, remaining))) != -1) {
                outputStream.write(buffer, 0, len);
                remaining -= len;
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new ServerException("文件下载失败", e);
        }
    }

    @Override
    public FileMetadata getMetadata(String key) {
        Path file = resolveFile(key);
        if (!Files.exists(file)) {
            throw new ServerException("文件不存在");
        }
        try {
            FileMetadata metadata = new FileMetadata();
            metadata.setContentLength(Files.size(file));
            String fileName = file.getFileName().toString();
            metadata.setContentType(MediaTypeUtils.getMimeType(fileName));
            metadata.setFilename(fileName);
            return metadata;
        } catch (IOException e) {
            throw new ServerException("文件读取失败", e);
        }
    }

    @Override
    public void delete(String key) {
        Path file = resolveFile(key);
        try {
            if (Files.exists(file)) {
                Files.delete(file);
            }
        } catch (IOException e) {
            throw new ServerException("文件删除失败", e);
        }
    }

    @Override
    public boolean exists(String key) {
        return Files.exists(resolveFile(key));
    }

    /**
     * 解析 key 为本地文件路径
     *
     * @param key 文件 key
     * @return 本地文件路径
     */
    private Path resolveFile(String key) {
        String basePath = properties.getLocal().getPath();
        if (key.startsWith("/")) {
            key = key.substring(1);
        }
        // 如果 key 包含本地 url 前缀，则去除
        String urlPrefix = properties.getLocal().getUrl();
        if (urlPrefix != null && !urlPrefix.isEmpty() && key.startsWith(urlPrefix + "/")) {
            key = key.substring(urlPrefix.length() + 1);
        }
        return Path.of(basePath, key).normalize();
    }
}
