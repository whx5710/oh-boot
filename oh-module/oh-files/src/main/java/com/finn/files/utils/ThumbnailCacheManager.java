package com.finn.files.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 缩略图磁盘缓存管理器
 * - 缓存位置：系统临时目录 /file-thumb-cache/
 * - 缓存键：MD5(fileKey + "|" + width + "|" + quality + "|" + shape)
 * - 过期策略：默认 7 天未访问自动清理（低优先级后台线程扫描）
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-26
 */
public class ThumbnailCacheManager {

    private static final Logger log = LoggerFactory.getLogger(ThumbnailCacheManager.class);

    private static final String CACHE_DIR_NAME = "file-thumb-cache";
    private static final Duration DEFAULT_TTL = Duration.ofDays(7);
    private static final Duration CLEAN_INTERVAL = Duration.ofHours(6);

    private static final ScheduledExecutorService CLEANER = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "thumb-cache-cleaner");
        t.setDaemon(true);
        return t;
    });

    private static volatile Path cacheDir;

    private ThumbnailCacheManager() {}

    public static void init() {
        if (cacheDir != null) return;
        synchronized (ThumbnailCacheManager.class) {
            if (cacheDir != null) return;
            try {
                Path tmp = Paths.get(System.getProperty("java.io.tmpdir"));
                cacheDir = tmp.resolve(CACHE_DIR_NAME);
                if (!Files.exists(cacheDir)) {
                    Files.createDirectories(cacheDir);
                }
                log.info("[ThumbnailCache] 初始化完成，缓存目录: {}", cacheDir.toAbsolutePath());
                // 启动定期清理线程
                CLEANER.scheduleAtFixedRate(ThumbnailCacheManager::cleanExpired,
                        CLEAN_INTERVAL.toMinutes(),
                        CLEAN_INTERVAL.toMinutes(),
                        TimeUnit.MINUTES);
            } catch (IOException e) {
                throw new IllegalStateException("初始化缩略图缓存目录失败", e);
            }
        }
    }

    /**
     * 读取缓存
     * @return 缓存文件字节，未命中返回 null
     */
    public static CachedResult get(String fileKey, int width, int quality, String shape) {
        init();
        Path cacheFile = toCachePath(fileKey, width, quality, shape);
        if (!Files.exists(cacheFile)) {
            return null;
        }
        try {
            // 读取前更新最后访问时间（通过 setLastModified 模拟）
            try {
                Files.setLastModifiedTime(cacheFile, FileTime.from(Instant.now()));
            } catch (Exception ignored) { /* 不影响主流程 */ }
            byte[] bytes = Files.readAllBytes(cacheFile);
            String contentType = null;
            String name = cacheFile.getFileName().toString();
            if (name.endsWith(".png")) contentType = "image/png";
            else if (name.endsWith(".gif")) contentType = "image/gif";
            else contentType = "image/jpeg";
            return new CachedResult(bytes, contentType);
        } catch (IOException e) {
            log.warn("[ThumbnailCache] 读取缓存失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 写入缓存，异常不会抛出（仅记录日志，不影响主流程）
     */
    public static void put(String fileKey, int width, int quality, String shape,
                           byte[] bytes, String formatName) {
        init();
        Path cacheFile = toCachePathRaw(fileKey, width, quality, shape, formatName);
        try {
            Files.write(cacheFile, bytes);
        } catch (IOException e) {
            log.warn("[ThumbnailCache] 写入缓存失败: {}", e.getMessage());
        }
    }

    /**
     * 清理过期缓存文件
     */
    public static void cleanExpired() {
        if (cacheDir == null) return;
        try {
            Instant now = Instant.now();
            Instant expireAt = now.minus(DEFAULT_TTL);
            long deleted = 0;
            long total = 0;
            try (var stream = Files.list(cacheDir)) {
                var iter = stream.iterator();
                while (iter.hasNext()) {
                    Path p = iter.next();
                    try {
                        total++;
                        FileTime ft = Files.getLastModifiedTime(p);
                        if (ft.toInstant().isBefore(expireAt)) {
                            Files.deleteIfExists(p);
                            deleted++;
                        }
                    } catch (Exception ignore) { /* 单个文件失败不影响整体 */ }
                }
            }
            if (deleted > 0 || total > 0) {
                log.info("[ThumbnailCache] 清理完成: 清理 {} / 共 {} 个缓存文件", deleted, total);
            }
        } catch (IOException e) {
            log.warn("[ThumbnailCache] 清理过期缓存异常: {}", e.getMessage());
        }
    }

    // ===================== 内部辅助 =====================

    private static Path toCachePath(String fileKey, int width, int quality, String shape) {
        // 无扩展名的路径，需要在 put 时结合 formatName 才知道；此处仅为 get 用
        // 做法：先尝试 .png（圆形一定是 png），再试 .jpg
        Path png = toCachePathRaw(fileKey, width, quality, shape, "png");
        if (Files.exists(png)) return png;
        return toCachePathRaw(fileKey, width, quality, shape, "jpg");
    }

    private static Path toCachePathRaw(String fileKey, int width, int quality, String shape, String formatName) {
        String sig = (shape==null? "" : shape.toLowerCase());
        String raw = fileKey + "|" + width + "|" + quality + "|" + sig;
        String hash = md5(raw);
        String fileName = hash + "." + ("png".equalsIgnoreCase(formatName) ? "png" : "jpg");
        return cacheDir.resolve(fileName);
    }

    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return String.format("%032x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException e) {
            // JDK 一定有 MD5，退化为 hashCode
            return Integer.toHexString(input.hashCode());
        }
    }

    public static class CachedResult {
        private final byte[] bytes;
        private final String contentType;
        public CachedResult(byte[] bytes, String contentType) {
            this.bytes = bytes;
            this.contentType = contentType;
        }
        public byte[] getBytes() { return bytes; }
        public String getContentType() { return contentType; }
    }
}
