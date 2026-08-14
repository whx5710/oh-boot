package com.finn.urban.query;

import com.finn.framework.query.Query;

import java.math.BigDecimal;

/**
 * 兴趣点查询
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-13 15:09:28
 *
 */
public class PoiQuery extends Query {
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
     * 坐标系，如WGS84、BD09
     */
    private String geoType;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 创建者
     */
    private Long creator;

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

    public String getGeoType() {
        return geoType;
    }

    public void setGeoType(String geoType) {
        this.geoType = geoType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

}
