package com.finn.framework.datasource.config;

import com.finn.core.exception.ServerException;
import com.finn.framework.datasource.utils.DynamicDataSourceHolder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    /**
     * 数据源集合
     */
    private Map<String, DataSource> dynamicDataSources;
    /**
     * 主数据源
     */
    private DataSource primaryDb;

    private MybatisProperties mybatisProperties;

    public DynamicDataSource(){

    }

    public DynamicDataSource(Map<String, DataSource> dynamicDataSources){
        this.dynamicDataSources = dynamicDataSources;
    }

    public void setMybatisProperties(MybatisProperties mybatisProperties){
        this.mybatisProperties = mybatisProperties;
    }

    public DataSource getPrimaryDb() {
        return primaryDb;
    }

    /**
     * 指定主数据源
     * @param primaryDb
     */
    public void setPrimaryDb(DataSource primaryDb) {
        this.primaryDb = primaryDb;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }

    public Map<String, DataSource> getDynamicDataSources() {
        return dynamicDataSources;
    }

    public void setDynamicDataSources(Map<String, DataSource> defineTargetDataSources) {
        this.dynamicDataSources = defineTargetDataSources;
    }

    /**
     * 获取数据源
     * @param key key
     * @return
     */
    public DataSource getDs(String key){
        return this.dynamicDataSources.get(key);
    }

    /**
     * 获取SqlSessionFactory
     * @param key 数据源
     * @return
     * @throws Exception
     */
    public SqlSessionFactory getSqlSessionFactory(String key) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        if(key == null || key.isEmpty()){
            sqlSessionFactoryBean.setDataSource(primaryDb);
        }else{
            if(this.dynamicDataSources.containsKey(key)){
                sqlSessionFactoryBean.setDataSource(this.dynamicDataSources.get(key));
            }else{
                throw new ServerException("未找到对应的数据源【" + key + "】");
            }
        }
        // 对应mybatis的xml路径
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(String.join(",", mybatisProperties.getMapperLocations())));
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        if(sqlSessionFactory == null){
            throw new ServerException("未找到对应的SqlSessionFactory，请检查！【" + key + "】");
        }
        MybatisProperties.CoreConfiguration mybatisPropertiesConfiguration = mybatisProperties.getConfiguration();
        if(mybatisPropertiesConfiguration == null){
            log.warn("mybatis配置为空，请检查");
        }else{
            Configuration configuration = sqlSessionFactory.getConfiguration();
            // 是否开启驼峰命名自动映射
            if(mybatisPropertiesConfiguration.getMapUnderscoreToCamelCase() != null){
                configuration.setMapUnderscoreToCamelCase(mybatisPropertiesConfiguration.getMapUnderscoreToCamelCase());
            }
            // 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存
            if(mybatisPropertiesConfiguration.getCacheEnabled() != null){
                configuration.setCacheEnabled(mybatisPropertiesConfiguration.getCacheEnabled());
            }
            // 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法
            if(mybatisPropertiesConfiguration.getCallSettersOnNulls() != null){
                configuration.setCallSettersOnNulls(mybatisPropertiesConfiguration.getCallSettersOnNulls());
            }
            // 当没有为参数指定特定的 JDBC 类型时，空值的默认 JDBC 类型
            if(mybatisPropertiesConfiguration.getJdbcTypeForNull() != null){
                configuration.setJdbcTypeForNull(mybatisPropertiesConfiguration.getJdbcTypeForNull());
            }
            // 其他配置可继续扩展
        }
        return sqlSessionFactory;
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
