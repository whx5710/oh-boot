package com.iris.system.vo;

import java.io.Serializable;
import java.io.Serial;

/**
 * 统计分析
 * 2023-11-27
 * @author 王小费 whx5710@qq.com
 */
public class AnalysisVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String label;

    /**
     * 分析时间
     */
    private String analysisTime;

    /**
     * 统计值
     */
    private String analysisValue;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(String analysisTime) {
        this.analysisTime = analysisTime;
    }

    public String getAnalysisValue() {
        return analysisValue;
    }

    public void setAnalysisValue(String analysisValue) {
        this.analysisValue = analysisValue;
    }
}
