package com.finn.sys.base.service.impl;

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

import java.util.List;

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
    public void save(MenuTreeVO vo) {
        MenuEntity entity = MenuConvert.INSTANCE.convert(vo);
        // url不以 / 开头
        if(entity.getUrl() != null && entity.getUrl().startsWith("/")){
            entity.setUrl(entity.getUrl().substring(1));
        }
        // 显示路径以 / 开头
        if(entity.getMenuPath() != null && !entity.getMenuPath().isEmpty() && !entity.getMenuPath().startsWith("/")){
            entity.setMenuPath("/" + entity.getMenuPath());
        }
        // 判断显示路径是否存在
        if(entity.getMenuPath() != null && !entity.getMenuPath().isEmpty()){
            MenuEntity param = new MenuEntity();
            param.setMenuPath(entity.getMenuPath());
            param.setType(0);
            List<MenuEntity> list = menuMapper.getList(param);
            if(list != null && !list.isEmpty()){
                throw new ServerException("显示路径已存在，请换一个!");
            }
        }
        // 保存菜单
        menuMapper.save(entity);
    }

    @Override
    public void update(MenuTreeVO vo) {
        MenuEntity entity = MenuConvert.INSTANCE.convert(vo);

        // 上级菜单不能为自己
        if (entity.getId().equals(entity.getParentId())) {
            throw new ServerException("上级菜单不能为自己");
        }
        // url不以 / 开头
        if(entity.getUrl() != null && entity.getUrl().startsWith("/")){
            entity.setUrl(entity.getUrl().substring(1));
        }
        // 显示路径以 / 开头
        if(entity.getMenuPath() != null && !entity.getMenuPath().isEmpty() && !entity.getMenuPath().startsWith("/")){
            entity.setMenuPath("/" + entity.getMenuPath());
        }
        // 判断显示路径是否存在
        if(entity.getMenuPath() != null && !entity.getMenuPath().isEmpty()){
            MenuEntity param = new MenuEntity();
            param.setMenuPath(entity.getMenuPath());
            List<MenuEntity> list = menuMapper.getList(param);
            if(list != null && !list.isEmpty()){
                for(MenuEntity item : list){
                    if(!item.getId().equals(entity.getId())){
                        throw new ServerException("显示路径已存在，请换一个!");
                    }
                }
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
    public List<MenuTreeVO> getMenuTreeList(MenuQuery query) {
        List<MenuEntity> menuList = menuMapper.getMenuList(query);
        return TreeUtils.build(MenuConvert.INSTANCE.convertTreeList(menuList),
                query.getParentId()==null? Constant.ROOT:query.getParentId());
    }

    @Override
    public List<MenuTreeVO> getUserMenuList(UserDetail user, Integer type) {
        List<MenuEntity> menuList;

        // 系统管理员，拥有最高权限
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            MenuQuery query = new MenuQuery();
            query.setType(type);
            menuList = menuMapper.getMenuList(query);
        } else {
            menuList = menuMapper.getUserMenuList(user.getId(), type);
        }
        return TreeUtils.build(MenuConvert.INSTANCE.convertTreeList(menuList));
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

}