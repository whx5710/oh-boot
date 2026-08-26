package com.finn.files.controller;

import com.finn.common.entity.Result;
import com.finn.files.service.StorageService;
import com.finn.files.service.impl.SeaweedFSService;
import com.finn.files.utils.MediaTypeUtils;
import com.finn.files.utils.ThumbnailCacheManager;
import com.finn.files.utils.ThumbnailUtils;
import com.finn.files.vo.CompleteMultipartRequest;
import com.finn.files.vo.FileMetadata;
import com.finn.files.vo.MultipartUploadInitVO;
import com.finn.files.vo.PartInfoVO;
import com.finn.files.vo.PresignedUrlVO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * 文件上传
 *
 * @author 王小费 whx5710@qq.com
 */
@RestController
@RequestMapping("/file")
@ConditionalOnProperty(prefix = "finn.storage", value = "enabled", havingValue = "true")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private final StorageService storageService;

    private final ObjectProvider<SeaweedFSService> seaweedFSServiceProvider;

    /**
     * 默认最大文件大小：500MB
     */
    private static final long MAX_FILE_SIZE = 500 * 1024 * 1024;

    public FileController(StorageService storageService, ObjectProvider<SeaweedFSService> seaweedFSServiceProvider) {
        this.storageService = storageService;
        this.seaweedFSServiceProvider = seaweedFSServiceProvider;
    }

    /**
     * 上传（使用流式上传，支持大文件）
     *
     * @param file  文件
     * @param isTmp 是否临时文件，临时文件可删除
     * @return 结果
     * @throws Exception 异常
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file,
                                 @RequestParam(name = "isTmp", required = false) Boolean isTmp) throws Exception {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的文件");
        }
        // 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            return Result.error("文件大小超过限制，最大支持 " + (MAX_FILE_SIZE / 1024 / 1024) + "MB");
        }
        return Result.ok(storageService.upload(file, isTmp));
    }

    /**
     * 下载文件（支持断点续传、流式下载）
     * key 可能包含 / 路径分隔符（如 upload/20260822/xxx.png），
     * 使用 /** 通配符匹配，从 URI 中提取 key
     */
    @GetMapping("/download/**")
    public ResponseEntity<StreamingResponseBody> download(HttpServletRequest request) {
        String key = extractKey(request, "/file/download/");
        return streamResponse(key, request, true);
    }

    /**
     * 预览文件（浏览器直接显示图片、PDF，支持流式传输）
     * key 可能包含 / 路径分隔符（如 upload/20260822/xxx.png），
     * 使用 /** 通配符匹配，从 URI 中提取 key
     *
     * 图片缩略图参数（仅图片生效，其他类型忽略）：
     *   w / width   : 目标宽度（像素），0 或不传表示不缩放
     *   q / quality : JPEG 质量 1-100，默认 80
     *   shape       : "circle" 裁剪为正方形内接圆形（PNG，透明背景），用于地图 marker
     */
    @GetMapping("/preview/**")
    public ResponseEntity<?> preview(HttpServletRequest request) {
        String key = extractKey(request, "/file/preview/");

        // 解析缩略图参数
        Integer width = parseIntParam(request, new String[]{"w", "width"});
        Integer quality = parseIntParam(request, new String[]{"q", "quality"});
        String shape = request.getParameter("shape");

        boolean needThumb = (width != null && width > 0)
                || (quality != null && quality > 0 && quality < 100)
                || "circle".equalsIgnoreCase(shape);

        if (!needThumb) {
            // 无缩略图参数：走原有的流式预览（支持 Range 断点续传）
            return streamResponse(key, request, false);
        }
        return thumbnailResponse(key,
                width == null ? 0 : width,
                quality == null ? 80 : quality,
                shape);
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete/**")
    public Result<String> delete(HttpServletRequest request) {
        String key = extractKey(request, "/file/delete/");
        storageService.delete(key);
        return Result.ok("删除成功");
    }

    /**
     * 检查文件是否存在
     */
    @GetMapping("/exists/**")
    public Result<Boolean> exists(HttpServletRequest request) {
        String key = extractKey(request, "/file/exists/");
        return Result.ok(storageService.exists(key));
    }

    // ===================== 预签名 URL =====================

    /**
     * 获取预签名上传 URL（前端直传）
     *
     * @param key         文件 key（可选，为空则自动生成）
     * @param contentType 文件类型
     * @param expiration  有效期（秒，默认 15 分钟）
     * @return 预签名 URL
     */
    @GetMapping("/presigned/upload")
    public Result<PresignedUrlVO> presignedUploadUrl(
            @RequestParam(required = false) String key,
            @RequestParam String contentType,
            @RequestParam(required = false, defaultValue = "900") Long expiration) {
        return Result.ok(getSeaweedFSService().generatePresignedUploadUrl(key, contentType, Duration.ofSeconds(expiration)));
    }

    /**
     * 获取预签名下载 URL（前端直传）
     *
     * @param key        文件 key
     * @param expiration 有效期（秒，默认 15 分钟）
     * @return 预签名 URL
     */
    @GetMapping("/presigned/download/{key}")
    public Result<PresignedUrlVO> presignedDownloadUrl(
            @PathVariable String key,
            @RequestParam(required = false, defaultValue = "900") Long expiration) {
        return Result.ok(getSeaweedFSService().generatePresignedDownloadUrl(key, Duration.ofSeconds(expiration)));
    }

    // ===================== 分片上传 =====================

    /**
     * 初始化分片上传
     *
     * @param key         文件 key（可选）
     * @param contentType 文件类型
     * @return 上传 ID 和文件 key
     */
    @PostMapping("/multipart/init")
    public Result<MultipartUploadInitVO> initMultipartUpload(
            @RequestParam(required = false) String key,
            @RequestParam String contentType) {
        return Result.ok(getSeaweedFSService().initiateMultipartUpload(key, contentType));
    }

    /**
     * 上传分片（服务端代理）
     *
     * @param key        文件 key
     * @param uploadId   上传 ID
     * @param partNumber 分片编号
     * @param file       分片文件
     * @return 分片 ETag
     */
    @PostMapping("/multipart/upload")
    public Result<PartInfoVO> uploadPart(
            @RequestParam String key,
            @RequestParam String uploadId,
            @RequestParam Integer partNumber,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("分片文件不能为空");
        }
        String etag = getSeaweedFSService().uploadPart(key, uploadId, partNumber, file);
        PartInfoVO vo = new PartInfoVO();
        vo.setPartNumber(partNumber);
        vo.setEtag(etag);
        return Result.ok(vo);
    }

    /**
     * 获取预签名分片上传 URL（前端直传分片）
     *
     * @param key        文件 key
     * @param uploadId   上传 ID
     * @param partNumber 分片编号
     * @param expiration 有效期（秒，默认 15 分钟）
     * @return 预签名 URL
     */
    @GetMapping("/presigned/multipart/{uploadId}")
    public Result<PresignedUrlVO> presignedUploadPartUrl(
            @RequestParam String key,
            @PathVariable String uploadId,
            @RequestParam Integer partNumber,
            @RequestParam(required = false, defaultValue = "900") Long expiration) {
        return Result.ok(getSeaweedFSService().generatePresignedUploadPartUrl(
                key, uploadId, partNumber, Duration.ofSeconds(expiration)));
    }

    /**
     * 完成分片上传
     *
     * @param request 完成请求
     * @return 结果
     */
    @PostMapping("/multipart/complete")
    public Result<String> completeMultipartUpload(@RequestBody CompleteMultipartRequest request) {
        getSeaweedFSService().completeMultipartUpload(request.getKey(), request.getUploadId(), request.getParts());
        return Result.ok("上传成功");
    }

    /**
     * 取消分片上传
     *
     * @param key      文件 key
     * @param uploadId 上传 ID
     * @return 结果
     */
    @PostMapping("/multipart/abort")
    public Result<String> abortMultipartUpload(
            @RequestParam String key,
            @RequestParam String uploadId) {
        getSeaweedFSService().abortMultipartUpload(key, uploadId);
        return Result.ok("取消成功");
    }

    // ===================== 私有方法 =====================

    /**
     * 从请求 URI 中提取文件 key
     * 当 key 可能包含 / 时（如 upload/20260822/xxx.png），
     * 使用 /** 通配符映射后，需手动从 URI 中截取 key 部分
     *
     * @param request  当前 HTTP 请求
     * @param prefix   URI 前缀（如 "/file/preview/"）
     * @return 文件 key
     */
    private String extractKey(HttpServletRequest request, String prefix) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        // 去掉 contextPath
        String path = uri.substring(contextPath.length());
        // 去掉 prefix 前缀，剩下的就是 key
        if (path.startsWith(prefix)) {
            return path.substring(prefix.length());
        }
        // 兜底：直接返回 path
        return path;
    }

    /**
     * 获取 SeaweedFS 服务，当前非 SeaweedFS 存储时抛出异常
     */
    private SeaweedFSService getSeaweedFSService() {
        SeaweedFSService service = seaweedFSServiceProvider.getIfAvailable();
        if (service == null) {
            throw new UnsupportedOperationException("当前存储类型不支持该操作");
        }
        return service;
    }

    /**
     * 构建流式响应（支持断点续传）
     *
     * @param key        文件 key
     * @param request    HTTP 请求
     * @param attachment 是否作为附件下载
     * @return 流式响应
     */
    private ResponseEntity<StreamingResponseBody> streamResponse(String key, HttpServletRequest request, boolean attachment) {
        FileMetadata metadata = storageService.getMetadata(key);
        long fileSize = metadata.getContentLength();
        String contentType = metadata.getContentType();
        if (contentType == null || contentType.isEmpty()) {
            contentType = MediaTypeUtils.getMimeType(key).toString();
        }

        long rangeStart = 0;
        long rangeEnd = fileSize - 1;
        HttpStatus status = HttpStatus.OK;

        String rangeHeader = request.getHeader(HttpHeaders.RANGE);
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String rangeValue = rangeHeader.substring("bytes=".length());
            int dashIndex = rangeValue.indexOf('-');
            try {
                if (dashIndex > 0) {
                    rangeStart = Long.parseLong(rangeValue.substring(0, dashIndex));
                    if (dashIndex < rangeValue.length() - 1) {
                        rangeEnd = Long.parseLong(rangeValue.substring(dashIndex + 1));
                    }
                } else if (dashIndex == 0) {
                    // bytes=-500 表示最后 500 字节
                    long suffixLength = Long.parseLong(rangeValue.substring(1));
                    rangeStart = fileSize - suffixLength;
                }
                // 校验范围
                if (rangeStart < 0) {
                    rangeStart = 0;
                }
                if (rangeEnd >= fileSize) {
                    rangeEnd = fileSize - 1;
                }
                if (rangeStart > rangeEnd) {
                    rangeStart = 0;
                    rangeEnd = fileSize - 1;
                }
                status = HttpStatus.PARTIAL_CONTENT;
            } catch (NumberFormatException e) {
                log.warn("Range 请求头解析失败: {}", rangeHeader);
            }
        }

        long contentLength = rangeEnd - rangeStart + 1;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(contentLength);
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");

        if (status == HttpStatus.PARTIAL_CONTENT) {
            headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize);
        }

        String filename = metadata.getFilename();
        if (filename == null || filename.isEmpty()) {
            filename = key;
        }
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20");
        if (attachment) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"");
        } else {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + encodedFilename + "\"");
        }

        final long finalRangeStart = rangeStart;
        final long finalRangeEnd = rangeEnd;

        StreamingResponseBody responseBody = outputStream -> {
            storageService.streamFile(key, outputStream, finalRangeStart, finalRangeEnd);
        };

        return new ResponseEntity<>(responseBody, headers, status);
    }

    // ===================== 缩略图相关 =====================

    /** 单张图片最大允许字节数：32MB，超过直接返回原图不压缩，防止内存爆炸 */
    private static final long MAX_IMAGE_BYTES_FOR_THUMB = 32L * 1024 * 1024;

    /**
     * 解析 Integer 类型请求参数（支持多个别名），解析失败或未传返回 null
     */
    private Integer parseIntParam(HttpServletRequest request, String[] names) {
        for (String name : names) {
            String v = request.getParameter(name);
            if (v != null && !v.isEmpty()) {
                try {
                    return Integer.parseInt(v);
                } catch (NumberFormatException ignore) { /* 非法数字，跳过 */ }
            }
        }
        return null;
    }

    /**
     * 返回缩略图响应（字节级 + 浏览器强缓存，不走 Range 断点续传）
     * 生成失败会静默降级为原图预览
     */
    private ResponseEntity<byte[]> thumbnailResponse(String key, int width, int quality, String shape) {
        try {
            FileMetadata metadata = storageService.getMetadata(key);
            String contentType = metadata.getContentType();
            if (contentType == null || contentType.isEmpty()) {
                contentType = MediaTypeUtils.getMimeType(key).toString();
            }

            // 非图片或文件太大：降级走原图（不流式，直接读字节返回，避免 ResponseEntity 类型混乱）
            if (!ThumbnailUtils.isSupportedImage(contentType) || metadata.getContentLength() > MAX_IMAGE_BYTES_FOR_THUMB) {
                return directBytesResponse(key, metadata, contentType);
            }

            // 1) 命中磁盘缓存，直接返回
            ThumbnailCacheManager.CachedResult cached = ThumbnailCacheManager.get(key, width, quality, shape);
            if (cached != null && cached.getBytes() != null && cached.getBytes().length > 0) {
                return buildThumbResponse(cached.getBytes(), cached.getContentType());
            }

            // 2) 未命中：读完整原图
            byte[] originalBytes = readAllBytes(key);
            if (originalBytes == null || originalBytes.length == 0) {
                // 读取失败，降级走原图流响应
                return buildRedirectToOriginal(key);
            }

            // 3) 生成缩略图
            ThumbnailUtils.ThumbnailResult result = ThumbnailUtils.generate(
                    originalBytes, contentType, width, quality, shape);

            // 4) 写缓存（异常不影响响应）
            ThumbnailCacheManager.put(key, width, quality, shape, result.getBytes(), result.getFormatName());

            return buildThumbResponse(result.getBytes(), result.getContentType());
        } catch (Exception e) {
            log.warn("[preview] 缩略图处理失败，降级返回原图. key={}, err={}", key, e.getMessage());
            return buildRedirectToOriginal(key);
        }
    }

    /**
     * 读取原图全部字节（用于缩略图生成）
     * 注意：调用方应先判断 contentLength，避免大文件读入内存
     */
    private byte[] readAllBytes(String key) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(8192)) {
            storageService.streamFile(key, baos, 0, -1);
            return baos.toByteArray();
        } catch (Exception e) {
            log.warn("[preview] 读取原图字节失败: key={}, err={}", key, e.getMessage());
            return null;
        }
    }

    /**
     * 构造缩略图响应（带浏览器 HTTP 缓存：ETag + max-age 30 天）
     */
    private ResponseEntity<byte[]> buildThumbResponse(byte[] bytes, String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(bytes.length);
        // 浏览器 + CDN 缓存：缩略图一般静态不变，强缓存 30 天
        headers.setCacheControl(CacheControl.maxAge(Duration.ofDays(30)).cachePublic());
        // inline：浏览器直接显示
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    /**
     * 非图片/超大图/生成失败时：直接一次性读原图返回（小文件 OK；大文件场景建议前端用无参数的 preview URL）
     * 为避免重复代码，返回 302 让浏览器重定向到无参 preview 地址，复用 streamResponse 的 Range 能力
     */
    private ResponseEntity<byte[]> buildRedirectToOriginal(String key) {
        // 此处返回的是 byte[] 类型，为保持 ResponseEntity 签名一致，用字节重定向实现
        // 实际无法优雅返回 302+StreamingResponseBody，所以退化为直接读取原图字节
        try {
            FileMetadata metadata = storageService.getMetadata(key);
            String contentType = metadata.getContentType();
            if (contentType == null || contentType.isEmpty()) {
                contentType = MediaTypeUtils.getMimeType(key).toString();
            }
            return directBytesResponse(key, metadata, contentType);
        } catch (Exception ignore) {
            return new ResponseEntity<>(new byte[0], HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<byte[]> directBytesResponse(String key, FileMetadata metadata, String contentType) {
        byte[] bytes = readAllBytes(key);
        if (bytes == null) bytes = new byte[0];
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(bytes.length);
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.setCacheControl(CacheControl.maxAge(Duration.ofDays(7)).cachePublic());
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
