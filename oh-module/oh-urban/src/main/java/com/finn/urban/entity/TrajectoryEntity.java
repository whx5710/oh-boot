package com.finn.urban.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 轨迹坐标表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
@TableName("ur_trajectory")
public class TrajectoryEntity extends BaseEntity {

    /**
     * 位置的精确度，反应与真实位置之间的接近程度，数值越小越精确
     */
    private Double accuracy;

    /**
     * 高度，单位 m
     */
    private Double altitude;

    /**
     * 水平精度，单位 m
     */
    @TableField("horizontal_accuracy")
    private Double horizontalAccuracy;

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
    private Double speed;

    /**
     * 垂直精度，单位 m
     */
    @TableField("vertical_accuracy")
    private Double verticalAccuracy;

    /**
     * gps定位时间
     */
    @TableField("gps_time")
    private Long gpsTime;

    /**
     * GPS时间直观展示
     */
    @TableField("gps_time_show")
    private String gpsTimeShow;

    /**
     * 坐标系，默认gcj02
     */
    @TableField("type")
    private String type;

    /**
     * 分组ID，同一次运动，分组ID相同
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 备注
     */
    private String remark;

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getHorizontalAccuracy() {
        return horizontalAccuracy;
    }

    public void setHorizontalAccuracy(Double horizontalAccuracy) {
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

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getVerticalAccuracy() {
        return verticalAccuracy;
    }

    public void setVerticalAccuracy(Double verticalAccuracy) {
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

    public String getGpsTimeShow() {
        return gpsTimeShow;
    }

    public void setGpsTimeShow(String gpsTimeShow) {
        this.gpsTimeShow = gpsTimeShow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
