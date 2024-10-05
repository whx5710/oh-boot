package com.iris.sys.base.entity;

import com.iris.framework.entity.BaseEntity;

import java.util.Objects;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class SysAttachmentEntity extends BaseEntity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysAttachmentEntity that)) return false;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getSize(), that.getSize()) && Objects.equals(getPlatform(), that.getPlatform());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUrl(), getSize(), getPlatform());
    }
}