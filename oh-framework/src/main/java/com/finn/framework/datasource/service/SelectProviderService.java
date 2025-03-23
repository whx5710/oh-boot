package com.finn.framework.datasource.service;

import com.finn.framework.utils.ParamsBuilder;


import static com.finn.core.constant.Constant.PAGE_NUM;
import static com.finn.core.constant.Constant.PAGE_SIZE;

/**
 * 通用provider,拼接增删查改，通过 @SelectProvider注解操作，减少sql编写<br/>
 * 单表查询      selectList selectPage selectPageByParam <br/>
 * 注意：如果对查询性能有要求，不建议使用
 * @author 王小费 whx5710@qq.com
 */
public class SelectProviderService extends ProviderService{

    public static final String SELECT_PAGE_PARAM = "selectPageByParam";

    public static final String SELECT_LIST_PARAM = "selectListByParam";

    /**
     * 通用单表查询
     * @param fp 参数
     * @return sql
     * @param <T> 类
     */
    public <T> String selectPageByParam(ParamsBuilder<T> fp){
        return fp.getSqlStr();
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
        return fp.getSqlStr();
    }
}
