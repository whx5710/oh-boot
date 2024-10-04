package com.iris.sys.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iris.framework.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Schema(description = "用户")
public class SysUserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "姓名不能为空")
    private String realName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别 0：男   1：女   2：未知", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 2, message = "性别不正确")
    private Integer gender;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @Schema(description = "机构ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "机构ID不能为空")
    private Long orgId;

    @Schema(description = "状态 0：停用    1：正常", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 0, max = 1, message = "用户状态不正确")
    private Integer status;

    @Schema(description = "角色ID列表")
    private List<Long> roleIdList;

    @Schema(description = "岗位ID列表")
    private List<Long> postIdList;

    @Schema(description = "超级管理员   0：否   1：是")
    private Integer superAdmin;

    @Schema(description = "用户密钥，用于第三方登录")
    private String userKey;

    @Schema(description = "机构名称")
    private String orgName;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改吗密码时间
     */
    @Schema(description = "修改吗密码时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime pwdModifyTime;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<Long> getPostIdList() {
        return postIdList;
    }

    public void setPostIdList(List<Long> postIdList) {
        this.postIdList = postIdList;
    }

    public Integer getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Integer superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
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
