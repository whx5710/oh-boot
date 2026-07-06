package com.finn.system.service.impl;

import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.Wrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.utils.AssertUtils;
import com.finn.system.convert.UserConvert;
import com.finn.system.entity.OpenUserEntity;
import com.finn.system.mapper.OpenUserMapper;
import com.finn.system.service.OpenUserService;
import com.finn.system.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 第三方用户
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class OpenUserServiceImpl implements OpenUserService {
    private final OpenUserMapper openUserMapper;

    public OpenUserServiceImpl(OpenUserMapper openUserMapper) {
        this.openUserMapper = openUserMapper;
    }

    /**
     * 根据open id 获取用户信息
     * @param openId 第三方ID
     * @param userType 用户类型，1微信小程序用户
     * @return 用户实体
     */
    @Override
    public OpenUserEntity getByOpenId(String openId, String userType) {
        AssertUtils.isBlank(openId, "第三方ID");
        Wrapper<OpenUserEntity> queryWrapper = QueryWrapper.of(OpenUserEntity.class).eq(OpenUserEntity::getDbStatus, 1)
                .eq(OpenUserEntity::getOpenId, openId).eq(OpenUserEntity::getUserType, userType);
        List<OpenUserEntity> list = openUserMapper.listByWrapper(queryWrapper);
        if(list != null){
            if(list.size() > 1){
                throw new ServerException("第三方ID对应多个账号，请检查");
            }
            if(list.isEmpty()){
                return null;
            }
            return list.getFirst();
        }else{
            return null;
        }
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    public long save(OpenUserEntity user) {
        AssertUtils.isBlank(user.getOpenId(), "第三方用户ID");
        AssertUtils.isBlank(user.getUserType(), "用户类型");
        return openUserMapper.insert(user);
    }

    @Override
    public UserVO info(Long userId) {
        UserVO user = new UserVO();
        if(ObjectUtils.isEmpty(userId)){
            user = UserConvert.INSTANCE.convert(SecurityUser.getUser());
            userId = user.getId();
        }
        // 重新查询，防止修改基本信息后，缓存未更新的情况
        OpenUserEntity entity = openUserMapper.findById(userId, OpenUserEntity.class);
        user.setId(entity.getId());
        user.setUsername(entity.getUserName());
        user.setRealName(entity.getRealName());
        user.setAvatar(entity.getAvatar());
        user.setGender(entity.getGender());
        user.setEmail(entity.getEmail());
        user.setMobile(entity.getMobile());
        user.setStatus(entity.getStatus());
        user.setOpenId(entity.getOpenId());
        user.setCreateTime(entity.getCreateTime());
        // 用户角色列表
        user.setRoleIdList(new ArrayList<>());

        // 用户岗位列表
        user.setPostIdList(new ArrayList<>());
        return user;
    }
}
