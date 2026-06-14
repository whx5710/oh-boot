package com.finn.system.query;

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
    private Long deptId;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 用户名集合
     */
    private List<String> userNames;

    private Long roleId;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
