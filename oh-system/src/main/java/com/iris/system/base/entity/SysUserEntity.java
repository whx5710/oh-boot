package com.iris.system.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iris.framework.common.entity.BaseEntity;
import com.iris.system.base.enums.UserStatusEnum;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别   0：男   1：女   2：未知
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 超级管理员   0：否   1：是
     */
    private Integer superAdmin;
    /**
     * 状态  {@link UserStatusEnum}
     */
    private Integer status;

    /**
     * 修改吗密码时间
     */
    private LocalDateTime pwdModifyTime;

    /**
     * 用户密钥，用于第三方系统登录
     */
    private String userKey;
    /**
     * 机构名称
     */
    @TableField(exist=false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysUserEntity that)) return false;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getRealName(), that.getRealName()) && Objects.equals(getAvatar(), that.getAvatar()) && Objects.equals(getGender(), that.getGender()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getMobile(), that.getMobile()) && Objects.equals(getOrgId(), that.getOrgId()) && Objects.equals(getSuperAdmin(), that.getSuperAdmin()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getOrgName(), that.getOrgName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getRealName(), getAvatar(), getGender(), getEmail(), getMobile(), getOrgId(), getSuperAdmin(), getStatus(), getOrgName());
    }
}
