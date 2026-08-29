package com.finn.urban.vo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 运动记录表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
public class SportRecordVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 轨迹名称
     */
    private String name;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 记录日期，yyyymmdd
     */
    private String recordDate;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 起始地址
     */
    private String startAddress;

    /**
     * 终止地址
     */
    private String endAddress;

    /**
     * 时长
     */
    private Long duration;

    /**
     * 距离 里程，公里，保留3位小数（米）
     */
    private Double distance;

    /**
     * 平均速度，米/秒
     */
    private Double avgSpeed;

    /**
     * 备注
     */
    private String remark;

    private List<PointVO> points;

    /**
     * 打卡点数量（列表页用）
     */
    private Integer checkinCount;

    /**
     * 打卡点列表（详情页用，photos 为文件 key 列表）
     */
    private List<SportCheckinVO> checkins;

    /**
     * 可见度0保密1公开
     */
    private Integer visibility = 0;

    /**
     * 公开日期
     */
    private LocalDateTime releaseTime;

    /**
     * 封面文件ID
     */
    private String coverFileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    /**
     * 保留3位小数（米）
     * @param distance
     */
    public void setDistance(Double distance) {
        if(distance != null){
            BigDecimal bigDecimal = new BigDecimal(distance);
            bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
            this.distance = bigDecimal.doubleValue();
        }else{
            this.distance = null;
        }
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public List<PointVO> getPoints() {
        return points;
    }

    public void setPoints(List<PointVO> points) {
        this.points = points;
    }

    public Integer getCheckinCount() {
        return checkinCount;
    }

    public void setCheckinCount(Integer checkinCount) {
        this.checkinCount = checkinCount;
    }

    public List<SportCheckinVO> getCheckins() {
        return checkins;
    }

    public void setCheckins(List<SportCheckinVO> checkins) {
        this.checkins = checkins;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCoverFileId() {
        return coverFileId;
    }

    public void setCoverFileId(String coverFileId) {
        this.coverFileId = coverFileId;
    }
}
