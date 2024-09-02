package com.iris.framework.datasource.config.auto;

import com.iris.framework.datasource.utils.DynamicDataSourceHolder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 * @author 王小费 whx5710@qq.com
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Value("${mybatis.mapper-locations:noPath}")
    String locationPattern;

    private Map<Object, Object> dynamicDataSources;

    public DynamicDataSource(){

    }

    public DynamicDataSource(Map<Object, Object> dynamicDataSources){
        this.dynamicDataSources = dynamicDataSources;
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }

    public Map<Object, Object> getDynamicDataSources() {
        return dynamicDataSources;
    }

    public void setDynamicDataSources(Map<Object, Object> defineTargetDataSources) {
        this.dynamicDataSources = defineTargetDataSources;
    }

    /**
     * 获取数据源
     * @param key key
     * @return
     */
    public DataSource getDs(String key){
        return (DataSource) this.dynamicDataSources.get(key);
    }

    /**
     * 获取SqlSessionFactory
     * @param key key
     * @return
     * @throws Exception
     */
    public SqlSessionFactory getSqlSessionFactory(String key) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDs(key));
        // 对应mybatis的xml路径
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(locationPattern));
        return sqlSessionFactoryBean.getObject();
    }
}
