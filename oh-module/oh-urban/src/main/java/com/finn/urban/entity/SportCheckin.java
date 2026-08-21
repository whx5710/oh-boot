package com.finn.urban.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 运动打卡点表（拍照打卡）
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-21
 */
@TableName("ur_sport_checkin")
public class SportCheckin extends BaseEntity {

    /**
     * 运动记录ID，关联 ur_sport_record.id（与 ur_trajectory.group_id 一致）
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 前端生成的打卡点标识，结束时去重用
     */
    @TableField("client_id")
    private String clientId;

    /**
     * 纬度，gcj02 坐标系
     */
    private BigDecimal latitude;

    /**
     * 经度，gcj02 坐标系
     */
    private BigDecimal longitude;

    /**
     * 打卡地址
     */
    private String address;

    /**
     * 打卡照片，JSON 数组字符串，存文件key
     */
    private String photos;

    /**
     * 打卡描述
     */
    private String description;

    /**
     * 打卡时间戳（毫秒），前端传入
     */
    @TableField("created_at")
    private Long createdAt;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
