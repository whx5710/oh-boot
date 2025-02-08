package com.finn.framework.datasource.service;

import com.finn.framework.common.properties.MultiTenantProperties;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表字段条件决策器
 * 用于决策某个表是否需要添加某个字段过滤条件
 * @author 王小费 whx5710@qq.com
 */
@Component
public class FieldConditionDecision {
    private final MultiTenantProperties multiTenantProperties;
    public FieldConditionDecision(MultiTenantProperties multiTenantProperties){
        this.multiTenantProperties = multiTenantProperties;
    }

    /**
     * 条件字段是否运行null值
     * @return
     */
    public Boolean isAllowNullValue(){
        return false;
    }
    /**
     * 判决某个表是否需要添加某个字段过滤
     *
     * @param tableName   表名称
     * @param fieldName   字段名称
     * @return 返回 true 则需要拼接SQL，隔离数据
     */
    public Boolean adjudge(String tableName, String fieldName){
        // 满足匹配
        String tableRegex = multiTenantProperties.getTablePattern();
        Pattern pattern = Pattern.compile(tableRegex);
        Matcher matcher = pattern.matcher(tableName);
        return matcher.matches() && !multiTenantProperties.getIgnoreTable().contains(tableName);
    }

}
