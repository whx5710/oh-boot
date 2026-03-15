package com.finn.framework.tenant;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 基于 JSqlParser 4.9 的 SQL 租户过滤器
 * 自动为 SQL 添加租户 ID 过滤条件
 * @author 王小费 whx5710@qq.com
 */
@Component
public class TenantSqlFilter {

    // 需要忽略租户过滤的表名集合（如系统表、配置表等）
    private final FieldConditionDecision conditionDecision;

    public TenantSqlFilter(FieldConditionDecision conditionDecision) {
        this.conditionDecision = conditionDecision;
    }

    /**
     * 主入口：为 SQL 添加租户过滤条件
     * @param originalSql sql
     * @param fieldName 字段名
     * @param fieldValue 租户ID
     * @return new sql
     * @throws JSQLParserException e
     */
    public String addTenantCondition(String originalSql, String fieldName, String fieldValue) throws JSQLParserException {
        if (fieldValue == null || fieldValue.isEmpty()) {
            return originalSql;
        }
        Statement statement = CCJSqlParserUtil.parse(originalSql);
        if (statement instanceof Select) {
            processSelect((Select) statement, fieldName, fieldValue);
        } else if (statement instanceof Update) {
            processUpdate((Update) statement, fieldName, fieldValue);
        } else if (statement instanceof Delete) {
            processDelete((Delete) statement, fieldName, fieldValue);
        } else if (statement instanceof Insert) {
            processInsert((Insert) statement, fieldName, fieldValue);
        }
        return statement.toString();
    }

    /**
     * 处理 SELECT 语句
     */
    private void processSelect(Select select, String fieldName, String fieldValue) {
        if (select instanceof PlainSelect) {
            processPlainSelect((PlainSelect) select, fieldName, fieldValue);
        } else if (select instanceof SetOperationList setOpList) {
            for (Select subSelect : setOpList.getSelects()) {
                processSelect(subSelect, fieldName, fieldValue);
            }
        } else if (select instanceof ParenthesedSelect parenthesedSelect) {
            processSelect(parenthesedSelect.getSelect(), fieldName, fieldValue);
        }

        // 处理 WITH 子句（CTE）
        if (select.getWithItemsList() != null) {
            for (WithItem withItem : select.getWithItemsList()) {
                processWithItem(withItem, fieldName, fieldValue);
            }
        }
    }

    /**
     * 处理 PlainSelect（普通查询）
     */
    private void processPlainSelect(PlainSelect plainSelect, String fieldName, String fieldValue) {
        FromItem fromItem = plainSelect.getFromItem();

        // 处理主表
        if (fromItem instanceof Table table) {
            if (!shouldIgnoreTable(table.getName())) {
                Expression tenantCondition = createTenantCondition(table, fieldName, fieldValue);
                addWhereCondition(plainSelect, tenantCondition);
            }
        } else if (fromItem instanceof ParenthesedSelect parenthesedSelect) {
            processSelect(parenthesedSelect.getSelect(), fieldName, fieldValue);
        } else if (fromItem instanceof ParenthesedFromItem parenthesedFromItem) {
            processParenthesesFromItem(parenthesedFromItem, fieldName, fieldValue, null, false);
        }

        // 处理 JOIN 表
        handleJoins(plainSelect.getJoins(), fieldName, fieldValue);

        // 处理子查询
        processSubSelects(plainSelect, fieldName, fieldValue);
    }

    /**
     * 处理 ParenthesedFromItem
     */
    private void processParenthesesFromItem(ParenthesedFromItem parenthesedFromItem, String fieldName, String fieldValue,
                                            Join join, boolean addToOn) {
        FromItem innerFromItem = parenthesedFromItem.getFromItem();

        if (innerFromItem instanceof Table table) {
            if (!shouldIgnoreTable(table.getName())) {
                Expression tenantCondition = createTenantCondition(table, fieldName, fieldValue);
                if (addToOn && join != null) {
                    addJoinOnCondition(join, tenantCondition);
                }
            }
        } else if (innerFromItem instanceof ParenthesedSelect) {
            processSelect(((ParenthesedSelect) innerFromItem).getSelect(), fieldName, fieldValue);
        } else if (innerFromItem instanceof Select) {
            processSelect((Select) innerFromItem, fieldName, fieldValue);
        }

        // 处理 ParenthesedFromItem 内部的 JOINs
        handleJoins(parenthesedFromItem.getJoins(), fieldName, fieldValue);
    }
    // 处理 ParenthesedFromItem 内部的 JOINs
    private void handleJoins(List<Join> joins, String fieldName, String fieldValue){
        if (joins != null) {
            for (Join innerJoin : joins) {
                FromItem rightItem = innerJoin.getRightItem();
                if (rightItem instanceof Table joinTable) {
                    if (!shouldIgnoreTable(joinTable.getName())) {
                        Expression joinTenantCondition = createTenantCondition(joinTable, fieldName, fieldValue);
                        addJoinOnCondition(innerJoin, joinTenantCondition);
                    }
                } else if (rightItem instanceof ParenthesedSelect) {
                    processSelect(((ParenthesedSelect) rightItem).getSelect(), fieldName, fieldValue);
                } else if (rightItem instanceof ParenthesedFromItem) {
                    processParenthesesFromItem((ParenthesedFromItem) rightItem, fieldName, fieldValue, innerJoin, true);
                }
            }
        }
    }

    /**
     * 处理 WITH 子句（CTE）
     */
    private void processWithItem(WithItem withItem, String fieldName, String fieldValue) {
        Select select = withItem.getSelect();
        if (select != null) {
            processSelect(select, fieldName, fieldValue);
        }
    }

    /**
     * 处理子查询（WHERE、HAVING、SELECT 列表等）
     */
    private void processSubSelects(PlainSelect plainSelect, String fieldName, String fieldValue) {
        // 处理 SELECT 列表中的子查询
        List<SelectItem<?>> selectItems = plainSelect.getSelectItems();
        if (selectItems != null) {
            for (SelectItem<?> item : selectItems) {
                Expression expr = item.getExpression();
                processExpressionForSubSelect(expr, fieldName, fieldValue);
            }
        }

        // 处理 WHERE 中的子查询
        Expression where = plainSelect.getWhere();
        if (where != null) {
            processExpressionForSubSelect(where, fieldName, fieldValue);
        }

        // 处理 HAVING 中的子查询
        Expression having = plainSelect.getHaving();
        if (having != null) {
            processExpressionForSubSelect(having, fieldName, fieldValue);
        }
    }

    /**
     * 递归处理表达式中的子查询
     */
    private void processExpressionForSubSelect(Expression expr, String fieldName, String fieldValue) {
        switch (expr) {
            case null -> {
                return;
            }
            case ParenthesedSelect parenthesedSelect -> processSelect(parenthesedSelect.getSelect(), fieldName, fieldValue);
            case InExpression inExpr -> {
                Expression rightExpr = inExpr.getRightExpression();
                if (rightExpr instanceof ParenthesedSelect) {
                    processSelect(((ParenthesedSelect) rightExpr).getSelect(), fieldName, fieldValue);
                }
            }
            case ExistsExpression existsExpr -> {
                Expression rightExpr = existsExpr.getRightExpression();
                if (rightExpr instanceof ParenthesedSelect) {
                    processSelect(((ParenthesedSelect) rightExpr).getSelect(), fieldName, fieldValue);
                }
            }
            case AndExpression andExpr -> {
                processExpressionForSubSelect(andExpr.getLeftExpression(), fieldName, fieldValue);
                processExpressionForSubSelect(andExpr.getRightExpression(), fieldName, fieldValue);
            }
            case OrExpression orExpr -> {
                processExpressionForSubSelect(orExpr.getLeftExpression(), fieldName, fieldValue);
                processExpressionForSubSelect(orExpr.getRightExpression(), fieldName, fieldValue);
            }
            default -> {
            }
        }

    }

    /**
     * 处理 UPDATE 语句
     */
    private void processUpdate(Update update, String fieldName, String fieldValue) {
        Table table = update.getTable();
        if (!shouldIgnoreTable(table.getName())) {
            Expression tenantCondition = createTenantCondition(table, fieldName, fieldValue);
            addUpdateWhereCondition(update, tenantCondition);
        }
    }

    /**
     * 处理 DELETE 语句
     */
    private void processDelete(Delete delete, String fieldName, String fieldValue) {
        Table table = delete.getTable();
        if (!shouldIgnoreTable(table.getName())) {
            Expression tenantCondition = createTenantCondition(table, fieldName, fieldValue);
            addDeleteWhereCondition(delete, tenantCondition);
        }
    }

    /**
     * 处理 INSERT 语句
     */
    private void processInsert(Insert insert, String fieldName, String fieldValue) {
        Table table = insert.getTable();
        if (shouldIgnoreTable(table.getName())) {
            return;
        }

        if (insert.getSelect() != null) {
            processSelect(insert.getSelect(), fieldName, fieldValue);
        }
    }

    /**
     * 创建租户条件表达式：table.tenant_id = ?
     */
    private Expression createTenantCondition(Table table, String fieldName, String fieldValue) {
        String tableAlias = table.getAlias() != null ? table.getAlias().getName() : table.getName();

        Column tenantColumn = new Column(tableAlias + "." + fieldName);
        StringValue tenantValue = new StringValue(fieldValue);

        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(tenantColumn);
        equalsTo.setRightExpression(tenantValue);
        return equalsTo;
    }

    /**
     * 向 PlainSelect 添加 WHERE 条件
     */
    private void addWhereCondition(PlainSelect plainSelect, Expression condition) {
        Expression where = plainSelect.getWhere();
        if (where == null) {
            plainSelect.setWhere(condition);
        } else {
            AndExpression and = new AndExpression(where, condition);
            plainSelect.setWhere(and);
        }
    }

    /**
     * 向 JOIN 添加 ON 条件
     * 注意：JSqlParser 4.9 中 Join 使用 List<Expression> 存储 ON 表达式
     */
    private void addJoinOnCondition(Join join, Expression condition) {
        Collection<Expression> onExpressions = join.getOnExpressions();
        if (onExpressions == null || onExpressions.isEmpty()) {
            // 如果没有 ON 表达式，直接添加
            join.getOnExpressions().add(condition);
        } else {
            // 获取第一个 ON 表达式，与租户条件组合
            LinkedList<Expression>  ss = (LinkedList<Expression>) onExpressions;
            Expression first = ss.getFirst();
            ss.removeFirst();
            AndExpression and = new AndExpression(first, condition);
            ss.add(and);
        }
    }

    /**
     * 向 Update 添加 WHERE 条件
     */
    private void addUpdateWhereCondition(Update update, Expression condition) {
        Expression where = update.getWhere();
        if (where == null) {
            update.setWhere(condition);
        } else {
            AndExpression and = new AndExpression(where, condition);
            update.setWhere(and);
        }
    }

    /**
     * 向 Delete 添加 WHERE 条件
     */
    private void addDeleteWhereCondition(Delete delete, Expression condition) {
        Expression where = delete.getWhere();
        if (where == null) {
            delete.setWhere(condition);
        } else {
            AndExpression and = new AndExpression(where, condition);
            delete.setWhere(and);
        }
    }

    /**
     * 判断表是否需要忽略租户过滤
     */
    private boolean shouldIgnoreTable(String tableName) {
        return conditionDecision.shouldIgnoreTable(tableName.toLowerCase());
    }


    // ==================== 使用示例 ====================
    /*public static void main(String[] args) throws JSQLParserException {
        // 初始化过滤器
        TenantSqlFilter filter = new TenantSqlFilter(new FieldConditionDecision(new MultiTenantProperties()));

        // 测试各种 SQL
        String[] testSqls = {
                "select * from sys_user left join sys_dept on dept_id = sys_dept.id and sys_dept.db_status = 1 where user_name = 'abc'"
                ,
                "SELECT * FROM user WHERE status = 1",
                "SELECT u.*, o.order_no FROM user u LEFT JOIN orders o ON u.id = o.user_id",
                "SELECT * FROM user WHERE id IN (SELECT user_id FROM orders WHERE amount > 100)",
                "UPDATE user SET name = 'test' WHERE id = 1",
                "DELETE FROM user WHERE status = 0",
                "SELECT * FROM sys_config",
                "WITH cte AS (SELECT * FROM user) SELECT * FROM cte",
                "INSERT INTO user (name, email) VALUES ('张三', 'zhangsan@example.com')"
        };

        for (String sql : testSqls) {
            System.out.println("Original: " + sql);
            String filteredSql = filter.addTenantCondition(sql, "tenant_id", "1001");
            System.out.println("Filtered: " + filteredSql);
            System.out.println("---");
        }
    }*/

}