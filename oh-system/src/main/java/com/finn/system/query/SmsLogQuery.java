package com.finn.system.query;

import com.finn.framework.query.Query;

/**
* 短信日志查询
*
* @author 王小费 whx5710@qq.com
*/
public class SmsLogQuery extends Query {
    /**
     * 平台ID
     */
    private Long platformId;

    /**
     * 平台类型
     */
    private Integer platform;

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
}