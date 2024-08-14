package com.iris.framework.datasource.config.auto;

import com.iris.framework.datasource.utils.DynamicDataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * 动态数据源
 * @author 王小费 whx5710@qq.com
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(){

    }

    public DynamicDataSource(Map<Object, Object> defineTargetDataSources){
        this.defineTargetDataSources = defineTargetDataSources;
    }

    private Map<Object, Object> defineTargetDataSources;

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }

    public Map<Object, Object> getDefineTargetDataSources() {
        return defineTargetDataSources;
    }

    public void setDefineTargetDataSources(Map<Object, Object> defineTargetDataSources) {
        this.defineTargetDataSources = defineTargetDataSources;
    }
}
