package com.finn.support.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * excel用户表
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class SysUserExcelVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 本属性对于导出无用，只是用于翻译
     */
    private Long id;

    @Excel(name = "用户名", orderNum = "1")
    private String username;

    @Excel(name = "姓名", orderNum = "2")
    private String realName;

    private Integer gender;

    @Excel(name = "性别", orderNum = "3")
    private String genderLabel;

    @Excel(name = "邮箱", orderNum = "4")
    private String email;

    @Excel(name = "手机号", orderNum = "5")
    private String mobile;

    @Excel(name = "机构ID", orderNum = "6")
    private Long orgId;

    private Integer status;

    @Excel(name = "状态", orderNum = "7")
    private String statusLabel;

    private Integer superAdmin;

    @Excel(name = "超级管理员", orderNum = "8")
    private String superAdminLabel;

    @Excel(name = "创建时间", orderNum = "9", exportFormat = "yyyy年MM月dd日 HH时mm分ss秒", width = 20)
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getGenderLabel() {
        return genderLabel;
    }

    public void setGenderLabel(String genderLabel) {
        this.genderLabel = genderLabel;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Integer getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Integer superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getSuperAdminLabel() {
        return superAdminLabel;
    }

    public void setSuperAdminLabel(String superAdminLabel) {
        this.superAdminLabel = superAdminLabel;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
