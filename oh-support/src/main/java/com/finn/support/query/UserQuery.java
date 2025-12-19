package com.finn.support.query;

import com.finn.framework.query.Query;

import java.util.List;

/**
 * 用户查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class UserQuery extends Query {
    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 状态 0：停用    1：正常
     */
    private Integer status;
    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 排除租户ID
     */
    private String unTenantId;

    /**
     * 用户名集合
     */
    private List<String> userNames;

    /**
     * 租户标识，0所有1租户
     */
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
