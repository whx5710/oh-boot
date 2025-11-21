package com.finn.sys.query;

import com.finn.framework.query.Query;

import java.util.List;

/**
 * 菜单查询
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class MenuQuery extends Query {
    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 类型 catalog | menu | action | all
     */
    private String type;

    /**
     * 搜索关键字
     */
    private String keyWords;
    /**
     * 菜单ID集合
     */
    private List<Long> menuIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }
}
