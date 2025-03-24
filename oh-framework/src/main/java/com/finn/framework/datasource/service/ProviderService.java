package com.finn.framework.datasource.service;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.Tools;
import com.finn.framework.datasource.annotations.TableName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 通用provider,拼接增删查改，通过 @InsertProvider、@UpdateProvider等注解操作，减少sql编写<br/>
 * @author 王小费 whx5710@qq.com
 */
public class ProviderService {

    private final Logger log = LoggerFactory.getLogger(ProviderService.class);

    /**
     * 获取表名
     * @param clazz c
     * @return 表名
     */
    protected String getTableName(Class<?> clazz){
        // 获取表名
        TableName apoTable = clazz.getAnnotation(TableName.class);
        if(apoTable == null){
            log.warn("实体类没指定表名（@TableName），默认使用类名作为表名");
            String s = clazz.getName();
            int i = s.lastIndexOf(".");
            return Tools.humpToLine(s.substring(i + 1));
        }else{
            String tableName = apoTable.value();
            if(tableName == null || tableName.isEmpty()){
                throw new ServerException("未指定表名，执行失败");
            }else{
                return tableName;
            }
        }
    }

}
