package com.finn.framework.security.cache;

import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.framework.common.properties.SecurityProperties;
import com.finn.framework.security.user.RefreshTokenInfo;
import com.finn.framework.security.user.UserDetail;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 认证 Cache
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class TokenStoreCache {
    private final RedisCache redisCache;

    private final SecurityProperties securityProperties;

    public TokenStoreCache(RedisCache redisCache, SecurityProperties securityProperties) {
        this.redisCache = redisCache;
        this.securityProperties = securityProperties;
    }

    /**
     * 保存用户信息到redis中
     * @param accessToken
     * @param user
     */
    public void saveUser(String accessToken, String refreshToken, UserDetail user) {
        // token用户信息
        String key = RedisKeys.getAccessTokenKey(accessToken);
        user.setPassword("");
        if(user.getSuperAdmin() == 1){
            user.setTenantId(""); // 租户ID为空，都用空字符串
        }
        redisCache.set(key, user, securityProperties.getAccessTokenExpire());

        // 刷新token，如果是重新刷新token，刷新token使用旧的有效期
        if(refreshToken != null && !refreshToken.isEmpty()){
            RefreshTokenInfo refreshTokenInfo = getRefreshTokenInfo(accessToken, refreshToken, user);
            String refreshKey = RedisKeys.getAccessRefreshTokenKey(refreshToken);
            redisCache.set(refreshKey, refreshTokenInfo, user.getRefreshTokenExpire());
        }

        // 用户信息
        redisCache.set(RedisKeys.getUserInfoKey(String.valueOf(user.getId()), accessToken), user, securityProperties.getAccessTokenExpire());
    }

    /**
     * 生成刷新token
     * @param accessToken
     * @param refreshToken
     * @param user
     * @return
     */
    private static RefreshTokenInfo getRefreshTokenInfo(String accessToken, String refreshToken, UserDetail user) {
        RefreshTokenInfo refreshTokenInfo = new RefreshTokenInfo();
        refreshTokenInfo.setId(user.getId());
        refreshTokenInfo.setUsername(user.getUsername());
        refreshTokenInfo.setRealName(user.getRealName());
        refreshTokenInfo.setIp(user.getIp());

        refreshTokenInfo.setRefreshToken(refreshToken); // 刷新token
        refreshTokenInfo.setAccessToken(accessToken); // token
        refreshTokenInfo.setExpiresIn(user.getRefreshTokenExpire()); // 刷新token有效时长
        return refreshTokenInfo;
    }

    /**
     * 根据token获取用户
     * @param accessToken
     * @return
     */
    public UserDetail getUser(String accessToken) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        return (UserDetail) redisCache.get(key);
    }

    /**
     * 根据token，删除redis
     * @param userId 用户ID
     * @param accessToken token
     */
    public void deleteUser(Long userId, String accessToken) {
        // 删除token
        String key = RedisKeys.getAccessTokenKey(accessToken);
        redisCache.delete(key);
        // 删除用户
        if(userId != null){
            redisCache.delete(RedisKeys.getUserInfoKey(String.valueOf(userId), accessToken));
        }
    }

    /**
     * 根据userId，删除redis
     * @param userId token
     */
    public void deleteUserById(Long userId) {
        List<String> tokens = this.getTokenByUserId(userId);
        for(String token : tokens){
            deleteUser(userId, token);
        }
        String key = RedisKeys.getUserInfoKey(String.valueOf(userId), "");
        redisCache.deleteAll(key);
    }


    /**
     * 获取所有用户token的key
     * @return list
     */
    public List<String> getUserKeyList() {
        String pattern = RedisKeys.getAccessTokenKey("*");
        Set<String> sets = redisCache.keys(pattern);
        List<String> list = new ArrayList<>();
        if(sets != null){
            list.addAll(sets);
        }
        return list;
    }

    /**
     * 获取所有用户ID的key
     * @return list
     */
    public List<String> getUserIdList() {
        String pattern = RedisKeys.getUserInfoKey("*", "*");
        Set<String> sets = redisCache.keys(pattern);
        List<String> list = new ArrayList<>();
        if(sets != null){
            Set<String> tmp = new HashSet<>();
            for(String key: sets){
                String s = key.substring(0, key.lastIndexOf(":"));
                tmp.add(s.substring(s.lastIndexOf(":") + 1));
            }
            list.addAll(tmp);
        }
        return list;
    }

    /**
     * 根据用户ID获取所有token
     * @return list
     */
    public List<String> getTokenByUserId(Long userId) {
        String pattern = RedisKeys.getUserInfoKey(String.valueOf(userId), "*");
        Set<String> sets = redisCache.keys(pattern);
        List<String> list = new ArrayList<>();
        if(sets != null){
            Set<String> tmp = new HashSet<>();
            for(String key: sets){
                tmp.add(key.substring(key.lastIndexOf(":") + 1));
            }
            list.addAll(tmp);
        }
        return list;
    }

    /**
     * 根据用户ID获取用户登录情况
     * @param userId 用户ID
     * @return list
     */
    public List<UserDetail> getUserById(Long userId){
        String pattern = RedisKeys.getUserInfoKey(String.valueOf(userId), "*");
        Set<String> sets = redisCache.keys(pattern);
        List<UserDetail> list = new ArrayList<>();
        if(sets != null){
            for(String key: sets){
                UserDetail userDetail = (UserDetail) redisCache.get(key);
                userDetail.setPassword(key.substring(key.lastIndexOf(":") + 1));
                list.add(userDetail);
            }
            list = list.stream().sorted(Comparator.comparing(UserDetail::getLoginTime).reversed())
                    .toList();
        }
        return list;
    }

}
