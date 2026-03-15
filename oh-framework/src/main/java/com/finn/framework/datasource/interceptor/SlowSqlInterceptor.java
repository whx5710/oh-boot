package com.finn.framework.datasource.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Statement;
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

    // 慢查询阈值（毫秒），可通过配置文件注入
    private long slowThreshold = 500;

    private final Logger log = LoggerFactory.getLogger(SlowSqlInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 1. 记录开始时间
        long startTime = System.currentTimeMillis();

        try {
            // 2. 执行原方法（继续SQL执行流程）
            return invocation.proceed();
        } finally {
            // 3. 计算执行耗时（无论成功失败都记录）
            long costTime = System.currentTimeMillis() - startTime;

            // 5. 判断是否慢查询
            if (costTime > slowThreshold) {
                // 4. 获取SQL语句和参数
                StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
                String sql = statementHandler.getBoundSql().getSql();  // 获取SQL语句（带?占位符）
                Object parameterObject = statementHandler.getBoundSql().getParameterObject();  // 获取参数
                log.warn("[慢查询警告] 执行时间: {}ms, SQL: {}, 参数: {}",
                        costTime, sql, parameterObject);
            }else{
                log.debug("[SQL监控] 执行时间: {}ms", costTime);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        // 从配置文件读取阈值（如application.yml中配置）
        String threshold = properties.getProperty("slowThreshold");
        if (threshold != null) {
            slowThreshold = Long.parseLong(threshold);
        }
    }
}
