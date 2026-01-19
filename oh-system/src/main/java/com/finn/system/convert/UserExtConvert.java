package com.finn.system.convert;

import com.finn.core.entity.BaseUserEntity;
import com.finn.framework.security.user.UserDetail;
import com.finn.framework.utils.ServiceFactory;
import com.finn.system.cache.TenantCache;
import com.finn.system.entity.UserEntity;
import com.finn.system.vo.UserExcelVO;
import com.finn.system.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义转换
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-06-08
 */
public class UserExtConvert implements UserConvert {

    private final UserConvert userConvert;
    // 租户缓存工具
    TenantCache tenantCache = ServiceFactory.getBean("tenantCache", TenantCache.class);

    public UserExtConvert(UserConvert userConvert){
        this.userConvert = userConvert;
    }

    @Override
    public UserVO convert(UserEntity entity) {
        UserVO vo = userConvert.convert(entity);
        // 租户名称获取
        vo.setTenantName(tenantCache.getNameByTenantId(entity.getTenantId()));
        return vo;
    }

    @Override
    public UserEntity convert(UserVO vo) {
        return userConvert.convert(vo);
    }

    @Override
    public UserVO convert(UserDetail userDetail) {
        UserVO vo = userConvert.convert(userDetail);
        // 租户名称获取
        vo.setTenantName(tenantCache.getNameByTenantId(userDetail.getTenantId()));
        return vo;
    }

    @Override
    public UserDetail convertDetail(UserEntity entity) {
        return userConvert.convertDetail(entity);
    }

    @Override
    public List<UserVO> convertList(List<UserEntity> list) {
        if (list == null) {
            return null;
        } else {
            List<UserVO> list1 = new ArrayList<>(list.size());

            for (UserEntity userEntity : list) {
                list1.add(this.convert(userEntity));
            }
            return list1;
        }
    }

    @Override
    public List<UserExcelVO> convert2List(List<UserEntity> list) {
        if (list == null) {
            return null;
        } else {
            List<UserExcelVO> list1 = new ArrayList<>(list.size());

            for (UserEntity userEntity : list) {
                list1.add(this.convert2Excel(userEntity));
            }
            return list1;
        }
    }

    @Override
    public UserExcelVO convert2Excel(UserEntity entity) {
        UserExcelVO vo = userConvert.convert2Excel(entity);
        // 租户名称获取
        vo.setTenantName(tenantCache.getNameByTenantId(entity.getTenantId()));
        return vo;
    }

    @Override
    public UserEntity convert(UserExcelVO vo) {
        return userConvert.convert(vo);
    }

    @Override
    public List<UserEntity> convertListEntity(List<UserExcelVO> list) {
        if (list == null) {
            return null;
        } else {
            List<UserEntity> list1 = new ArrayList<>(list.size());

            for (UserExcelVO userExcelVO : list) {
                list1.add(this.convert(userExcelVO));
            }
            return list1;
        }
    }

    @Override
    public BaseUserEntity convertSimpleVO(UserEntity entity) {
        return userConvert.convertSimpleVO(entity);
    }
}
