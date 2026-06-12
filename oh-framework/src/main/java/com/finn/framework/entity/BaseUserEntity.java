package com.finn.framework.entity;

import java.io.Serial;

/**
 * 用户基类
 *
 * @author 王小费 whx5710@qq.com
 */
public class BaseUserEntity extends IDEntity {

    @Serial
    private static final long serialVersionUID = 1L;
    private String username;
    private String realName;
    private Integer gender;
    private String email;
    private String mobile;
    // 用户类型，根据业务规则可扩展其他类型
    private String userType;
    private String openId;
    // 租户ID
    private String tenantId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
