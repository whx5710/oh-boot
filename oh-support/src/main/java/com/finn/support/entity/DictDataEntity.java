package com.finn.support.entity;

import com.finn.framework.datasource.annotations.TableField;
import com.finn.framework.datasource.annotations.TableName;
import com.finn.framework.entity.BaseEntity;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@TableName("sys_dict_data")
public class DictDataEntity extends BaseEntity {
    /**
     * 字典类型ID
     */
    @TableField("dict_type_id")
    private Long dictTypeId;
    /**
     * 字典标签
     */
    @TableField("dict_label")
    private String dictLabel;
    /**
     * 字典值
     */
    @TableField("dict_value")
    private String dictValue;
    /**
     * 标签样式
     */
    @TableField("label_class")
    private String labelClass;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sort;

    public Long getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public String getDictLabel() {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getLabelClass() {
        return labelClass;
    }

    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
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

}
