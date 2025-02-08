package com.finn.sys.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.finn.core.constant.Constant;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.TreeUtils;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.service.SysRoleMenuService;
import com.finn.sys.base.convert.SysMenuConvert;
import com.finn.sys.base.entity.SysMenuEntity;
import com.finn.sys.base.enums.SuperAdminEnum;
import com.finn.sys.base.mapper.SysMenuMapper;
import com.finn.sys.base.query.SysMenuQuery;
import com.finn.sys.base.service.SysMenuService;
import com.finn.sys.base.vo.SysMenuTreeVO;
import com.finn.sys.base.vo.SysMenuVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void save(SysMenuTreeVO vo) {
        SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);
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
            SysMenuEntity param = new SysMenuEntity();
            param.setMenuPath(entity.getMenuPath());
            param.setType(0);
            List<SysMenuEntity> list = sysMenuMapper.getList(param);
            if(list != null && !list.isEmpty()){
                throw new ServerException("显示路径已存在，请换一个!");
            }
        }
        // 保存菜单
        sysMenuMapper.save(entity);
    }

    @Override
    public void update(SysMenuTreeVO vo) {
        SysMenuEntity entity = SysMenuConvert.INSTANCE.convert(vo);

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
            SysMenuEntity param = new SysMenuEntity();
            param.setMenuPath(entity.getMenuPath());
            List<SysMenuEntity> list = sysMenuMapper.getList(param);
            if(list != null && !list.isEmpty()){
                for(SysMenuEntity item : list){
                    if(!item.getId().equals(entity.getId())){
                        throw new ServerException("显示路径已存在，请换一个!");
                    }
                }
            }
        }
        // 更新菜单
        sysMenuMapper.updateById(entity);
    }

    @Override
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
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<SysMenuEntity> list = sysMenuMapper.getMenuList(query);
        PageInfo<SysMenuEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysMenuConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public List<SysMenuTreeVO> getMenuTreeList(SysMenuQuery query) {
        List<SysMenuEntity> menuList = sysMenuMapper.getMenuList(query);
        return TreeUtils.build(SysMenuConvert.INSTANCE.convertTreeList(menuList),
                query.getParentId()==null? Constant.ROOT:query.getParentId());
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