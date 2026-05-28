package com.finn.framework.cache;

import com.finn.framework.common.properties.SecurityProperties;
import com.finn.framework.security.user.RefreshTokenInfo;
import com.finn.framework.security.user.UserDetail;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 认证 Cache
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class TokenCache {
    private final RedisCache redisCache;

    private final SecurityProperties securityProperties;

    public TokenCache(RedisCache redisCache, SecurityProperties securityProperties) {
        this.redisCache = redisCache;
        this.securityProperties = securityProperties;
    }

    /**
     * 保存用户信息到redis中
     * @param accessToken a
     * @param user u
     */
    public void saveUser(String accessToken, String refreshToken, UserDetail user) {
        // token用户信息
        String key = RedisKeys.getAccessTokenKey(accessToken);
        user.setPassword("");
        if(user.getSuperAdmin() == 1){
            user.setTenantId(null); // 租户ID为空，都用空字符串
        }
        RefreshTokenInfo refreshTokenInfo = getRefreshTokenInfo(accessToken, refreshToken, user);
        // token
        redisCache.set(key, refreshTokenInfo, securityProperties.getAccessTokenExpire());

        // 刷新token，如果是重新刷新token，刷新token使用旧的有效期
        String refreshKey = RedisKeys.getAccessRefreshTokenKey(refreshToken);
        redisCache.set(refreshKey, refreshTokenInfo, user.getRefreshTokenExpire());

        // 用户信息
        redisCache.set(RedisKeys.getUserInfoKey(String.valueOf(user.getId()), accessToken), user, securityProperties.getAccessTokenExpire());

        // 将用户ID添加到在线用户集合（ZSet），score 为过期时间戳
        long expireTime = System.currentTimeMillis() + (securityProperties.getAccessTokenExpire() * 1000);
        redisCache.zAdd(RedisKeys.getOnlineUserIdSetKey(), String.valueOf(user.getId()), expireTime);
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
        RefreshTokenInfo refreshTokenInfo = (RefreshTokenInfo) redisCache.get(key);
        // 用户ID和token
        if(refreshTokenInfo != null){
            key = RedisKeys.getUserInfoKey(String.valueOf(refreshTokenInfo.getId()), accessToken);
            return (UserDetail) redisCache.get(key);
        }else{
            return null;
        }
    }

    /**
     * 根据token，删除token,用户
     * @param userId 用户ID
     * @param accessToken token
     */
    public void deleteUser(Long userId, String accessToken) {
        // 删除token
        String key = RedisKeys.getAccessTokenKey(accessToken);
        RefreshTokenInfo refreshTokenInfo = (RefreshTokenInfo) redisCache.get(key);
        // 修复：添加空值检查，避免NPE
        if (refreshTokenInfo == null) {
            return;
        }
        String refresh = refreshTokenInfo.getRefreshToken();
        redisCache.delete(key);
        // 删除用户
        if(userId != null){
            redisCache.delete(RedisKeys.getUserInfoKey(String.valueOf(userId), accessToken));
            // 检查该用户是否还有其他token，如果没有则从在线用户集合中移除
            List<String> remainingTokens = getTokenByUserId(userId);
            if (remainingTokens.isEmpty() || (remainingTokens.size() == 1 && remainingTokens.getFirst().equals(accessToken))) {
                redisCache.zRemove(RedisKeys.getOnlineUserIdSetKey(), String.valueOf(userId));
            }
        }
        // 删除刷新token
        redisCache.delete(RedisKeys.getAccessRefreshTokenKey(refresh));
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
        // 删除刷新token
        String key = RedisKeys.getUserInfoKey(String.valueOf(userId), "");
        redisCache.deleteAll(key);
        // 从在线用户集合中移除
        redisCache.zRemove(RedisKeys.getOnlineUserIdSetKey(), String.valueOf(userId));
    }


    /**
     * 获取所有用户token的key（使用 SCAN 避免阻塞 Redis）
     * @return list
     */
    public List<String> getUserKeyList() {
        String pattern = RedisKeys.getAccessTokenKey("*");
        return new ArrayList<>(redisCache.scanKeys(pattern));
    }

    /**
     * 获取所有在线用户ID（使用 ZSet，自动过滤过期用户）
     * @return list
     */
    public List<String> getUserIdList() {
        // 先清理已过期的用户ID（score <= 当前时间戳）
        long currentTime = System.currentTimeMillis();
        redisCache.zRemoveRangeByScore(RedisKeys.getOnlineUserIdSetKey(), currentTime);
        // 获取未过期的用户ID（score > 当前时间戳）
        return new ArrayList<>(redisCache.zRangeByScore(RedisKeys.getOnlineUserIdSetKey(), Long.MAX_VALUE));
    }

    /**
     * 根据用户ID获取所有token（使用 SCAN 避免阻塞 Redis）
     * @return list
     */
    public List<String> getTokenByUserId(Long userId) {
        String pattern = RedisKeys.getUserInfoKey(String.valueOf(userId), "*");
        Set<String> tokens = new HashSet<>();
        redisCache.scan(pattern, key -> tokens.add(key.substring(key.lastIndexOf(":") + 1)));
        return new ArrayList<>(tokens);
    }

    /**
     * 根据用户ID获取用户登录情况（使用 SCAN 避免阻塞 Redis）
     * @param userId 用户ID
     * @return list
     */
    public List<UserDetail> getUserById(Long userId) {
        String pattern = RedisKeys.getUserInfoKey(String.valueOf(userId), "*");
        List<UserDetail> list = new ArrayList<>();
        redisCache.scan(pattern, key -> {
            UserDetail userDetail = (UserDetail) redisCache.get(key);
            if (userDetail != null) {
                userDetail.setPassword(key.substring(key.lastIndexOf(":") + 1));
                list.add(userDetail);
            }
        });
        return list.stream()
                .sorted(Comparator.comparing(UserDetail::getLoginTime).reversed())
                .toList();
    }

}
