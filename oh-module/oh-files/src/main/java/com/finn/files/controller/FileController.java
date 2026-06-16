package com.finn.files.controller;

import com.finn.files.service.SeaweedFSService;
import com.finn.framework.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * 文件上传
 *
 * 参考命令
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
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    private final SeaweedFSService seaweedFSService;

    /**
     * 默认最大文件大小：500MB
     */
    private static final long MAX_FILE_SIZE = 500 * 1024 * 1024;

    public FileController(SeaweedFSService seaweedFSService){
        this.seaweedFSService = seaweedFSService;
    }

    /**
     * 上传（使用流式上传，支持大文件）
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的文件");
        }
        // 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            return Result.error("文件大小超过限制，最大支持 " + (MAX_FILE_SIZE / 1024 / 1024) + "MB");
        }
        return Result.ok(seaweedFSService.uploadFile(file));
    }

    /**
     * 下载文件
     */
    @GetMapping("/download/{key}")
    public ResponseEntity<byte[]> download(@PathVariable String key) {
        byte[] data = seaweedFSService.downloadFile(key);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    /**
     * 预览文件（浏览器直接显示图片、PDF）
     */
    @GetMapping("/preview/{key}")
    public ResponseEntity<byte[]> preview(@PathVariable String key) {
        byte[] data = seaweedFSService.downloadFile(key);

        // 根据文件扩展名获取 Content-Type
        MediaType contentType = getMediaType(key);

        // inline 表示浏览器内直接显示，不下载
        String encodedFilename = URLEncoder.encode(key, StandardCharsets.UTF_8)
                .replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + encodedFilename + "\"")
                .contentType(contentType)
                .body(data);
    }

    /**
     * 删除文件
     */
    @GetMapping("/delete/{key}")
    public Result<String> delete(@PathVariable String key) {
        seaweedFSService.deleteFile(key);
        return Result.ok("删除成功");
    }

    /**
     * 检查文件是否存在
     */
    @GetMapping("/exists/{key}")
    public Result<Boolean> exists(@PathVariable String key) {
        return Result.ok(seaweedFSService.exists(key));
    }


    /**
     * 根据文件名获取 MediaType
     */
    private MediaType getMediaType(String filename) {
        String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

        return switch (ext) {
            // 图片
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            case "webp" -> MediaType.valueOf("image/webp");
            case "svg" -> MediaType.valueOf("image/svg+xml");
            case "bmp" -> MediaType.valueOf("image/bmp");
            // PDF
            case "pdf" -> MediaType.APPLICATION_PDF;
            // 文本
            case "txt" -> MediaType.TEXT_PLAIN;
            case "html", "htm" -> MediaType.TEXT_HTML;
            case "css" -> MediaType.valueOf("text/css");
            case "js" -> MediaType.valueOf("application/javascript");
            case "json" -> MediaType.APPLICATION_JSON;
            case "xml" -> MediaType.APPLICATION_XML;
            // 视频
            case "mp4" -> MediaType.valueOf("video/mp4");
            case "webm" -> MediaType.valueOf("video/webm");
            // 音频
            case "mp3" -> MediaType.valueOf("audio/mpeg");
            case "wav" -> MediaType.valueOf("audio/wav");
            // 默认：二进制流（浏览器会提示下载）
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}
