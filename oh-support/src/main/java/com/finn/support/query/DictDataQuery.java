package com.finn.support.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import com.finn.framework.query.Query;

/**
 * 字典数据
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "字典数据查询")
public class DictDataQuery extends Query {
    @Schema(description = "字典类型ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    public Long getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
    }
}
