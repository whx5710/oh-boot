package com.finn.framework.datasource.config;

import com.finn.framework.datasource.utils.DynamicDataSourceHolder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 * @author 王小费 whx5710@qq.com
 * @since 2024-08-11
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${mybatis.mapper-locations:noPath}")
    String locationPattern;

    private Map<Object, Object> dynamicDataSources;

    private String primaryDb;

    public DynamicDataSource(){

    }

    public DynamicDataSource(Map<Object, Object> dynamicDataSources){
        this.dynamicDataSources = dynamicDataSources;
    }

    public String getPrimaryDb() {
        return primaryDb;
    }

    /**
     * 指定主数据源
     * @param primaryDb
     */
    public void setPrimaryDb(String primaryDb) {
        this.primaryDb = primaryDb;
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
        if(key == null || key.equals("")){
            key = primaryDb;
            log.warn("使用默认数据源！【{}】", key);
        }
        log.debug("SqlSessionFactory使用{}数据源", key);
        sqlSessionFactoryBean.setDataSource(getDs(key));
        // 对应mybatis的xml路径
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(locationPattern));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 获取SqlSessionFactory
     * @return
     * @throws Exception
     */
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        return getSqlSessionFactory(null);
    }
}
