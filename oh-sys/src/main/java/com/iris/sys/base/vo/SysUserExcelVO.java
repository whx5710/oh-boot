package com.iris.sys.base.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.iris.framework.common.excel.LocalDateTimeConverter;

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
    @ExcelIgnore
    private Long id;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("姓名")
    private String realName;

    @ExcelIgnore
    // @Trans(type = TransType.DICTIONARY, key = "user_gender", ref = "genderLabel", dataSource = Constant.SYS_DB)
    private Integer gender;

    @ExcelProperty(value = "性别")
    private String genderLabel;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("手机号")
    private String mobile;

    @ExcelProperty("机构ID")
    private Long orgId;

    @ExcelIgnore
    // @Trans(type = TransType.DICTIONARY, key = "user_status", ref = "statusLabel", dataSource = Constant.SYS_DB)
    private Integer status;

    @ExcelProperty(value = "状态")
    private String statusLabel;

    @ExcelIgnore
    // @Trans(type = TransType.DICTIONARY, key = "user_super_admin", ref = "superAdminLabel", dataSource = Constant.SYS_DB)
    private Integer superAdmin;

    @ExcelProperty(value = "超级管理员")
    private String superAdminLabel;

    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
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
