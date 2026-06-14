package com.finn.urban.mapper;

import com.finn.framework.aop.annotations.Pages;
import com.finn.urban.entity.Event;
import com.finn.urban.query.EventQuery;
import com.finn.framework.datasource.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 事件表
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-06-14 17:42:45
 * 
 */
@Mapper
public interface EventMapper extends BaseMapper<Event> {
    @Pages
    List<Event> getList(EventQuery query);
}
