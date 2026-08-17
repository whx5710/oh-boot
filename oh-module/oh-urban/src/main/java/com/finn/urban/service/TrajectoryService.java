package com.finn.urban.service;

import com.finn.urban.entity.TrajectoryEntity;
import com.finn.urban.query.TrajectoryQuery;
import com.finn.urban.vo.TrajectoryVO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 轨迹坐标表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-17
 */
public interface TrajectoryService {

    /**
     * 分页查询
     */
    Page<TrajectoryEntity> page(TrajectoryQuery query);

    /**
     * 根据ID查询详情
     */
    TrajectoryVO detail(Long id);

    /**
     * 批量保存（数据写入 Redis 队列，由定时任务异步落库）
     */
    void saveBatch(List<TrajectoryVO> list);

    /**
     * 修改
     */
    void update(TrajectoryVO vo);

    /**
     * 删除
     */
    void delete(List<Long> idList);
}
