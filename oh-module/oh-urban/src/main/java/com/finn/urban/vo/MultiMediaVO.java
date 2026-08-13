package com.finn.urban.vo;

import com.finn.common.entity.IDEntity;

import java.io.Serializable;

/**
 * 多媒体
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-06-14 17:42:45
 *
 */
public class MultiMediaVO extends IDEntity implements Serializable {

    /**
     * 事件ID
     */
    private Long evtId;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 所属状态：1待处理2处理中3已解决4已驳回
     */
    private String statusType;

    public Long getEvtId() {
        return evtId;
    }

    public void setEvtId(Long evtId) {
        this.evtId = evtId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }
}
