package com.finn.urban.entity;

import com.finn.framework.aop.annotations.TableField;
import com.finn.framework.aop.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 兴趣点
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-13 15:09:28
 *
 */
@TableName("ur_poi")
public class Poi extends BaseEntity {
    /**
     * 地理位置
     */
    private String location;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 区域编码
     */
    @TableField("area_code")
    private String areaCode;

    /**
     * 区域名称
     */
    @TableField("area_name")
    private String areaName;

    /**
     * 坐标系，如WGS84、BD09
     */
    @TableField("geo_type")
    private String geoType;

    /**
     * 备注
     */
    private String remark;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getGeoType() {
        return geoType;
    }

    public void setGeoType(String geoType) {
        this.geoType = geoType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
