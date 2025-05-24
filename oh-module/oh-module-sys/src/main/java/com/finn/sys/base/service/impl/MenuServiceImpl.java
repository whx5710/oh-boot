package com.finn.sys.base.service.impl;

import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.TreeNode;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.utils.ParamsBuilder;
import com.finn.sys.base.vo.RouteMetaVO;
import com.finn.sys.base.vo.RouteVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.constant.Constant;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.TreeUtils;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.service.RoleMenuService;
import com.finn.sys.base.convert.MenuConvert;
import com.finn.sys.base.entity.MenuEntity;
import com.finn.sys.base.enums.SuperAdminEnum;
import com.finn.sys.base.mapper.MenuMapper;
import com.finn.sys.base.query.MenuQuery;
import com.finn.sys.base.service.MenuService;
import com.finn.sys.base.vo.MenuTreeVO;
import com.finn.sys.base.vo.MenuVO;
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
    public void save(RouteVO vo) {
        MenuEntity entity = MenuConvert.INSTANCE.convert(vo.getMeta());
        /*if(vo.getMeta().getAuthority() != null){
            entity.setAuthority(String.join(",", vo.getMeta().getAuthority()));
        }*/
        entity.setAuthority(vo.getAuthCode());
        entity.setId(vo.getId());
        entity.setParentId(vo.getParentId());
        entity.setName(vo.getName());
        entity.setPath(vo.getPath());
        entity.setMenuPath(vo.getComponent());
        entity.setType(vo.getType());
        entity.setStatus(vo.getStatus());

        // 显示路径以 / 开头
        if(entity.getMenuPath() != null && !entity.getMenuPath().isEmpty()
                && !entity.getMenuPath().equals("BasicLayout")  && !entity.getMenuPath().startsWith("/")){
            entity.setMenuPath("/" + entity.getMenuPath());
        }
        // 判断显示路径是否存在
        if(entity.getMenuPath() != null && !entity.getMenuPath().isEmpty()){
            if(pathExists(entity.getId(), entity.getPath())){
                throw new ServerException("显示路径已存在，请换一个!");
            }
        }
        // 保存菜单
        menuMapper.insert(entity);
    }

    @Override
    public void update(RouteVO vo) {
        MenuEntity entity = MenuConvert.INSTANCE.convert(vo.getMeta());
        /*if(vo.getMeta().getAuthority() != null){
            entity.setAuthority(String.join(",", vo.getMeta().getAuthority()));
        }*/
        entity.setAuthority(vo.getAuthCode());
        entity.setId(vo.getId());
        entity.setParentId(vo.getParentId());
        entity.setName(vo.getName());
        entity.setPath(vo.getPath());
        entity.setMenuPath(vo.getComponent());
        entity.setType(vo.getType());
        entity.setStatus(vo.getStatus());

        // 上级菜单不能为自己
        if (entity.getId().equals(entity.getParentId())) {
            throw new ServerException("上级菜单不能为自己");
        }
        // 显示路径以 / 开头
        if(entity.getMenuPath() != null && !entity.getMenuPath().isEmpty()
                && !entity.getMenuPath().equals("BasicLayout")  && !entity.getMenuPath().startsWith("/")){
            entity.setMenuPath("/" + entity.getMenuPath());
        }
        // 判断显示路径是否存在
        if(entity.getMenuPath() != null && !entity.getMenuPath().isEmpty()){
            if(pathExists(entity.getId(), entity.getPath())){
                throw new ServerException("显示路径已存在，请换一个!");
            }
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
            if(query.getKeyWords() != null && !query.getKeyWords().isEmpty()){
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
                query.setKeyWords(null);
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
        routeVO.setName(item.getName());
        routeVO.setType(item.getType());
        routeVO.setStatus(item.getStatus());
        routeVO.setAuthCode(item.getAuthority());
        routeVO.setParentName(item.getParentName());
        if(item.getMenuPath() != null && !item.getMenuPath().isEmpty()){
            if(!item.getMenuPath().startsWith("/")){
                if(item.getMenuPath().equals("BasicLayout")){
                    routeVO.setComponent("BasicLayout");
                }else {
                    routeVO.setComponent("/" + item.getMenuPath());
                }
            }else{
                routeVO.setComponent(item.getMenuPath());
            }
        }else{
            routeVO.setComponent(item.getMenuPath());
        }
        routeVO.setPath(item.getPath());

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
        RouteMetaVO metaVO = new RouteMetaVO();
        metaVO.setTitle(item.getTitle());
        metaVO.setIcon(item.getIcon());
        // 权限列表
        if(item.getAuthority() != null && !item.getAuthority().isEmpty()){
            metaVO.setAuthority(Arrays.asList(item.getAuthority().split(",")));
        }
        // 用于配置外链跳转路径，会在新窗口打开
        if(item.getOpenStyle() == 1){
            metaVO.setLink(item.getLink());
            metaVO.setOpenInNewWindow(true);
        }else{
            // 用于配置内嵌页面的 iframe 地址，设置后会在当前页面内嵌对应的页面
            if(item.getLink() != null && (item.getLink().startsWith("http://") || item.getLink().startsWith("https://"))){
                metaVO.setIframeSrc(item.getLink());
            }
        }
        // 用于配置页面是否开启缓存，开启后页面会缓存，不会重新加载，仅在标签页启用时有效keepAlive
        metaVO.setKeepAlive(item.getKeepAlive());
        metaVO.setAffixTab(item.getAffixTab());
        metaVO.setHideInMenu(item.getHideInMenu());
        metaVO.setHideInTab(item.getHideInTab());
        metaVO.setBadge(item.getBadge());
        metaVO.setBadgeType(item.getBadgeType());
        metaVO.setBadgeVariants(item.getBadgeVariants());
        metaVO.setOrder(item.getSort());
        return metaVO;
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
            ParamsBuilder<MenuEntity> params = ParamsBuilder.of(MenuEntity.class).eq(MenuEntity::getDbStatus, 1).eq(MenuEntity::getName, name);
            if(id != null && id != 0L){
                params.ne(MenuEntity::getId, id);
            }
            return !menuMapper.selectListByParam(params).isEmpty();
        }
    }

    @Override
    public Boolean pathExists(Long id, String path) {
        if(path == null || path.isEmpty()){
            return false;
        }else {
            ParamsBuilder<MenuEntity> params = ParamsBuilder.of(MenuEntity.class).eq(MenuEntity::getDbStatus, 1).eq(MenuEntity::getPath, path);
            if(id != null && id != 0L){
                params.ne(MenuEntity::getId, id);
            }
            return !menuMapper.selectListByParam(params).isEmpty();
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