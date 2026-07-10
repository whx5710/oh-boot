package com.finn.files.controller;

import com.finn.files.service.StorageService;
import com.finn.files.service.impl.SeaweedFSService;
import com.finn.files.utils.MediaTypeUtils;
import com.finn.files.vo.CompleteMultipartRequest;
import com.finn.files.vo.FileMetadata;
import com.finn.files.vo.MultipartUploadInitVO;
import com.finn.files.vo.PartInfoVO;
import com.finn.files.vo.PresignedUrlVO;
import com.finn.framework.entity.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
     */
    @GetMapping("/download/{key}")
    public ResponseEntity<StreamingResponseBody> download(@PathVariable String key, HttpServletRequest request) {
        return streamResponse(key, request, true);
    }

    /**
     * 预览文件（浏览器直接显示图片、PDF，支持流式传输）
     */
    @GetMapping("/preview/{key}")
    public ResponseEntity<StreamingResponseBody> preview(@PathVariable String key, HttpServletRequest request) {
        return streamResponse(key, request, false);
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete/{key}")
    public Result<String> delete(@PathVariable String key) {
        storageService.delete(key);
        return Result.ok("删除成功");
    }

    /**
     * 检查文件是否存在
     */
    @GetMapping("/exists/{key}")
    public Result<Boolean> exists(@PathVariable String key) {
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
}
