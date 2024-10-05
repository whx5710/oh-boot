package com.iris.sys.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.PageResult;
import com.iris.sys.base.mapper.SysMenuMapper;
import com.iris.sys.base.enums.SuperAdminEnum;
import com.iris.sys.base.query.SysMenuQuery;
import com.iris.sys.base.vo.SysMenuTreeVO;
import com.iris.sys.base.convert.SysMenuConvert;
import com.iris.sys.base.service.SysRoleMenuService;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.exception.ServerException;
import com.iris.framework.common.utils.TreeUtils;
import com.iris.framework.security.user.UserDetail;
import com.iris.sys.base.entity.SysMenuEntity;
import com.iris.sys.base.service.SysMenuService;
import com.iris.sys.base.vo.SysMenuVO;
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
    private final SysMenuMapper sysMenuMapper;

    public SysMenuServiceImpl(SysRoleMenuService sysRoleMenuService, SysMenuMapper sysMenuMapper) {
        this.sysRoleMenuService = sysRoleMenuService;
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuTreeVO vo) {
        SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);

        // 保存菜单
        sysMenuMapper.save(entity);
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
        sysMenuMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除菜单
        SysMenuEntity param = new SysMenuEntity();
        param.setId(id);
        param.setDbStatus(0);
        sysMenuMapper.updateById(param);
        // 删除角色菜单关系
        sysRoleMenuService.deleteByMenuId(id);
    }

    /**
     * 菜单类别-分页
     * @param query 菜单
     * @return r
     */
    @Override
    public PageResult<SysMenuVO> page(SysMenuQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysMenuEntity> list = sysMenuMapper.getMenuList(query);
        PageInfo<SysMenuEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysMenuConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public List<SysMenuTreeVO> getMenuTreeList(SysMenuQuery query) {
        List<SysMenuEntity> menuList = sysMenuMapper.getMenuList(query);
        return TreeUtils.build(SysMenuConvert.INSTANCE.convertTreeList(menuList),
                query.getParentId()==null?Constant.ROOT:query.getParentId());
    }

    @Override
    public List<SysMenuTreeVO> getUserMenuList(UserDetail user, Integer type) {
        List<SysMenuEntity> menuList;

        // 系统管理员，拥有最高权限
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            SysMenuQuery query = new SysMenuQuery();
            query.setType(type);
            menuList = sysMenuMapper.getMenuList(query);
        } else {
            menuList = sysMenuMapper.getUserMenuList(user.getId(), type);
        }
        return TreeUtils.build(SysMenuConvert.INSTANCE.convertTreeList(menuList));
    }

    @Override
    public Long getSubMenuCount(Long pid) {
        SysMenuEntity param = new SysMenuEntity();
        param.setParentId(pid);
        List<SysMenuEntity> list = sysMenuMapper.getList(param);
        return (long) list.size();
    }

    @Override
    public Set<String> getUserAuthority(UserDetail user) {
        // 系统管理员，拥有最高权限
        List<String> authorityList;
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            authorityList = sysMenuMapper.getAuthorityList();
        } else {
            authorityList = sysMenuMapper.getUserAuthorityList(user.getId());
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
        return sysMenuMapper.getById(id);
    }

}