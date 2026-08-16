package com.finn.files.vo;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分片上传初始化视图对象
 */
public class MultipartUploadInitVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 上传 ID
     */
    private String uploadId;

    /**
     * 文件 key
     */
    private String key;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
