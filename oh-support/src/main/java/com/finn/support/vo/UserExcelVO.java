package com.finn.support.vo;

import cn.idev.excel.annotation.ExcelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * excel用户表
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class UserExcelVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 本属性对于导出无用，只是用于翻译
     */
    private Long id;

    @ExcelProperty(value = "用户名", order = 1)
    private String username;

    @ExcelProperty(value = "姓名", order = 2)
    private String realName;

    private Integer gender;

    @ExcelProperty(value = "性别", order = 3)
    private String genderLabel;

    @ExcelProperty(value = "邮箱", order = 4)
    private String email;

    @ExcelProperty(value = "手机号", order = 5)
    private String mobile;

    @ExcelProperty(value = "部门ID", order = 6)
    private Long deptId;

    private Integer status;

    @ExcelProperty(value = "状态", order = 7)
    private String statusLabel;

    private Integer superAdmin;

    @ExcelProperty(value = "超级管理员", order = 8)
    private String superAdminLabel;

    @ExcelProperty(value = "创建时间", order = 9)
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
