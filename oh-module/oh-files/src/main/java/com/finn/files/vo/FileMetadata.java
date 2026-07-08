package com.finn.files.vo;

/**
 * 文件元数据
 *
 * @author 王小费 whx5710@qq.com
 */
public class FileMetadata {

    /**
     * 文件大小（字节）
     */
    private long contentLength;

    /**
     * 文件 MIME 类型
     */
    private String contentType;

    /**
     * 文件名
     */
    private String filename;

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
