package com.finn.urban.mapper;

import com.finn.framework.aop.annotations.Pages;
import com.finn.urban.entity.Poi;
import com.finn.urban.query.PoiQuery;
import com.finn.framework.datasource.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 兴趣点
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-13 15:09:28
 * 
 */
@Mapper
public interface PoiMapper extends BaseMapper<Poi> {
    @Pages
    List<Poi> getList(PoiQuery query);
}
