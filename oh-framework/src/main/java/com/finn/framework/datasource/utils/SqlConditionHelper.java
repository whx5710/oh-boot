package com.finn.framework.datasource.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.finn.core.exception.ServerException;
import com.finn.framework.datasource.service.FieldConditionDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * sql语句where条件处理辅助类
 * @author 王小费 whx5710@qq.com
 */
@Component
public class SqlConditionHelper {
    private final Logger log = LoggerFactory.getLogger(SqlConditionHelper.class);

    private static final String LEFT = "LEFT";

    private static final String RIGHT = "RIGHT";

    private final FieldConditionDecision conditionDecision;

    public SqlConditionHelper(FieldConditionDecision conditionDecision) {
        this.conditionDecision = conditionDecision;
    }

    /**
     * 为sql语句添加指定where条件
     *
     * @param sqlStatement
     * @param fieldName
     * @param fieldValue
     */
    public void addStatementCondition(SQLStatement sqlStatement, String fieldName, String fieldValue) {
        if (sqlStatement instanceof SQLSelectStatement) {
            SQLSelectQueryBlock queryObject = (SQLSelectQueryBlock) ((SQLSelectStatement) sqlStatement).getSelect().getQuery();
            addSelectStatementCondition(queryObject, queryObject.getFrom(), fieldName, fieldValue);
        } else if (sqlStatement instanceof SQLUpdateStatement updateStatement) {
            addUpdateStatementCondition(updateStatement, fieldName, fieldValue);
        } else if (sqlStatement instanceof SQLDeleteStatement deleteStatement) {
            addDeleteStatementCondition(deleteStatement, fieldName, fieldValue);
        } else if (sqlStatement instanceof SQLInsertStatement insertStatement) {
            addInsertStatementCondition(insertStatement, fieldName, fieldValue);
        }else{
            log.warn("为sql语句添加指定where条件：没有拦截到 {}", sqlStatement.getClass().getName());
        }
    }

    /**
     * 为insert语句添加where条件
     *
     * @param insertStatement
     * @param fieldName
     * @param fieldValue
     */
    private void addInsertStatementCondition(SQLInsertStatement insertStatement, String fieldName, String fieldValue) {
        if (insertStatement != null) {
            SQLSelect sqlSelect = insertStatement.getQuery();
            if (sqlSelect != null) {
                SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) sqlSelect.getQuery();
                addSelectStatementCondition(selectQueryBlock, selectQueryBlock.getFrom(), fieldName, fieldValue);
            }
        }
    }


    /**
     * 为delete语句添加where条件
     *
     * @param deleteStatement
     * @param fieldName
     * @param fieldValue
     */
    private void addDeleteStatementCondition(SQLDeleteStatement deleteStatement, String fieldName, String fieldValue) {
        SQLExpr where = deleteStatement.getWhere();
        //添加子查询中的where条件
        addSQLExprCondition(where, fieldName, fieldValue);
        SQLExpr newCondition = newEqualityCondition(deleteStatement.getTableName().getSimpleName(),
                deleteStatement.getTableSource().getAlias(), fieldName, fieldValue, where);
        deleteStatement.setWhere(newCondition);
    }

    /**
     * where中添加指定筛选条件
     *
     * @param where      源where条件
     * @param fieldName
     * @param fieldValue
     */
    private void addSQLExprCondition(SQLExpr where, String fieldName, String fieldValue) {
        if (where instanceof SQLInSubQueryExpr inWhere) {
            SQLSelect subSelectObject = inWhere.getSubQuery();
            SQLSelectQueryBlock subQueryObject = (SQLSelectQueryBlock) subSelectObject.getQuery();
            addSelectStatementCondition(subQueryObject, subQueryObject.getFrom(), fieldName, fieldValue);
        } else if (where instanceof SQLBinaryOpExpr opExpr) {
            SQLExpr left = opExpr.getLeft();
            SQLExpr right = opExpr.getRight();
            addSQLExprCondition(left, fieldName, fieldValue);
            addSQLExprCondition(right, fieldName, fieldValue);
        } else if (where instanceof SQLQueryExpr) {
            SQLSelectQueryBlock selectQueryBlock = (SQLSelectQueryBlock) (((SQLQueryExpr) where).getSubQuery()).getQuery();
            addSelectStatementCondition(selectQueryBlock, selectQueryBlock.getFrom(), fieldName, fieldValue);
        }else{
            if(where == null){
                log.warn("where中添加指定筛选条件：where条件为空");
            }else{
                log.warn("where中添加指定筛选条件：没有拦截到 {}", where.getClass().getName());
            }
        }
    }

    /**
     * 为update语句添加where条件
     *
     * @param updateStatement
     * @param fieldName
     * @param fieldValue
     */
    private void addUpdateStatementCondition(SQLUpdateStatement updateStatement, String fieldName, String fieldValue) {
        SQLExpr where = updateStatement.getWhere();
        //添加子查询中的where条件
        addSQLExprCondition(where, fieldName, fieldValue);
        SQLTableSource sqlTableSource = updateStatement.getTableSource();
        SQLExpr newCondition = null;
        if(sqlTableSource instanceof SQLJoinTableSource sqlJoinTableSource){
            if(sqlJoinTableSource.getJoinType().name().contains(LEFT)
                    || sqlJoinTableSource.getJoinType().name().contains("INNER_JOIN")){
                newCondition = newEqualityCondition(updateStatement.getTableName().getSimpleName(),
                        sqlJoinTableSource.getLeft().getAlias(), fieldName, fieldValue, where);
            }else if(sqlJoinTableSource.getJoinType().name.contains(RIGHT)){
                newCondition = newEqualityCondition(updateStatement.getTableName().getSimpleName(),
                        sqlJoinTableSource.getRight().getAlias(), fieldName, fieldValue, where);
            }else{
                newCondition = newEqualityCondition(updateStatement.getTableName().getSimpleName(),
                        updateStatement.getTableSource().getAlias(), fieldName, fieldValue, where);
            }
        }else{
            newCondition = newEqualityCondition(updateStatement.getTableName().getSimpleName(),
                    updateStatement.getTableSource().getAlias(), fieldName, fieldValue, where);
        }
        updateStatement.setWhere(newCondition);
    }

    /**
     * 给一个查询对象添加一个where条件
     *
     * @param queryObject
     * @param fieldName
     * @param fieldValue
     */
    private void addSelectStatementCondition(SQLSelectQueryBlock queryObject, SQLTableSource from, String fieldName, String fieldValue) {
        if (fieldName ==null || fieldName.isEmpty() || from == null || queryObject == null){
            return;
        }
        SQLExpr originCondition = queryObject.getWhere();
        switch (from) {
            case SQLExprTableSource sqlExprTableSource -> {
                String tableName = sqlExprTableSource.getTableName();
                String alias = from.getAlias();
                SQLExpr newCondition = newEqualityCondition(tableName, alias, fieldName, fieldValue, originCondition);
                queryObject.setWhere(newCondition);
            }
            case SQLJoinTableSource joinObject -> {
                SQLTableSource left = joinObject.getLeft();
                SQLTableSource right = joinObject.getRight();
                if(joinObject.getJoinType().name().contains(LEFT)){
                    addSelectStatementCondition(queryObject, left, fieldName, fieldValue);
                } else if (joinObject.getJoinType().name().contains(RIGHT)) {
                    addSelectStatementCondition(queryObject, right, fieldName, fieldValue);
                }else{
                    addSelectStatementCondition(queryObject, left, fieldName, fieldValue);
                    addSelectStatementCondition(queryObject, right, fieldName, fieldValue);
                }
            }
            case SQLSubqueryTableSource sqlSubqueryTableSource -> {
                SQLSelect subSelectObject = sqlSubqueryTableSource.getSelect();
                SQLSelectQueryBlock subQueryObject = (SQLSelectQueryBlock) subSelectObject.getQuery();
                addSelectStatementCondition(subQueryObject, subQueryObject.getFrom(), fieldName, fieldValue);
            }
            default -> throw new ServerException("未处理的异常");
        }
    }

    /**
     * 根据原来的condition创建一个新的condition
     *
     * @param tableName       表名称
     * @param tableAlias      表别名
     * @param fieldName
     * @param fieldValue      租户ID值
     * @param originCondition
     * @return
     */
    private SQLExpr newEqualityCondition(String tableName, String tableAlias, String fieldName, String fieldValue, SQLExpr originCondition) {
        // 如果不需要设置条件
        if (!conditionDecision.adjudge(tableName, fieldName)){
            return originCondition;
        }
        // 如果条件字段不允许为空
        /*if (fieldValue == null && !conditionDecision.isAllowNullValue()){
            return originCondition;
        }*/
        String filedName = (tableAlias == null || tableAlias.isEmpty()) ? fieldName : tableAlias + "." + fieldName;
        SQLExpr condition = new SQLBinaryOpExpr(new SQLIdentifierExpr(filedName), new SQLCharExpr(fieldValue), SQLBinaryOperator.Equality);
        return SQLUtils.buildCondition(SQLBinaryOperator.BooleanAnd, condition, false, originCondition);
    }


    /*public static void main(String[] args) {
//        String sql = "select * from user s  ";
//        String sql = "select * from user s where s.name='333'";
//        String sql = "select * from (select * from tab t where id = 2 and name = 'wenshao') s where s.name='333'";
//        String sql="select u.*,g.name from user u join user_group g on u.groupId=g.groupId where u.name='123'";
//        String sql = "update user set name=? where id =(select id from user s)";
//        String sql = "update sys_user as a inner JOIN sys_user_role as b on a.id = b.user_id set a.name = b.role_name " +
//                "where a.id = b.user_id";
//        String sql = "update sys_user  set user_name = 'whx' where id = 123";
        String sql = "delete from user where id = ( select id from user s )";
//        String sql = "insert into user (id,name) select g.id,g.name from user_group g where id=1";
//        String sql = "select * from sys_user a INNER JOIN sys_user_role b on a.id = b.user_id";
//        String sql = "select * from sys_user a LEFT JOIN sys_user_role b on a.id = b.user_id";
//        String sql = "select * from sys_user a right JOIN sys_user_role b on a.id = b.user_id";
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
        SQLStatement sqlStatement = statementList.get(0);
        //决策器定义
        SqlConditionHelper helper = new SqlConditionHelper(new FieldConditionDecision(new MultiTenantProperties()));
        //添加多租户条件，domain是字段名，whx是筛选值
        helper.addStatementCondition(sqlStatement, "domain", "whx");
        System.out.println("源sql：" + sql);
        System.out.println("修改后sql:" + SQLUtils.toSQLString(statementList, JdbcConstants.MYSQL));
    }*/
}
