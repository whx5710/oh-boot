package com.finn.support.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 字典类型
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@TableName("sys_dict_type")
public class DictTypeEntity extends BaseEntity {
    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;
    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 来源  0：字典数据  1：动态SQL
     */
    @TableField("dict_source")
    private Integer dictSource;
    /**
     * 动态sql
     */
    @TableField("dict_sql")
    private String dictSql;

    @TableField(exists = false)
    private String tenantId;

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDictSource() {
        return dictSource;
    }

    public void setDictSource(Integer dictSource) {
        this.dictSource = dictSource;
    }

    public String getDictSql() {
        return dictSql;
    }

    public void setDictSql(String dictSql) {
        this.dictSql = dictSql;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}