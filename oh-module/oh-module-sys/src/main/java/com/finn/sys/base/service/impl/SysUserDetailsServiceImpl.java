package com.finn.sys.base.service.impl;

import com.finn.framework.security.user.UserDetail;
import com.finn.support.convert.UserConvert;
import com.finn.support.entity.UserEntity;
import com.finn.support.mapper.RoleDataScopeMapper;
import com.finn.support.mapper.RoleMapper;
import com.finn.support.service.OrgService;
import com.finn.sys.base.enums.DataScopeEnum;
import com.finn.sys.base.enums.UserStatusEnum;
import com.finn.sys.base.service.MenuService;
import com.finn.sys.base.service.SysUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户 UserDetails 信息
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysUserDetailsServiceImpl implements SysUserDetailsService {
    private final MenuService menuService;
    private final OrgService orgService;
    private final RoleMapper roleMapper;
    private final RoleDataScopeMapper roleDataScopeMapper;

    public SysUserDetailsServiceImpl(MenuService menuService, OrgService orgService, RoleMapper roleMapper, RoleDataScopeMapper roleDataScopeMapper) {
        this.menuService = menuService;
        this.orgService = orgService;
        this.roleMapper = roleMapper;
        this.roleDataScopeMapper = roleDataScopeMapper;
    }

    @Override
    public UserDetails getUserDetails(UserEntity userEntity) {
        // 转换成UserDetail对象
        UserDetail userDetail = UserConvert.INSTANCE.convertDetail(userEntity);

        // 账号不可用
        if (userEntity.getStatus() == UserStatusEnum.DISABLE.getValue()) {
            userDetail.setEnabled(false);
        }

        // 数据权限范围
        List<Long> dataScopeList = getDataScope(userDetail);
        userDetail.setDataScopeList(dataScopeList);

        // 用户权限列表
        Set<String> authoritySet = menuService.getUserAuthority(userDetail);
        userDetail.setAuthoritySet(authoritySet);

        return userDetail;
    }

    /**
     * 获取数据范围
     * @param userDetail
     * @return
     */
    private List<Long> getDataScope(UserDetail userDetail) {
        Integer dataScope = roleMapper.getDataScopeByUserId(userDetail.getId());
        if (dataScope == null) {
            return new ArrayList<>();
        }

        if (dataScope.equals(DataScopeEnum.ALL.getValue())) {
            // 全部数据权限，则返回null
            return null;
        } else if (dataScope.equals(DataScopeEnum.ORG_AND_CHILD.getValue())) {
            // 本机构及子机构数据
            List<Long> dataScopeList = orgService.getSubOrgIdList(userDetail.getOrgId());
            // 自定义数据权限范围
            dataScopeList.addAll(roleDataScopeMapper.getDataScopeList(userDetail.getId()));

            return dataScopeList;
        } else if (dataScope.equals(DataScopeEnum.ORG_ONLY.getValue())) {
            // 本机构数据
            List<Long> dataScopeList = new ArrayList<>();
            dataScopeList.add(userDetail.getOrgId());
            // 自定义数据权限范围
            dataScopeList.addAll(roleDataScopeMapper.getDataScopeList(userDetail.getId()));

            return dataScopeList;
        } else if (dataScope.equals(DataScopeEnum.CUSTOM.getValue())) {
            // 自定义数据权限范围
            return roleDataScopeMapper.getDataScopeList(userDetail.getId());
        }

        return new ArrayList<>();
    }
}
