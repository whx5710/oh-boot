package com.finn.urban.mapper;

import com.finn.common.entity.HashDto;
import com.finn.framework.datasource.mapper.BaseMapper;
import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.query.SportRecordQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 运动记录表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
@Mapper
public interface SportRecordMapper extends BaseMapper<SportRecordEntity> {

    HashDto statistics(@Param("params")SportRecordQuery params);
}
