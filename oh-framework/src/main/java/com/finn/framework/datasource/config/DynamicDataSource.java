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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源
 * @author 王小费 whx5710@qq.com
 * @since 2024-08-11
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 数据源集合，使用ConcurrentHashMap提高并发安全性
     */
    private Map<String, DataSource> dynamicDataSources = new ConcurrentHashMap<>();
    /**
     * 主数据源
     */
    private DataSource primaryDb;

    private MybatisProperties mybatisProperties;

    public DynamicDataSource(){

    }

    public DynamicDataSource(Map<String, DataSource> dynamicDataSources){
        this.dynamicDataSources = new ConcurrentHashMap<>(dynamicDataSources);
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
        // 更新默认目标数据源
        setDefaultTargetDataSource(primaryDb);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDynamicDataSourceKey();
    }

    public Map<String, DataSource> getDynamicDataSources() {
        return dynamicDataSources;
    }

    public void setDynamicDataSources(Map<String, DataSource> defineTargetDataSources) {
        this.dynamicDataSources = new ConcurrentHashMap<>(defineTargetDataSources);
        // 更新目标数据源
        setTargetDataSources(new HashMap<>(defineTargetDataSources));
        afterPropertiesSet(); // 刷新数据源
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
     * 动态添加数据源
     * @param key 数据源名称
     * @param dataSource 数据源
     */
    public void addDataSource(String key, DataSource dataSource) {
        if (dynamicDataSources.containsKey(key)) {
            log.warn("数据源[{}]已存在，将被替换", key);
        }
        dynamicDataSources.put(key, dataSource);
        // 更新目标数据源
        Map<Object, Object> targetDataSources = new HashMap<>(dynamicDataSources);
        setTargetDataSources(targetDataSources);
        afterPropertiesSet(); // 刷新数据源
        log.info("数据源[{}]添加成功", key);
    }

    /**
     * 动态移除数据源
     * @param key 数据源名称
     * @return 是否移除成功
     */
    public boolean removeDataSource(String key) {
        if (!dynamicDataSources.containsKey(key)) {
            log.warn("数据源[{}]不存在", key);
            return false;
        }
        // 不能移除主数据源
        if (primaryDb != null && primaryDb.equals(dynamicDataSources.get(key))) {
            log.error("不能移除主数据源[{}]", key);
            return false;
        }
        dynamicDataSources.remove(key);
        // 更新目标数据源
        Map<Object, Object> targetDataSources = new HashMap<>(dynamicDataSources);
        setTargetDataSources(targetDataSources);
        afterPropertiesSet(); // 刷新数据源
        log.info("数据源[{}]移除成功", key);
        return true;
    }

    /**
     * 检查数据源是否存在
     * @param key 数据源名称
     * @return 是否存在
     */
    public boolean containsDataSource(String key) {
        return dynamicDataSources.containsKey(key);
    }

    /**
     * 获取数据源数量
     * @return 数据源数量
     */
    public int getDataSourceCount() {
        return dynamicDataSources.size();
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
