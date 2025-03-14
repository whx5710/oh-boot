package com.finn.core.entity;

public class Parameter extends SuperEntity{

    /**
     * 列名 user_name
     */
    String colName;
    /**
     * 字段 userName
     */
    String field;
    // 值
    Object value;
    // 表达式
    String expression = "eq";

    public Parameter(){

    }

    /**
     * 初始化参数
     * @param field 字段
     * @param expression 表达式
     * @param value 值
     * @param colName 列名
     */
    public Parameter(String field, String expression, Object value, String colName){
        this.field = field;
        this.expression = expression;
        this.value = value;
        this.colName = colName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
