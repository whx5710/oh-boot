package com.finn.system.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_attachment")
public class AttachmentEntity extends BaseEntity {
    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件地址
     */
    private String url;

    /**
     * 附件大小
     */
    private Long size;

    /**
     * 存储平台
     */
    private String platform;
    /**
     * 临时文件标识，可删除，1为临时文件
     */
    @TableField("tmp_flag")
    private Integer tmpFlag;

    @TableField("content_type")
    private String contentType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getTmpFlag() {
        return tmpFlag;
    }

    public void setTmpFlag(Integer tmpFlag) {
        this.tmpFlag = tmpFlag;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}