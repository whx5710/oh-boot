package com.iris.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 项目名变更
 *
 * @author 王小费 whx5710@qq.com
 * 
 */

@TableName("gen_project_modify")
public class ProjectModifyEntity {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 项目标识
     */
    private String projectCode;

    /**
     * 项目包名
     */
    private String projectPackage;

    /**
     * 项目路径
     */
    private String projectPath;

    /**
     * 变更项目名
     */
    private String modifyProjectName;

    /**
     * 变更标识
     */
    private String modifyProjectCode;

    /**
     * 变更包名
     */
    private String modifyProjectPackage;

    /**
     * 排除文件
     */
    private String exclusions;

    /**
     * 变更文件
     */
    private String modifySuffix;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectPackage() {
        return projectPackage;
    }

    public void setProjectPackage(String projectPackage) {
        this.projectPackage = projectPackage;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getModifyProjectName() {
        return modifyProjectName;
    }

    public void setModifyProjectName(String modifyProjectName) {
        this.modifyProjectName = modifyProjectName;
    }

    public String getModifyProjectCode() {
        return modifyProjectCode;
    }

    public void setModifyProjectCode(String modifyProjectCode) {
        this.modifyProjectCode = modifyProjectCode;
    }

    public String getModifyProjectPackage() {
        return modifyProjectPackage;
    }

    public void setModifyProjectPackage(String modifyProjectPackage) {
        this.modifyProjectPackage = modifyProjectPackage;
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    public String getModifySuffix() {
        return modifySuffix;
    }

    public void setModifySuffix(String modifySuffix) {
        this.modifySuffix = modifySuffix;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}