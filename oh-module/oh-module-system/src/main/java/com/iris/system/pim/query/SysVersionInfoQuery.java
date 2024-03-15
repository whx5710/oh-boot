package com.iris.system.pim.query;

import com.iris.framework.common.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* 版本信息查询
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-09-16
*/
@Schema(description = "版本信息查询")
public class SysVersionInfoQuery extends Query {

    String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}