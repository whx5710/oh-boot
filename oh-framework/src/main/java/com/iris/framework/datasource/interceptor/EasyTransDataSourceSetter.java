package com.iris.framework.datasource.interceptor;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.fhs.trans.ds.DataSourceSetter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * EasyTrans数据源设置
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class EasyTransDataSourceSetter implements DataSourceSetter {

    private Map<Object, Object> map;


    /**
     * 指定数据源
     * @param dataSource 数据源名
     */
    @Override
    public void setDataSource(String dataSource) {
        // 使用 DynamicDataSourceContextHolder 的 push 方法动态设置数据源上下文
        // 完成了使用特定数据源的数据库操作后，再调用 poll 方法，以便将数据源上下文清空，避免影响后续的数据库操作
        // 此处是指定固定数据源，因此不需要poll
        DynamicDataSourceContextHolder.push(dataSource);
    }

    @Override
    public Map<Object, Object> getContext() {
        return map;
    }

    @Override
    public void setContext(Map<Object, Object> map) {
        this.map = map;
    }
}
