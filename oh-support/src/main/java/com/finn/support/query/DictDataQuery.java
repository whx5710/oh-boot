package com.finn.support.query;

import jakarta.validation.constraints.NotNull;
import com.finn.framework.query.Query;

/**
 * 字典数据
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class DictDataQuery extends Query {
    /**
     * 字典类型ID
     */
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    public Long getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
    }
}
