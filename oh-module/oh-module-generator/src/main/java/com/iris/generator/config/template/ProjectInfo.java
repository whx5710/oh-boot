package com.iris.generator.config.template;


/**
 * 项目信息
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class ProjectInfo {
    /**
     * 项目包名
     */
    private String packageName;
    /**
     * 项目版本号
     */
    private String version;
    /**
     * 后端生成路径
     */
    private String backendPath;
    /**
     * 前端生成路径
     */
    private String frontendPath;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBackendPath() {
        return backendPath;
    }

    public void setBackendPath(String backendPath) {
        this.backendPath = backendPath;
    }

    public String getFrontendPath() {
        return frontendPath;
    }

    public void setFrontendPath(String frontendPath) {
        this.frontendPath = frontendPath;
    }
}