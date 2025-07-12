package com.finn.sys.base.controller;

import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.framework.query.Query;
import com.finn.framework.security.cache.TokenStoreCache;
import com.finn.support.cache.UserCache;
import com.finn.support.entity.UserEntity;
import com.finn.sys.base.vo.UserOnlineVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("monitor/user")
public class UserOnlineController {
    private final TokenStoreCache tokenStoreCache;
    private final UserCache userCache;

    public UserOnlineController(TokenStoreCache tokenStoreCache, UserCache userCache) {
        this.tokenStoreCache = tokenStoreCache;
        this.userCache = userCache;
    }

    /**
     * 分页
     * @param query 查询条件
     * @return 列表
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('monitor:user:all')")
    public Result<PageResult<UserOnlineVO>> page(@Valid Query query) {
        // 获取登录用户的全部key
        List<String> userIds = tokenStoreCache.getUserIdList(); // tokenStoreCache.getUserKeyList();

        // 逻辑分页
        List<String> keyList = userIds.subList((query.getPageNum() - 1) * query.getPageSize(), query.getPageNum() * query.getPageSize());

        List<UserOnlineVO> userOnlineList = new ArrayList<>();
        keyList.forEach(key -> {
            UserEntity user = userCache.getUser(Long.valueOf(key));
            if (user != null) {
                UserOnlineVO userOnlineVO = new UserOnlineVO();
                userOnlineVO.setId(user.getId());
                userOnlineVO.setUsername(user.getUsername());
                userOnlineVO.setRealName(user.getRealName());
                userOnlineVO.setGender(user.getGender());
                userOnlineVO.setEmail(user.getEmail());
//                userOnlineVO.setAccessToken(key.replace(RedisKeys.getAccessTokenKey(""), ""));
//                userOnlineVO.setLoginTime(DateUtil.format(user.getLoginTime(), "yyyy-MM-dd HH:mm:ss"));
//                userOnlineVO.setIp(user.getIp());
                userOnlineList.add(userOnlineVO);
            }
        });

        return Result.ok(new PageResult<>(userOnlineList, userIds.size()));
    }

    /**
     * 强制退出
     * @param accessToken token
     * @return 提示信息
     */
    @GetMapping("/forceLogout/{accessToken}")
    @PreAuthorize("hasAuthority('monitor:user:user')")
    public Result<String> forceLogout(@PathVariable("accessToken") String accessToken) {
        // token不能为空
        if (accessToken == null || accessToken.isEmpty()) {
            Result.error("token不能为空");
        }
        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);

        return Result.ok();
    }

    /**
     * 强制退出
     * @param userId 用户ID
     * @return 提示信息
     */
    @GetMapping("/forceLogoutAll/{userId}")
    @PreAuthorize("hasAuthority('monitor:user:user')")
    public Result<String> forceLogoutAll(@PathVariable("userId") String userId) {
        // token不能为空
        if (userId == null || userId.isEmpty()) {
            Result.error("用户ID不能为空");
        }
        // 删除用户信息
        tokenStoreCache.deleteUserById(Long.valueOf(userId));
        return Result.ok();
    }

}
