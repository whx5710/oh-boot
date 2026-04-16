package com.finn.framework.datasource.service;

import com.finn.framework.datasource.wrapper.CountWrapper;
import com.finn.framework.datasource.wrapper.Wrapper;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import org.apache.ibatis.jdbc.SQL;

import java.io.Serializable;

import static com.finn.framework.common.constant.Constant.PAGE_NUM;
import static com.finn.framework.common.constant.Constant.PAGE_SIZE;

/**
 * 通用provider,拼接增删查改，通过 @SelectProvider注解操作，减少sql编写<br/>
 * 单表查询      listByWrapper，如果有分页参数，则对list进行分页 <br/>
 * 注：如果对查询性能要求高，请直接使用SQL操作
 * @author 王小费 whx5710@qq.com
 */
public class QueryProviderService {

    public static final String SELECT_PAGE_PARAM = "selectPageByWrapper";

    public static final String SELECT_LIST_PARAM = "selectListByWrapper";

    public static final String LIST_PARAM = "listByWrapper";

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
     * 通用单表查询，如果传入了分页参数，进行分页查询
     * @param fp 参数
     * @return sql
     * @param <T> 类
     */
    public <T> String listByWrapper(QueryWrapper<T> fp){
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

    /**
     * 通过主键ID获取一个实体对象
     * @param id 主键ID
     * @param clazz 类
     * @return sql
     * @param <T> e
     */
    public <T> String findById(Serializable id, Class<T> clazz){
        SQL sql = new SQL();
        String tableName = Wrapper.getTableName(clazz);
        String priKey = Wrapper.getPriKey(clazz);
        sql.SELECT("*").FROM(tableName).WHERE(priKey + " = #{id}");
        return sql.toString();
    }
}
