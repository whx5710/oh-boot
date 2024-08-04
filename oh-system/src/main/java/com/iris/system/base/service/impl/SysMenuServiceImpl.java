package com.iris.system.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.iris.system.base.dao.SysMenuDao;
import com.iris.system.base.enums.SuperAdminEnum;
import com.iris.system.base.vo.SysMenuTreeVO;
import com.iris.system.base.convert.SysMenuConvert;
import com.iris.system.base.service.SysRoleMenuService;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.TreeUtils;
import com.iris.framework.security.user.UserDetail;
import com.iris.system.base.entity.SysMenuEntity;
import com.iris.system.base.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    private final SysRoleMenuService sysRoleMenuService;
    private final SysMenuDao sysMenuDao;

    public SysMenuServiceImpl(SysRoleMenuService sysRoleMenuService, SysMenuDao sysMenuDao) {
        this.sysRoleMenuService = sysRoleMenuService;
        this.sysMenuDao = sysMenuDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuTreeVO vo) {
        SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);

        // 保存菜单
        sysMenuDao.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuTreeVO vo) {
        SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);

        // 上级菜单不能为自己
        if (entity.getId().equals(entity.getParentId())) {
            throw new ServerException("上级菜单不能为自己");
        }

        // 更新菜单
        sysMenuDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除菜单
        SysMenuEntity param = new SysMenuEntity();
        param.setId(id);
        param.setDbStatus(0);
        sysMenuDao.updateById(param);
        // 删除角色菜单关系
        sysRoleMenuService.deleteByMenuId(id);
    }

    @Override
    public List<SysMenuTreeVO> getMenuList(Integer type) {
        List<SysMenuEntity> menuList = sysMenuDao.getMenuList(type);
        return SysMenuConvert.INSTANCE.convertList(menuList);
    }

    @Override
    public List<SysMenuTreeVO> getMenuTreeList(Integer type) {
        return TreeUtils.build(getMenuList(type), Constant.ROOT);
    }

    @Override
    public List<SysMenuTreeVO> getUserMenuList(UserDetail user, Integer type) {
        List<SysMenuEntity> menuList;

        // 系统管理员，拥有最高权限
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            menuList = sysMenuDao.getMenuList(type);
        } else {
            menuList = sysMenuDao.getUserMenuList(user.getId(), type);
        }

        return TreeUtils.build(SysMenuConvert.INSTANCE.convertList(menuList));
    }

    @Override
    public Long getSubMenuCount(Long pid) {
        SysMenuEntity param = new SysMenuEntity();
        param.setParentId(pid);
        List<SysMenuEntity> list = sysMenuDao.getList(param);
        return (long) list.size();
    }

    @Override
    public Set<String> getUserAuthority(UserDetail user) {
        // 系统管理员，拥有最高权限
        List<String> authorityList;
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            authorityList = sysMenuDao.getAuthorityList();
        } else {
            authorityList = sysMenuDao.getUserAuthorityList(user.getId());
        }

        // 用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String authority : authorityList) {
            if (StrUtil.isBlank(authority)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(authority.trim().split(",")));
        }

        return permsSet;
    }

    @Override
    public SysMenuEntity getById(Long id) {
        return sysMenuDao.getById(id);
    }

}