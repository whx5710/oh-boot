package com.iris.framework.datasource.interceptor;

import cn.hutool.core.util.ReflectUtil;
import com.iris.framework.security.user.SecurityUser;
import com.iris.framework.security.user.UserDetail;
//import net.sf.jsqlparser.JSQLParserException;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.StringValue;
//import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
//import net.sf.jsqlparser.parser.CCJSqlParserUtil;
//import net.sf.jsqlparser.statement.select.PlainSelect;
//import net.sf.jsqlparser.statement.select.Select;
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

    private final static String SQL_FILTER = "sqlFilter";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        // 新增
        if (ms.getSqlCommandType().equals(SqlCommandType.INSERT)) {
            autoInsertFill(invocation);
        }
        // 更新
        if (ms.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
            autoUpdateFill(invocation);
        }
        // 查询过滤
        if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
            dataFilter(args);
        }
        return invocation.proceed();
    }

    /**
     * 新增自动填充
     * @param invocation
     */
    private void autoInsertFill(Invocation invocation) {
        UserDetail user = SecurityUser.getUser();
        LocalDateTime date = LocalDateTime.now();

        Object[] args = invocation.getArgs();
        Object params = args[1];
        if (user != null && user.getId() != null) {
            // 创建人ID
            if (ReflectUtil.hasField(params.getClass(), CREATOR) && ReflectUtil.getFieldValue(params, CREATOR) == null) {
                ReflectUtil.setFieldValue(params, CREATOR, user.getId());
            }
            // 所属组织
            if (ReflectUtil.hasField(params.getClass(), ORG_ID) && ReflectUtil.getFieldValue(params, ORG_ID) == null) {
                ReflectUtil.setFieldValue(params, ORG_ID, user.getOrgId());
            }
        }
        // 创建时间
        if (ReflectUtil.hasField(params.getClass(), CREATE_TIME) && ReflectUtil.getFieldValue(params, CREATE_TIME) == null) {
            ReflectUtil.setFieldValue(params, CREATE_TIME, date);
        }
        // 有效标识
        if (ReflectUtil.hasField(params.getClass(), DB_STATUS) && ReflectUtil.getFieldValue(params, DB_STATUS) == null) {
            ReflectUtil.setFieldValue(params, DB_STATUS, 1);
        }
    }

    /**
     * 更新自动填充
     * @param invocation
     */
    private void autoUpdateFill(Invocation invocation) {
        UserDetail user = SecurityUser.getUser();
        LocalDateTime date = LocalDateTime.now();

        Object[] args = invocation.getArgs();
        Object params = args[1];
        if (user != null && user.getId() != null) {
            // 创建人ID
            if (ReflectUtil.hasField(params.getClass(), UPDATER) && ReflectUtil.getFieldValue(params, UPDATER) == null) {
                ReflectUtil.setFieldValue(params, UPDATER, user.getId());
            }
        }
        // 更新时间
        if (ReflectUtil.hasField(params.getClass(), UPDATE_TIME) && ReflectUtil.getFieldValue(params, UPDATE_TIME) == null) {
            ReflectUtil.setFieldValue(params, UPDATE_TIME, date);
        }
        // 有效标识
        if (ReflectUtil.hasField(params.getClass(), DB_STATUS) && ReflectUtil.getFieldValue(params, DB_STATUS) == null) {
            ReflectUtil.setFieldValue(params, DB_STATUS, 1);
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
            if("java.util.HashMap".equals(params.getClass().getName())){
                HashMap hashMap = (HashMap) params;
                if(hashMap.containsKey(SQL_FILTER) && hashMap.get(SQL_FILTER) != null){
                    sqlFilter = (String) hashMap.get(SQL_FILTER);
                }
            }else{
                if((ReflectUtil.hasField(params.getClass(), SQL_FILTER) && ReflectUtil.getFieldValue(params, SQL_FILTER) != null)){
                    sqlFilter = (String) ReflectUtil.getFieldValue(params, SQL_FILTER);
                }
            }
            if(sqlFilter != null){
                BoundSql boundSql = (BoundSql) args[5];
                ReflectUtil.setFieldValue(boundSql, "sql", getSelect(boundSql.getSql(), sqlFilter));
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
        /*try {
            Select select = (Select) CCJSqlParserUtil.parse(buildSql);
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            Expression expression = plainSelect.getWhere();
            if(expression == null){
                plainSelect.setWhere(new StringValue(sqlFilter));
            }else{
                AndExpression andExpression =  new AndExpression(expression, new StringValue(sqlFilter));
                plainSelect.setWhere(andExpression);
            }
            // 需用双引号，比如 concat('%', ?)
            return select.toString().replaceAll("'", "");
        }catch (JSQLParserException e){
            log.error("数据过滤SQL拼接失败，请检查!{}",e.getMessage());
            return buildSql;
        }*/
        log.warn("查询过滤功能未实现");
        return buildSql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}