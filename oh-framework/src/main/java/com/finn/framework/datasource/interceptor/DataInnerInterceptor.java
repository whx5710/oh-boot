package com.finn.framework.datasource.interceptor;

import com.finn.core.utils.ReflectUtil;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 数据拦截器
 * 1、自动填充基础数据
 * 2、数据范围过滤
 * @author 王小费 whx5710@qq.com
 */
@Intercepts({
        @Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Component
public class DataInnerInterceptor implements Interceptor {

    private final Logger log = LoggerFactory.getLogger(DataInnerInterceptor.class);

    // 创建时间
    private final static String CREATE_TIME = "createTime";
    // 创建人ID
    private final static String CREATOR = "creator";
    // 修改时间
    private final static String UPDATE_TIME = "updateTime";
    // 修改人ID
    private final static String UPDATER = "updater";
    // 机构ID
    private final static String ORG_ID = "orgId";
    // 数据状态标记，0删除1有效
    private final static String DB_STATUS = "dbStatus";
    // 租户ID
    private final static String TENANT_ID = "tenantId";

    private final static String SQL_FILTER = "sqlFilter";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        if (ms.getSqlCommandType().equals(SqlCommandType.INSERT)) { // 新增
            autoInsertFill(invocation);
            return invocation.proceed();
        }
        if (ms.getSqlCommandType().equals(SqlCommandType.UPDATE)) { // 更新
            autoUpdateFill(invocation);
            return invocation.proceed();
        }
        if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) { // 查询过滤
            dataFilter(args);
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    /**
     * 新增自动填充
     * @param invocation p
     */
    private void autoInsertFill(Invocation invocation) {
        UserDetail user = SecurityUser.getUser();
        LocalDateTime date = LocalDateTime.now();

        Object[] args = invocation.getArgs();
        Object params = args[1];
        if (user != null && user.getId() != null) {
            // 创建人ID
            try {
                Object object = ReflectUtil.getValue(params, CREATOR);
                if(object == null){
                    ReflectUtil.setValue(params, CREATOR, user.getId(), false);
                }
            }catch (NoSuchFieldException e){
                log.warn("无{}属性！", CREATOR);
            }
            // 所属组织
            try {
                ReflectUtil.setValue(params, ORG_ID, user.getOrgId(), false);
            }catch (NoSuchFieldException e){
                log.warn("无{}属性！", ORG_ID);
            }
            // 租户ID
            try {
                Object object = ReflectUtil.getValue(params, TENANT_ID);
                if(object == null || object.equals("")){
                    ReflectUtil.setValue(params, TENANT_ID, user.getTenantId(), false);
                }
            }catch (NoSuchFieldException e){
                log.warn("无{}属性！", TENANT_ID);
            }
        }
        // 创建时间
        try {
            Object object = ReflectUtil.getValue(params, CREATE_TIME);
            if(object == null){
                ReflectUtil.setValue(params, CREATE_TIME, date, false);
            }
        }catch (NoSuchFieldException e){
            log.warn("无{}属性！", CREATE_TIME);
        }
        // 有效标识
        try {
            Object object = ReflectUtil.getValue(params, DB_STATUS);
            if(object == null){
                ReflectUtil.setValue(params, DB_STATUS, 1, false);
            }
        }catch (NoSuchFieldException e){
            log.warn("无{}属性！", DB_STATUS);
        }
    }

    /**
     * 更新自动填充
     * @param invocation p
     */
    private void autoUpdateFill(Invocation invocation) {
        UserDetail user = SecurityUser.getUser();
        LocalDateTime date = LocalDateTime.now();

        Object[] args = invocation.getArgs();
        Object params = args[1];
        if (user != null && user.getId() != null) {
            // 更新人ID
            try {
                Object object = ReflectUtil.getValue(params, UPDATER);
                if(object == null){
                    ReflectUtil.setValue(params, UPDATER, user.getId(), false);
                }
            }catch (NoSuchFieldException e){
                log.warn("无{}属性！", UPDATER);
            }
            // 租户ID
            try {
                Object object = ReflectUtil.getValue(params, TENANT_ID);
                if(object == null || object.equals("")){
                    ReflectUtil.setValue(params, TENANT_ID, user.getTenantId(), false);
                }
            }catch (NoSuchFieldException e){
                log.warn("无{}属性！", TENANT_ID);
            }
        }
        // 更新时间
        try {
            Object object = ReflectUtil.getValue(params, UPDATE_TIME);
            if(object == null){
                ReflectUtil.setValue(params, UPDATE_TIME, date, false);
            }
        }catch (NoSuchFieldException e){
            log.warn("无{}属性！", UPDATE_TIME);
        }
        // 有效标识
        try {
            Object object = ReflectUtil.getValue(params, DB_STATUS);
            if(object == null){
                ReflectUtil.setValue(params, DB_STATUS, 1, false);
            }
        }catch (NoSuchFieldException e){
            log.warn("无{}属性！", DB_STATUS);
        }
    }

    /**
     * 查询数据过滤
     * @param args
     */
    private void dataFilter(Object[] args){
        Object params = args[1];
        // 查询过滤
        if (params != null) {
            // 数据过滤SQL
            String sqlFilter = null;
            if(params instanceof HashMap<?,?> hashMap){
                if(hashMap.containsKey(SQL_FILTER) && hashMap.get(SQL_FILTER) != null){
                    sqlFilter = (String) hashMap.get(SQL_FILTER);
                }
            }else{
                try {
                    Object object = ReflectUtil.getValue(params, SQL_FILTER);
                    if(object != null){
                        sqlFilter = (String) object;
                    }
                }catch (NoSuchFieldException e){
                    log.warn("无{}属性！", SQL_FILTER);
                }
            }
            if(sqlFilter != null){
                BoundSql boundSql = (BoundSql) args[5];
                try {
                    ReflectUtil.setValue(boundSql, "sql", getSelect(boundSql.getSql(), sqlFilter));
                }catch (NoSuchFieldException e){
                    log.warn("无{}属性！", "sql");
                }
            }
        }
    }

    /**
     * 拼接SQL
     * @param buildSql 原SQL
     * @param sqlFilter 添加的过滤sql
     * @return 拼接好的SQL
     */
    private String getSelect(String buildSql, String sqlFilter){
        log.debug("原始sql = {}", buildSql);
        log.debug("过滤sql = {}", sqlFilter);
        String where = "WHERE";
        String sqlTmp = buildSql.toUpperCase();
        String sql = "";
        if(sqlTmp.contains(where)){
            int i = sqlTmp.lastIndexOf(where) + where.length();
            sql = buildSql.substring(0, i) + sqlFilter + " and" + buildSql.substring(i);
        }else{
            sql = buildSql + sqlFilter;
        }
        log.debug("拼接后的sql = {}", sql);
        return sql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}