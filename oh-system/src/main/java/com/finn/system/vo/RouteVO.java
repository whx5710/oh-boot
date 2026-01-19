package com.finn.system.vo;

import com.finn.core.utils.TreeNode;

/**
 * 菜单数据结构
 * @author 王小费
 * @since 2025-04-20
 */
public class RouteVO extends TreeNode<RouteVO> {
//    export type MenuListType = {
//        id: number; // id
//        path: string; // 路由路径
//        name: string; // 组件名
//        component?: string; // 组件路径
//        meta: {
//            /** 路由标题 */
//            title: string;
//            /** 路由图标 */
//            icon?: string;
//            /** 是否显示徽章 */
//            showBadge?: boolean;
//            /** 文本徽章 */
//            showTextBadge?: string;
//            /** 是否在菜单中隐藏 */
//            isHide?: boolean;
//            /** 是否在标签页中隐藏 */
//            isHideTab?: boolean;
//            /** 外部链接 */
//            link?: string;
//            /** 是否为iframe */
//            isIframe?: boolean;
//            /** 是否缓存 */
//            keepAlive?: boolean;
//            /** 操作权限 */
//            authList?: Array<{
//                    /** 权限名称 */
//                    title: string;
//            /** 权限标识 */
//            authMark: string;
//    }>;
//            /** 是否为一级菜单（不需要手动配置，自动识别） */
//            isFirstLevel?: boolean;
//            /** 角色权限 */
//            roles?: string[];
//            /** 是否固定标签页 */
//            fixedTab?: boolean;
//        };
//        children?: MenuListType[]; // 子路由
//    };

    String name;
    String path;
    // 这里为页面的路径，需要去掉 views/ 和 .vue
    String component;
    // 类型:  catalog | menu | button
    String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public RouteMetaVO getMeta() {
        return meta;
    }

    public void setMeta(RouteMetaVO meta) {
        this.meta = meta;
    }
}
