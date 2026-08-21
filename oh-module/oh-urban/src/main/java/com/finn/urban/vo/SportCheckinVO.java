package com.finn.urban.vo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 运动打卡点
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-21
 */
public class SportCheckinVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id（保存时由后端生成，返回给前端；前端传入的临时标识走 clientId）
     */
    private Long id;

    /**
     * 运动记录ID，关联 ur_sport_record.id
     */
    private Long groupId;

    /**
     * 前端生成的打卡点标识，结束时去重用
     */
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
     * 打卡照片URL/key列表
     */
    private List<String> photos;

    /**
     * 打卡描述
     */
    private String description;

    /**
     * 打卡时间戳（毫秒），前端传入
     */
    private Long createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
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
