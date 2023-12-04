package com.iris.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import com.iris.framework.common.query.Query;

import java.util.Objects;

/**
 * 字典数据
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "字典数据查询")
public class SysDictDataQuery extends Query {
    @Schema(description = "字典类型ID", required = true)
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    public Long getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(Long dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysDictDataQuery that)) return false;
        return Objects.equals(getDictTypeId(), that.getDictTypeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDictTypeId());
    }
}
