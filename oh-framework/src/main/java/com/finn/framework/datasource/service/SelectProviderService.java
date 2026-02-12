package com.finn.framework.datasource.service;

import com.finn.framework.datasource.utils.CountWrapper;
import com.finn.framework.datasource.utils.Wrapper;
import com.finn.framework.datasource.utils.QueryWrapper;
import org.apache.ibatis.jdbc.SQL;


import static com.finn.core.constant.Constant.PAGE_NUM;
import static com.finn.core.constant.Constant.PAGE_SIZE;

/**
 * 通用provider,拼接增删查改，通过 @SelectProvider注解操作，减少sql编写<br/>
 * 单表查询      selectPageByWrapper、selectListByWrapper <br/>
 * 注意：如果对查询性能有要求，不建议使用
 * @author 王小费 whx5710@qq.com
 */
public class SelectProviderService {

    public static final String SELECT_PAGE_PARAM = "selectPageByWrapper";

    public static final String SELECT_LIST_PARAM = "selectListByWrapper";

    public static final String FIND_BY_ID = "findById";

    public static final String SELECT_COUNT_PARAM = "count";

    /**
     * 通用单表查询
     * @param fp 参数
     * @return sql
     * @param <T> 类
     */
    public <T> String selectPageByWrapper(QueryWrapper<T> fp){
        return fp.getSql().toString();
    }

    /**
     * 通用单表查询
     * @param fp 参数
     * @return sql
     * @param <T> 类
     */
    public <T> String selectListByWrapper(QueryWrapper<T> fp){
        fp.remove(PAGE_NUM);
        fp.remove(PAGE_SIZE);
        return fp.getSql().toString();
    }

    /**
     * 通用汇总计数
     * @param fp 参数
     * @return sql
     * @param <T> 类
     */
    public <T> String count(CountWrapper<T> fp){
        fp.remove(PAGE_NUM);
        fp.remove(PAGE_SIZE);
        return fp.getSql().toString();
    }

    public <T> String findById(Long id, Class<T> clazz){
        SQL sql = new SQL();
        String tableName = Wrapper.getTableName(clazz);
        String priKey = Wrapper.getPriKey(clazz);
        sql.SELECT("*").FROM(tableName).WHERE(priKey + " = #{id}");
        return sql.toString();
    }
}
