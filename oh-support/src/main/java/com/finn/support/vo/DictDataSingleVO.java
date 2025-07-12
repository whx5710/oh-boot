package com.finn.support.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典数据-简单
 * @author 王小费
 */
public class DictDataSingleVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 字典类型ID
     */
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    private String dictLabel;

    /**
     * 标签样式
     */
    private String labelClass;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 排序
     */
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictLabel() {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }

    public String getLabelClass() {
        return labelClass;
    }

    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

}
