package com.finn.files.utils;

import org.springframework.http.MediaType;

/**
 * 媒体类型工具类
 *
 * @author 王小费 whx5710@qq.com
 */
public class MediaTypeUtils {

    private MediaTypeUtils() {
    }

    /**
     * 根据文件名获取 MediaType
     *
     * @param filename 文件名
     * @return MediaType
     */
    public static MediaType getMediaType(String filename) {
        if (filename == null || !filename.contains(".")) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
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

    /**
     * 根据文件名获取 MIME 类型字符串
     *
     * @param filename 文件名
     * @return MIME 类型
     */
    public static String getMimeType(String filename) {
        return getMediaType(filename).toString();
    }
}
