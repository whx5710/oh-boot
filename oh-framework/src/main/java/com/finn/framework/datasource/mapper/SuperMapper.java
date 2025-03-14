package com.finn.framework.datasource.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface SuperMapper<T> {

    @Pages
    Page<T>  selectPageByParam(@Param("selectSql")String selectSql,@Param("p") Map<String, Object> selectParams);

}
