package com.iris.support.entity;

import com.iris.framework.datasource.annotations.TableField;
import com.iris.framework.datasource.annotations.TableName;
import com.iris.framework.entity.BaseEntity;
import com.iris.support.enums.UserStatusEnum;

import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 性别   0：男   1：女   2：未知
     */
    @TableField("gender")
    private Integer gender;
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;
    /**
     * 机构ID
     */
    @TableField("org_id")
    private Long orgId;
    /**
     * 超级管理员   0：否   1：是
     */
    @TableField("super_admin")
    private Integer superAdmin;
    /**
     * 状态  {@link UserStatusEnum}
     */
    @TableField("status")
    private Integer status;

    /**
     * 修改吗密码时间
     */
    @TableField("pwd_modify_time")
    private LocalDateTime pwdModifyTime;

    /**
     * 用户密钥，用于第三方系统登录
     */
    @TableField("user_key")
    private String userKey;
    /**
     * 机构名称
     */
    @TableField(exists = false)
    private String orgName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Integer superAdmin) {
        this.superAdmin = superAdmin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public LocalDateTime getPwdModifyTime() {
        return pwdModifyTime;
    }

    public void setPwdModifyTime(LocalDateTime pwdModifyTime) {
        this.pwdModifyTime = pwdModifyTime;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
