package com.finn.files.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 完成分片上传请求对象
 */
public class CompleteMultipartRequest implements Serializable {
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

    /**
     * 分片信息列表
     */
    private List<PartInfoVO> parts;

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

    public List<PartInfoVO> getParts() {
        return parts;
    }

    public void setParts(List<PartInfoVO> parts) {
        this.parts = parts;
    }
}
