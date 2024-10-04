package com.iris.sys.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.iris.framework.common.utils.DateUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典类型
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Schema(description = "字典类型")
public class SysDictTypeVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    @Schema(description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    @Schema(description = "来源  0：字典数据  1：动态SQL")
    private Integer dictSource;

    @Schema(description = "动态sql")
    private String dictSql;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
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
}