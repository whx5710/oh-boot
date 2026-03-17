package com.finn.framework.datasource.interceptor;

import com.finn.framework.common.properties.DataSourceProperty;
import com.finn.framework.common.properties.DynamicDataSourceProperties;
import com.finn.framework.datasource.utils.DynamicDataSourceHolder;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

/**
 * 慢sql
 * @author 王小费 whx5710@qq.com
 */
@Intercepts({
        // 拦截查询方法
        @Signature(type = StatementHandler.class,method = "query",args = {Statement.class, ResultHandler.class}),
        // 拦截更新方法（insert/update/delete）
        @Signature(type = StatementHandler.class,method = "update", args = {Statement.class})
})
@Component
public class SlowSqlInterceptor implements Interceptor {

    private final String primary;
    private final Map<String, DataSourceProperty> dataSourceMap;
    private final Logger log = LoggerFactory.getLogger(SlowSqlInterceptor.class);

    public SlowSqlInterceptor(DynamicDataSourceProperties properties) {
        this.primary = properties.getSysDataSource().getPrimary();
        this.dataSourceMap = properties.getDynamic();
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 获取当前数据源的慢查询阈值（线程安全）
        String dbKey = DynamicDataSourceHolder.getDynamicDataSourceKey();
        dbKey = dbKey == null ? primary : dbKey;

        long slowThreshold = 1000L; // 默认阈值
        DataSourceProperty property = dataSourceMap.get(dbKey);
        if (property != null && property.getHikari() != null) {
            slowThreshold = property.getHikari().getSlowThreshold();
        }

        try {
            return invocation.proceed();
        } finally {
            long costTime = System.currentTimeMillis() - startTime;

            if (costTime > slowThreshold) {
                StatementHandler handler = (StatementHandler) invocation.getTarget();
                String sql = handler.getBoundSql().getSql();
                Object params = handler.getBoundSql().getParameterObject();
                if(log.isWarnEnabled()){
                    log.warn("[慢查询警告] 数据源: {}, 执行时间: {}ms, 阈值: {}ms, SQL: {}, 参数: {}",
                            dbKey, costTime, slowThreshold, sql, params);
                }else{
                    log.warn("[慢查询警告] 数据源: {}, 执行时间: {}ms, 阈值: {}ms, SQL: {}",
                            dbKey, costTime, slowThreshold, sql);
                }
            } else if (log.isDebugEnabled()) {
                log.debug("[SQL监控] 数据源: {}, 执行时间: {}ms", dbKey, costTime);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 配置已从数据源读取，此方法保留为空或删除
    }
}
