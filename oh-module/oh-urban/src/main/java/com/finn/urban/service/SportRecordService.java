package com.finn.urban.service;

import com.finn.common.entity.HashDto;
import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.query.SportRecordQuery;
import com.finn.urban.vo.SportRecordVO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 运动记录表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
public interface SportRecordService {

    /**
     * 分页查询
     */
    Page<SportRecordEntity> page(SportRecordQuery query);

    /**
     * 根据ID查询详情（含轨迹点 + 打卡点）
     */
//    SportRecordVO detail(Long id);

    /**
     * 根据ID查询基础详情 + 轨迹点（不含打卡点）
     */
    SportRecordVO detailBaseWithTrack(Long id);

    /**
     * 保存
     */
    Long save(SportRecordVO vo);

    /**
     * 开始运动，返回运动记录ID
     */
    Long start(SportRecordVO vo);

    /**
     * 结束运动：更新记录状态，按 clientId 去重落库打卡点
     */
    String finish(Long id, SportRecordVO vo);

    /**
     * 是否正在运动期间
     */
    Boolean isRunning(Long id);

    /**
     * 修改
     */
    void update(SportRecordVO vo);

    /**
     * 删除
     */
    void delete(List<Long> idList);

    /**
     * 统计运动情况
     * @param query
     * @return
     */
    HashDto statistics(SportRecordQuery query);
}
