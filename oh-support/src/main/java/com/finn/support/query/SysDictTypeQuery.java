package com.finn.support.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.finn.framework.query.Query;

/**
 * 字典类型
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "字典类型查询")
public class SysDictTypeQuery extends Query {
    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典名称")
    private String dictName;

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

}
