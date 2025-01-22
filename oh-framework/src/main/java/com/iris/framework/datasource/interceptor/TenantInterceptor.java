package com.iris.framework.datasource.interceptor;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.iris.framework.common.properties.MultiTenantProperties;
import com.iris.framework.datasource.utils.SqlConditionHelper;
import com.iris.framework.security.user.SecurityUser;
import com.iris.framework.security.user.UserDetail;
import com.iris.framework.utils.TenantContextHolder;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.*;

/**
 * 多租户数据隔离插件,where 条件语句后面拼接租户过滤条件
 * @author 王小费 whx5710@qq.com
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class TenantInterceptor implements Interceptor {
    // private final Logger log = LoggerFactory.getLogger(TenantInterceptor.class);

    private final SqlConditionHelper conditionHelper;

    private final MultiTenantProperties multiTenantProperties;

    public TenantInterceptor(SqlConditionHelper conditionHelper, MultiTenantProperties multiTenantProperties){
        this.conditionHelper = conditionHelper;
        this.multiTenantProperties = multiTenantProperties;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(multiTenantProperties.isEnable()){
            String tenantId = TenantContextHolder.getTenant(); // 线程名
            //租户id为空时不做处理
            if (tenantId == null) {
                return invocation.proceed();
            }
            // 超级管理员不过滤
            UserDetail user = SecurityUser.getUser();
            if(user != null && user.getSuperAdmin() == 1){
                return invocation.proceed();
            }
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            BoundSql boundSql = statementHandler.getBoundSql();
            String newSql = addTenantCondition(boundSql.getSql(), tenantId);
            // log.debug("过滤租户SQL = {}", newSql);
            MetaObject boundSqlMeta = SystemMetaObject.forObject(boundSql); // getMetaObject(boundSql);
            //把新sql设置到boundSql
            boundSqlMeta.setValue("sql", newSql);
        }
        return invocation.proceed();
    }


    /**
     * 给sql语句where添加租户id过滤条件
     *
     * @param sql      要添加过滤条件的sql语句
     * @param tenantId 当前的租户id
     * @return 添加条件后的sql语句
     */
    private String addTenantCondition(String sql, String tenantId) {
        // log.debug("原始SQL = {}", sql);
        if (sql == null || sql.isEmpty() || multiTenantProperties.getTenantIdField() == null
                || multiTenantProperties.getTenantIdField().isEmpty()){
            return sql;
        }
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, multiTenantProperties.getDialect());
        if (statementList == null || statementList.isEmpty()){
            return sql;
        }
        SQLStatement sqlStatement = statementList.getFirst();
        // 拼接where条件
        conditionHelper.addStatementCondition(sqlStatement, multiTenantProperties.getTenantIdField(), tenantId);
        // 生成新的sql
        return SQLUtils.toSQLString(statementList, DbType.valueOf(multiTenantProperties.getDialect()));
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
