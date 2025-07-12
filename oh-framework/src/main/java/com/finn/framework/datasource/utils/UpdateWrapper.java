package com.finn.framework.datasource.utils;

import com.finn.core.aspect.FuncUtils;
import com.finn.core.utils.ReflectUtil;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * SQL 修改语句构建器
 * @author 王小费
 * @since 2025-06-28
 */
public class UpdateWrapper<T> extends Wrapper<T> {

    /**
     * 更新人 Long
     */
    private static final String UPDATER = "updater";
    /**
     * 更新时间 LocalDateTime
     */
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 初始化，构建SQL：update tableName
     * @param clazz
     * @param b 是否强制更新用户ID和时间
     * @return
     * @param <T>
     */
    public static <T> UpdateWrapper<T> of(Class<T> clazz, boolean b) {
        UpdateWrapper<T> params = new UpdateWrapper<>();
        SQL tmpSQL = new SQL();
        String tableName = getTableName(clazz);
        tmpSQL.UPDATE(tableName);

        // 拼接列名
        HashMap<String, String> colValue = buildColumn(clazz);
        params.setColValue(colValue); // 列名

        // 是否更新用户ID和时间
        if(b){
            // 更新人、更新时间
            if(colValue.containsKey(UPDATER)){
                UserDetail user = SecurityUser.getUser();
                if(user != null && user.getId() != null){
                    tmpSQL.SET(UPDATER + " = #{__SET"+ UPDATER +"}");
                    params.put("__SET" + UPDATER, user.getId());
                }
            }
            if(colValue.containsKey(UPDATE_TIME)){
                tmpSQL.SET("update_time = #{__SET"+ UPDATE_TIME +"}");
                params.put("__SET" + UPDATE_TIME, LocalDateTime.now());
            }
        }

        params.setSql(tmpSQL);
        return params;
    }

    /**
     * 初始化，默认更新用户ID和更新时间
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> UpdateWrapper<T> of(Class<T> clazz) {
        return of(clazz, true);
    }

    /**
     * 设置更新值
     * @param function f
     * @param value 值
     * @return p
     */
    public UpdateWrapper<T> set(FuncUtils<T> function, Object value) {
        String fieldName = ReflectUtil.getFieldName(function);
        String colName = getColName(fieldName);
        fieldName = "__SET" + fieldName;
        this.getSql().SET(colName + " = #{" + fieldName + "}");
        this.put(fieldName, value);
        return this;
    }
}
