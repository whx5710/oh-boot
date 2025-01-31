package com.iris.support.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.query.Query;

import java.util.List;

/**
 * 用户查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "用户查询")
public class SysUserQuery extends Query {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "机构ID")
    private String orgId;

    @Schema(description = "关键字")
    private String keyWord;

    @Schema(description = "租户ID")
    private String tenantId;

    @Schema(description = "排除租户ID")
    private String unTenantId;

    @Schema(description = "用户名集合")
    private List<String> userNames;

    @Schema(description = "租户标识，0所有1租户")
    private Integer tenantFlag = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUnTenantId() {
        return unTenantId;
    }

    public void setUnTenantId(String unTenantId) {
        this.unTenantId = unTenantId;
    }

    public Integer getTenantFlag() {
        return tenantFlag;
    }

    public void setTenantFlag(Integer tenantFlag) {
        this.tenantFlag = tenantFlag;
    }
}
