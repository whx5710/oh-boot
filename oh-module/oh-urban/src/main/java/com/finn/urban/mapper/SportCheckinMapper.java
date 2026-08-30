package com.finn.urban.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.urban.entity.SportCheckin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 运动打卡点表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-21
 */
@Mapper
public interface SportCheckinMapper extends BaseMapper<SportCheckin> {

    /**
     * 查询坐标半径 5 千米内最近的 80 条打卡记录（按 created_at 倒序）
     * @param latitude 纬度，gcj02 坐标系
     * @param longitude 经度，gcj02 坐标系
     * @return 打卡记录列表
     */
    List<SportCheckin> listNearby(@Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude);
}
