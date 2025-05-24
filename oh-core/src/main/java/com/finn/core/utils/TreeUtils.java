package com.finn.core.utils;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类，如：菜单、部门等
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class TreeUtils {

    /**
     * 根据pid，构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Long pid) {
        // pid不能为空
        AssertUtils.isNull(pid, "parentId[上级ID]");

        List<T> treeList = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (pid.equals(treeNode.getParentId())) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }
        return treeList;
    }

    /**
     * 查找子节点
     */
    private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode) {
        for (T treeNode : treeNodes) {
            if (rootNode.getId().equals(treeNode.getParentId())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();

        // list转map
        Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        for (T treeNode : treeNodes) {
            nodeMap.put(treeNode.getId(), treeNode);
        }

        for (T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getParentId());
            if (parent != null && !(node.getId().equals(parent.getId()))) {
                if(parent.getChildren() == null){
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(node);
                continue;
            }

            result.add(node);
        }
        return result;
    }

    /**
     * 获取上级级联id列表
     * @param id 菜单ID
     * @param menuMap 所有菜单
     * @param out 输出级联 id 集合
     */
    public static  void getParentIds(Long id, Map<Long, TreeNode> menuMap, List<Long> out){
        if(id != null && menuMap.containsKey(id)){
            Long parentId = menuMap.get(id).getParentId();
            out.add(parentId);
            getParentIds(parentId, menuMap, out);
        }
    }

}