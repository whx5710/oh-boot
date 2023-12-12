package com.iris.system.service.impl;

import com.iris.system.convert.SysUserConvert;
import com.iris.system.service.SysUserDetailsService;
import com.iris.framework.security.user.UserDetail;
import com.iris.system.dao.SysRoleDao;
import com.iris.system.dao.SysRoleDataScopeDao;
import com.iris.system.entity.SysUserEntity;
import com.iris.system.enums.DataScopeEnum;
import com.iris.system.enums.UserStatusEnum;
import com.iris.system.service.SysMenuService;
import com.iris.system.service.SysOrgService;
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
    private final SysMenuService sysMenuService;
    private final SysOrgService sysOrgService;
    private final SysRoleDao sysRoleDao;
    private final SysRoleDataScopeDao sysRoleDataScopeDao;

    public SysUserDetailsServiceImpl(SysMenuService sysMenuService, SysOrgService sysOrgService, SysRoleDao sysRoleDao, SysRoleDataScopeDao sysRoleDataScopeDao) {
        this.sysMenuService = sysMenuService;
        this.sysOrgService = sysOrgService;
        this.sysRoleDao = sysRoleDao;
        this.sysRoleDataScopeDao = sysRoleDataScopeDao;
    }

    @Override
    public UserDetails getUserDetails(SysUserEntity userEntity) {
        // 转换成UserDetail对象
        UserDetail userDetail = SysUserConvert.INSTANCE.convertDetail(userEntity);

        // 账号不可用
        if (userEntity.getStatus() == UserStatusEnum.DISABLE.getValue()) {
            userDetail.setEnabled(false);
        }

        // 数据权限范围
        List<Long> dataScopeList = getDataScope(userDetail);
        userDetail.setDataScopeList(dataScopeList);

        // 用户权限列表
        Set<String> authoritySet = sysMenuService.getUserAuthority(userDetail);
        userDetail.setAuthoritySet(authoritySet);

        return userDetail;
    }

    private List<Long> getDataScope(UserDetail userDetail) {
        Integer dataScope = sysRoleDao.getDataScopeByUserId(userDetail.getId());
        if (dataScope == null) {
            return new ArrayList<>();
        }

        if (dataScope.equals(DataScopeEnum.ALL.getValue())) {
            // 全部数据权限，则返回null
            return null;
        } else if (dataScope.equals(DataScopeEnum.ORG_AND_CHILD.getValue())) {
            // 本机构及子机构数据
            List<Long> dataScopeList = sysOrgService.getSubOrgIdList(userDetail.getOrgId());
            // 自定义数据权限范围
            dataScopeList.addAll(sysRoleDataScopeDao.getDataScopeList(userDetail.getId()));

            return dataScopeList;
        } else if (dataScope.equals(DataScopeEnum.ORG_ONLY.getValue())) {
            // 本机构数据
            List<Long> dataScopeList = new ArrayList<>();
            dataScopeList.add(userDetail.getOrgId());
            // 自定义数据权限范围
            dataScopeList.addAll(sysRoleDataScopeDao.getDataScopeList(userDetail.getId()));

            return dataScopeList;
        } else if (dataScope.equals(DataScopeEnum.CUSTOM.getValue())) {
            // 自定义数据权限范围
            return sysRoleDataScopeDao.getDataScopeList(userDetail.getId());
        }

        return new ArrayList<>();
    }
}
