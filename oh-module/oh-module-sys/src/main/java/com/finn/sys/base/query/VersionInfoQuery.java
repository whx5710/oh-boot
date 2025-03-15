package com.finn.sys.base.query;

import com.finn.framework.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* 版本信息查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@Schema(description = "版本信息查询")
public class VersionInfoQuery extends Query {

    // 是否当前版本
    private Boolean isCurrVersion;

    String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Boolean getCurrVersion() {
        return isCurrVersion;
    }

    public void setCurrVersion(Boolean currVersion) {
        isCurrVersion = currVersion;
    }
}