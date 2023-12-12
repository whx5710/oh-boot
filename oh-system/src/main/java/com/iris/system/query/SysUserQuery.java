package com.iris.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import com.iris.framework.common.query.Query;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysUserQuery that)) return false;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getMobile(), that.getMobile()) && Objects.equals(getGender(), that.getGender()) && Objects.equals(getRealName(), that.getRealName()) && Objects.equals(getOrgId(), that.getOrgId()) && Objects.equals(getKeyWord(), that.getKeyWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getMobile(), getGender(), getRealName(), getOrgId(), getKeyWord());
    }
}
