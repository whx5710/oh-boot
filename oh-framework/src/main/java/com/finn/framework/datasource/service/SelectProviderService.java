package com.finn.framework.datasource.service;

import com.finn.framework.utils.ParamsBuilder;
import com.finn.framework.utils.ParamsSQL;
import org.apache.ibatis.jdbc.SQL;


import static com.finn.core.constant.Constant.PAGE_NUM;
import static com.finn.core.constant.Constant.PAGE_SIZE;

/**
 * 通用provider,拼接增删查改，通过 @SelectProvider注解操作，减少sql编写<br/>
 * 单表查询      selectPageByParam、selectListByParam <br/>
 * 注意：如果对查询性能有要求，不建议使用
 * @author 王小费 whx5710@qq.com
 */
public class SelectProviderService {

    public static final String SELECT_PAGE_PARAM = "selectPageByParam";

    public static final String SELECT_LIST_PARAM = "selectListByParam";

    public static final String FIND_BY_ID = "findById";

    /**
     * 通用单表查询
     * @param fp 参数
     * @return sql
     * @param <T> 类
     */
    public <T> String selectPageByParam(ParamsBuilder<T> fp){
        return fp.getSql().toString();
    }

    /**
     * 通用单表查询
     * @param fp 参数
     * @return sql
     * @param <T> 类
     */
    public <T> String selectListByParam(ParamsBuilder<T> fp){
        fp.remove(PAGE_NUM);
        fp.remove(PAGE_SIZE);
        return fp.getSql().toString();
    }

    public <T> String findById(Long id, Class<T> clazz){
        SQL sql = new SQL();
        String tableName = ParamsSQL.getTableName(clazz);
        String priKey = ParamsSQL.getPriKey(clazz);
        sql.SELECT("*").FROM(tableName).WHERE(priKey + " = #{id}");
        return sql.toString();
    }
}
