package com.iris.system.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iris.system.base.dao.SysMenuDao;
import com.iris.system.base.enums.SuperAdminEnum;
import com.iris.system.base.vo.SysMenuMetaVO;
import com.iris.system.base.vo.SysMenuNativeVO;
import com.iris.system.base.vo.SysMenuVO;
import com.iris.system.base.convert.SysMenuConvert;
import com.iris.system.base.service.SysRoleMenuService;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.datasource.service.impl.BaseServiceImpl;
import com.iris.framework.common.utils.TreeUtils;
import com.iris.framework.security.user.UserDetail;
import com.iris.system.base.entity.SysMenuEntity;
import com.iris.system.base.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 菜单管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    private final SysRoleMenuService sysRoleMenuService;

    public SysMenuServiceImpl(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuVO vo) {
        SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);

        // 保存菜单
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuVO vo) {
        SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);

        // 上级菜单不能为自己
        if (entity.getId().equals(entity.getPid())) {
            throw new ServerException("上级菜单不能为自己");
        }

        // 更新菜单
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除菜单
        removeById(id);

        // 删除角色菜单关系
        sysRoleMenuService.deleteByMenuId(id);
    }

    @Override
    public List<SysMenuVO> getMenuList(Integer type) {
        List<SysMenuEntity> menuList = baseMapper.getMenuList(type);

        return TreeUtils.build(SysMenuConvert.INSTANCE.convertList(menuList), Constant.ROOT);
    }

    @Override
    public List<SysMenuVO> getUserMenuList(UserDetail user, Integer type) {
        List<SysMenuEntity> menuList;

        // 系统管理员，拥有最高权限
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            menuList = baseMapper.getMenuList(type);
        } else {
            menuList = baseMapper.getUserMenuList(user.getId(), type);
        }

        return TreeUtils.build(SysMenuConvert.INSTANCE.convertList(menuList));
    }

    @Override
    public List<SysMenuNativeVO> getUserNativeMenuList(UserDetail user, Integer type) {
        List<SysMenuEntity> menuList;

        List<SysMenuNativeVO> menuNativeList = new ArrayList<>();

        // 系统管理员，拥有最高权限
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            menuList = baseMapper.getMenuList(type);
        } else {
            menuList = baseMapper.getUserMenuList(user.getId(), type);
        }
        for(SysMenuEntity sysMenuEntity: menuList){
            SysMenuNativeVO sysMenuNativeVO = new SysMenuNativeVO();

            sysMenuNativeVO.setId(sysMenuEntity.getId());
            String url = sysMenuEntity.getUrl();
            if(url != null && url.length() > 1 && !url.startsWith("/")){
                url = "/" + url;
            }
            sysMenuNativeVO.setPath(url);
            sysMenuNativeVO.setName(sysMenuEntity.getName());
            sysMenuNativeVO.setType(sysMenuEntity.getType());
            sysMenuNativeVO.setOpenStyle(sysMenuEntity.getOpenStyle());
            sysMenuNativeVO.setPid(sysMenuEntity.getPid());

            SysMenuMetaVO sysMenuMetaVO = new SysMenuMetaVO();
            sysMenuMetaVO.setRank(sysMenuEntity.getSort());
            sysMenuMetaVO.setIcon(sysMenuEntity.getIcon());
            sysMenuMetaVO.setTitle(sysMenuEntity.getName());
            if(!ObjectUtils.isEmpty(sysMenuEntity.getAuthority())){
                sysMenuMetaVO.setAuths(sysMenuEntity.getAuthority().split(","));
            }
            sysMenuNativeVO.setMeta(sysMenuMetaVO);
            menuNativeList.add(sysMenuNativeVO);
        }
        return TreeUtils.build(menuNativeList);
    }

    @Override
    public Long getSubMenuCount(Long pid) {
        return count(new LambdaQueryWrapper<SysMenuEntity>().eq(SysMenuEntity::getPid, pid));
    }

    @Override
    public Set<String> getUserAuthority(UserDetail user) {
        // 系统管理员，拥有最高权限
        List<String> authorityList;
        if (user.getSuperAdmin().equals(SuperAdminEnum.YES.getValue())) {
            authorityList = baseMapper.getAuthorityList();
        } else {
            authorityList = baseMapper.getUserAuthorityList(user.getId());
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

}