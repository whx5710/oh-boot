package com.finn.framework.datasource.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SuperMapper<T> {

    /**
     * 分页查询
     * @param selectSql sql字符串
     * @param selectParams 参数
     * @return
     */
    @Pages
    Page<T>  selectPageByParam(@Param("selectSql")String selectSql,@Param("fp") Map<String, Object> selectParams);


    /**
     * 列表查询
     * @param selectSql sql字符串
     * @param selectParams 参数
     * @return
     */
    List<T> selectListByParam(@Param("selectSql")String selectSql, @Param("fp") Map<String, Object> selectParams);

}
