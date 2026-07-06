package com.finn.system.service;

import com.finn.system.entity.OpenUserEntity;
import com.finn.system.vo.UserVO;


/**
 * 第三方用户管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface OpenUserService {
    /**
     * 根据open id 获取用户信息
     * @param openId 第三方ID
     * @param userType 用户类型，1微信小程序用户
     * @return 用户实体
     */
    OpenUserEntity getByOpenId(String openId, String userType);

    /**
     * 新增
     * @param user
     * @return
     */
    long save(OpenUserEntity user);

    UserVO info(Long userId);

}
