package com.finn.support.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部字典
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "全部字典")
public class DictVO {
    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典数据列表")
    private List<DictData> dataList = new ArrayList<>();

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public List<DictData> getDataList() {
        return dataList;
    }

    public void setDataList(List<DictData> dataList) {
        this.dataList = dataList;
    }

    @Schema(description = "字典数据")
    public static class DictData {
        @Schema(description = "字典标签")
        private String dictLabel;

        @Schema(description = "字典值")
        private String dictValue;

        @Schema(description = "标签样式")
        private String labelClass;

        public DictData(String dictLabel, String dictValue, String labelClass) {
            this.dictLabel = dictLabel;
            this.dictValue = dictValue;
            this.labelClass = labelClass;
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
    }
}
