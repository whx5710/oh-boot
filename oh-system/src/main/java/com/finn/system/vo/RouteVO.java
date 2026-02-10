package com.finn.system.vo;

import com.finn.core.utils.TreeNode;

/**
 * 菜单数据结构
 * @author 王小费
 * @since 2025-04-20
 */
public class RouteVO extends TreeNode<RouteVO> {
    /**
     * 菜单路由示例
     * const dashboardMenus = [
     *   {
     *     // 这里固定写死 BasicLayout，不可更改
     *     component: 'BasicLayout',
     *     meta: {
     *       order: -1,
     *       title: 'page.dashboard.title',
     *     },
     *     name: 'Dashboard',
     *     path: '/',
     *     redirect: '/analytics',
     *     children: [
     *       {
     *         name: 'Analytics',
     *         path: '/analytics',
     *         // 这里为页面的路径，需要去掉 views/ 和 .vue
     *         component: '/dashboard/analytics/index',
     *         meta: {
     *           affixTab: true,
     *           title: 'page.dashboard.analytics',
     *         },
     *       },
     *       {
     *         name: 'Workspace',
     *         path: '/workspace',
     *         component: '/dashboard/workspace/index',
     *         meta: {
     *           title: 'page.dashboard.workspace',
     *         },
     *       },
     *     ],
     *   }
     * ]
     */
    String name;
    String path;
    // 这里为页面的路径，需要去掉 views/ 和 .vue
    String component;

    String redirect;
    // 类型:  catalog | menu | action
    String type;
    /**
     * 状态 0停用 1有效
     */
    Integer status;
    String authCode;
    String parentName;
    RouteMetaVO meta;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public RouteMetaVO getMeta() {
        return meta;
    }

    public void setMeta(RouteMetaVO meta) {
        this.meta = meta;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
