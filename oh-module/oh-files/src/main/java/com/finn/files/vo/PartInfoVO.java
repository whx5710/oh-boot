package com.finn.files.vo;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分片信息视图对象
 */
public class PartInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分片编号
     */
    private Integer partNumber;

    /**
     * 分片 ETag
     */
    private String etag;

    public Integer getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Integer partNumber) {
        this.partNumber = partNumber;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
