package com.finn.urban.vo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 轨迹坐标表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
public class TrajectoryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 位置的精确度，反应与真实位置之间的接近程度，数值越小越精确
     */
    private BigDecimal accuracy;

    /**
     * 高度，单位 m
     */
    private BigDecimal altitude;

    /**
     * 水平精度，单位 m
     */
    private BigDecimal horizontalAccuracy;

    /**
     * 经度，范围为 -180~180，负数表示西经，使用 gcj02 国测局坐标系
     */
    private BigDecimal longitude;

    /**
     * 纬度，范围为 -90~90，负数表示南纬，使用 gcj02 国测局坐标系
     */
    private BigDecimal latitude;

    /**
     * 速度，单位 m/s
     */
    private BigDecimal speed;

    /**
     * 垂直精度，单位 m
     */
    private BigDecimal verticalAccuracy;

    /**
     * gps定位时间
     */
    private Long gpsTime;

    /**
     * 坐标系，默认gcj02
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }

    public BigDecimal getAltitude() {
        return altitude;
    }

    public void setAltitude(BigDecimal altitude) {
        this.altitude = altitude;
    }

    public BigDecimal getHorizontalAccuracy() {
        return horizontalAccuracy;
    }

    public void setHorizontalAccuracy(BigDecimal horizontalAccuracy) {
        this.horizontalAccuracy = horizontalAccuracy;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getVerticalAccuracy() {
        return verticalAccuracy;
    }

    public void setVerticalAccuracy(BigDecimal verticalAccuracy) {
        this.verticalAccuracy = verticalAccuracy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(Long gpsTime) {
        this.gpsTime = gpsTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
