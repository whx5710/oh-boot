package com.finn.urban.vo;

import java.math.BigDecimal;

public class PointVO {

    /**
     * 经度，范围为 -180~180，负数表示西经，使用 gcj02 国测局坐标系
     */
    private BigDecimal longitude;

    /**
     * 纬度，范围为 -90~90，负数表示南纬，使用 gcj02 国测局坐标系
     */
    private BigDecimal latitude;

    public PointVO(BigDecimal longitude, BigDecimal latitude){
        this.longitude = longitude;
        this.latitude = latitude;
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
}
