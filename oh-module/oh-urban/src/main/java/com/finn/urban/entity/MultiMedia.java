package com.finn.urban.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 多媒体附件表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-06-14 17:42:45
 *
 */
@TableName("ur_multi_media")
public class MultiMedia extends BaseEntity {

    /**
     * 事件ID
     */
    @TableField("evt_id")
    private Long evtId;

    /**
     * 文件ID
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 所属状态：1待处理2处理中3已解决4已驳回
     */
    @TableField("status_type")
    private String statusType;

    /**
     * 备注
     */
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
