package com.finn.urban.service;

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
     * 根据ID查询详情
     */
    SportRecordVO detail(Long id);

    /**
     * 保存
     */
    Long save(SportRecordVO vo);

    /**
     * 开始运动
     * @return
     */
    Long start();
    /**
     * 修改
     */
    void update(SportRecordVO vo);

    /**
     * 删除
     */
    void delete(List<Long> idList);
}
