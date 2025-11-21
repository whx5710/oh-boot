package com.finn.sys.query;

import com.finn.framework.query.Query;

/**
* 短信平台查询
*
* @author 王小费 whx5710@qq.com
*/
public class SmsPlatformQuery extends Query {
    /**
     * 平台类型  0：阿里云   1：腾讯云
     */
    private Integer platform;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 状态  0：禁用   1：启用
     */
    private Integer status;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}