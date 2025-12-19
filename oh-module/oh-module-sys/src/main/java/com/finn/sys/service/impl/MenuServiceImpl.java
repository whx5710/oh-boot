package com.finn.sys.service.impl;

import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.TreeNode;
import com.finn.framework.datasource.utils.Wrapper;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.datasource.utils.QueryWrapper;
import com.finn.sys.vo.RouteMetaVO;
import com.finn.sys.vo.RouteVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.constant.Constant;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.TreeUtils;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.service.RoleMenuService;
import com.finn.sys.convert.MenuConvert;
import com.finn.sys.entity.MenuEntity;
import com.finn.sys.enums.SuperAdminEnum;
import com.finn.sys.mapper.MenuMapper;
import com.finn.sys.query.MenuQuery;
import com.finn.sys.service.MenuService;
import com.finn.sys.vo.MenuTreeVO;
import com.finn.sys.vo.MenuVO;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class MenuServiceImpl implements MenuService {
    private final RoleMenuService roleMenuService;
    private final MenuMapper menuMapper;

    public MenuServiceImpl(RoleMenuService roleMenuService, MenuMapper menuMapper) {
        this.roleMenuService = roleMenuService;
        this.menuMapper = menuMapper;
    }

    @Override
    public void save(MenuVO vo) {
        MenuEntity entity = MenuConvert.INSTANCE.convert(vo);

        if(vo.getAuthList() != null && !vo.getAuthList().isEmpty()){
            // 权限
            entity.setAuthority(String.join(",", vo.getAuthList()));
        }else{
            // 菜单，判断同级是否已经有权限菜单，如果有，则不能新增菜单，只能新增权限
            List<MenuEntity> list = menuMapper.selectListByWrapper(QueryWrapper.of(MenuEntity.class)
                    .eq(MenuEntity::getDbStatus, 1)
                    .eq(MenuEntity::getParentId, vo.getParentId())
                    .eq(MenuEntity::getType, "button"));
            if(!list.isEmpty()){
                throw new ServerException("该菜单下已有权限菜单，请删除权限菜单后再新增菜单");
            }
        }

        // 判断显示路径是否存在
        if(entity.getPath() != null && !entity.getPath().isEmpty()){
            if(pathExists(entity.getId(), entity.getPath())){
                throw new ServerException("显示路径已存在，请换一个");
            }
        }
        // 保存菜单
        menuMapper.insert(entity);
    }

    @Override
    public void update(MenuVO vo) {
        MenuEntity entity = MenuConvert.INSTANCE.convert(vo);
        // 上级菜单不能为自己
        if (entity.getId().equals(entity.getParentId())) {
            throw new ServerException("上级菜单不能为自己");
        }
        // 判断显示路径是否存在
        if(entity.getPath() != null && !entity.getPath().isEmpty()){
            if(pathExists(entity.getId(), entity.getPath())){
                throw new ServerException("显示路径已存在，请换一个!");
            }
        }
        // 如果是按钮权限
        if(vo.getAuthList() != null && !vo.getAuthList().isEmpty()){
            // 权限
            entity.setAuthority(String.join(",", vo.getAuthList()));
        }
        // 更新菜单
        menuMapper.updateById(entity);
    }

    @Override
    public void delete(Long id) {
        // 删除菜单
        MenuEntity param = new MenuEntity();
        param.setId(id);
        param.setDbStatus(0);
        menuMapper.updateById(param);
        // 删除角色菜单关系
        roleMenuService.deleteByMenuId(id);
    }

    /**
     * 菜单类别-分页
     * @param query 菜单
     * @return r
     */
    @Override
    public PageResult<MenuVO> page(MenuQuery query) {
        Page<MenuEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<MenuEntity> list = menuMapper.getMenuList(query);
        return new PageResult<>(MenuConvert.INSTANCE.convertList(list), page.getTotal());
    }

    @Override
    public List<MenuVO> list(MenuQuery query) {
        return MenuConvert.INSTANCE.convertList(menuMapper.getMenuList(query));
    }

    @Override
    public List<MenuTreeVO> getMenuTreeList(MenuQuery query) {
        List<MenuEntity> menuList = menuMapper.getMenuList(query);
        return TreeUtils.build(MenuConvert.INSTANCE.convertTreeList(menuList),
                query.getParentId()==null? Constant.ROOT:query.getParentId());
    }

    @Override
    public List<MenuTreeVO> getUserMenuList(MenuQuery query) {
        query.setType("menu");
        List<MenuEntity> menuList = menuList(query);
        return TreeUtils.build(MenuConvert.INSTANCE.convertTreeList(menuList));
    }

    @Override
    public List<RouteVO> getUserRouteList(MenuQuery query) {
        if(query == null){
            query = new MenuQuery();
        }
        String type = query.getType();
        List<MenuEntity> menuList = menuList(query);
        if(menuList != null && !menuList.isEmpty()){
            // 搜索-查询级联数据
            if(query.getKeyWord() != null && !query.getKeyWord().isEmpty()){
                MenuQuery tmp = new MenuQuery();
                tmp.setType("all");
                List<MenuEntity> menuListAll = menuList(tmp);
                // list转map
                Map<Long, TreeNode> nodeMap = new LinkedHashMap<>(menuListAll.size());
                for(MenuEntity menu: menuListAll){
                    TreeNode treeNode = new TreeNode<>();
                    treeNode.setId(menu.getId());
                    treeNode.setParentId(menu.getParentId());
                    nodeMap.put(menu.getId(), treeNode);
                }
                List<Long> menuIds = new ArrayList<>();
                for(MenuEntity menu: menuList){
                    menuIds.add(menu.getId());
                    TreeUtils.getParentIds(menu.getId(), nodeMap, menuIds);
                }
                // 根据id重新查询
                query.setMenuIds(menuIds);
                query.setKeyWord(null);
                query.setType(type);
                menuList = menuList(query);
            }
            List<RouteVO> routeVOS = new ArrayList<>(menuList.size());
            // 创建route vo,组装meta
            for(MenuEntity item: menuList){
                RouteVO routeVO = getRouteVO(item);
                routeVOS.add(routeVO);
            }
            return TreeUtils.build(routeVOS);
        }else{
            return null;
        }
    }

    /**
     * 组装RouteVO
     * @param item
     * @return
     */
    private static RouteVO getRouteVO(MenuEntity item) {
        RouteVO routeVO = new RouteVO();
        routeVO.setId(item.getId());
        routeVO.setParentId(item.getParentId());
        routeVO.setParentName(item.getParentName());
        routeVO.setPath(item.getPath());
        routeVO.setName(item.getName());
        routeVO.setComponent(item.getComponent());


        // 组装meta
        RouteMetaVO metaVO = getMetaVO(item);
        routeVO.setMeta(metaVO);
        return routeVO;
    }

    /**
     * 组装meta
     * @param item
     * @return
     */
    private static RouteMetaVO getMetaVO(MenuEntity item) {
        RouteMetaVO meta = new RouteMetaVO();
        meta.setTitle(item.getTitle());
        meta.setIcon(item.getIcon());
        meta.setShowBadge(item.getShowBadge());
        meta.setShowTextBadge(item.getShowTextBadge());
        meta.setIsHide(item.getIsHide());
        meta.setIsHideTab(item.getIsHideTab());
        meta.setLink(item.getLink());
        meta.setIsIframe(item.getIsIframe());
        meta.setIsFullPage(item.getIsFullPage());
        meta.setKeepAlive(item.getKeepAlive());
        meta.setFixedTab(item.getFixedTab());
        meta.setSort(item.getSort());
        meta.setMark(item.getMark());
        meta.setCreateTime(item.getCreateTime());
        if(item.getAuthority() != null && !item.getAuthority().isEmpty()){
            meta.setAuthList(Arrays.asList(item.getAuthority().split(",")));
        }
        return meta;
    }

    @Override
    public Long getSubMenuCount(Long pid) {
        MenuEntity param = new MenuEntity();
        param.setParentId(pid);
        List<MenuEntity> list = menuMapper.getList(param);
        return (long) list.size();
    }

    @Override
    public MenuEntity getById(Long id) {
        return menuMapper.getById(id);
    }

    @Override
    public Boolean nameExists(Long id, String name) {
        if(name == null || name.isEmpty()){
            return false;
        }else {
            Wrapper<MenuEntity> params = QueryWrapper.of(MenuEntity.class).eq(MenuEntity::getDbStatus, 1).eq(MenuEntity::getName, name);
            if(id != null && id != 0L){
                params.ne(MenuEntity::getId, id);
            }
            return !menuMapper.selectListByWrapper(params).isEmpty();
        }
    }

    @Override
    public Boolean pathExists(Long id, String path) {
        if(path == null || path.isEmpty()){
            return false;
        }else {
            Wrapper<MenuEntity> params = QueryWrapper.of(MenuEntity.class).eq(MenuEntity::getDbStatus, 1).eq(MenuEntity::getPath, path);
            if(id != null && id != 0L){
                params.ne(MenuEntity::getId, id);
            }
            return !menuMapper.selectListByWrapper(params).isEmpty();
        }
    }

    /**
     * 获取菜单列表
     * 1、系统管理员可查询全部的
     * 2、type = all 的查全部的
     * 3、其他根据用户角色查询
     * @param query type 菜单类型 null  全部 catalog | menu | action | all
     * @return
     */
    private List<MenuEntity> menuList(MenuQuery query){
        if(query.getType() == null || query.getType().isEmpty()){
            query.setType("menu");
        }
        List<MenuEntity> menuList;
        UserDetail user = SecurityUser.getUser();
        AssertUtils.isNull(user, "用户信息为空，请登录");
        if(query.getType().equalsIgnoreCase("all")){
            query.setType(null);
        }
        // 系统管理员，拥有最高权限
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            menuList = menuMapper.getMenuList(query);
        } else {
            menuList = menuMapper.getUserMenuList(user.getId(), query);
        }
        return menuList;
    }

}