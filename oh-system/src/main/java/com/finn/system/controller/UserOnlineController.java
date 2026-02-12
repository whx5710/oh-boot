package com.finn.system.controller;

import com.finn.core.utils.DateUtils;
import com.finn.core.utils.PageResult;
import com.finn.core.utils.Result;
import com.finn.framework.query.Query;
import com.finn.framework.security.cache.TokenStoreCache;
import com.finn.framework.security.user.UserDetail;
import com.finn.system.cache.UserCache;
import com.finn.system.convert.UserConvert;
import com.finn.system.entity.UserEntity;
import com.finn.system.vo.UserVO;
import com.finn.system.vo.UserOnlineVO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户在线监控，建议只提供给管理员权限
 * @author 王小费 whx5710@qq.com
 */
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
    public Result<PageResult<UserVO>> page(@Valid Query query) {
        // 获取登录用户的全部key
        List<String> userIds = tokenStoreCache.getUserIdList(); // tokenStoreCache.getUserKeyList();

        // 逻辑分页
        int toIndex = query.getPageNum() * query.getPageSize();
        if(toIndex > userIds.size()){
            toIndex = userIds.size();
        }
        List<String> keyList = userIds.subList((query.getPageNum() - 1) * query.getPageSize(), toIndex);

        List<UserVO> userOnlineList = new ArrayList<>();
        keyList.forEach(key -> {
            UserEntity user = userCache.getUser(Long.valueOf(key));
            if (user != null) {
                userOnlineList.add(UserConvert.INSTANCE.convert(user));
            }
        });
        return Result.ok(new PageResult<>(userOnlineList, userIds.size()));
    }

    /**
     * token列表
     * @param userId 用户ID
     * @return
     */
    @GetMapping("/tokenList/{userId}")
    @PreAuthorize("hasAuthority('monitor:user:tokens')")
    public Result<List<UserOnlineVO>> tokenList(@PathVariable("userId")Long userId) {
        List<UserDetail> list = tokenStoreCache.getUserById(userId);
        List<UserOnlineVO> result = new ArrayList<>();
        if(list != null && !list.isEmpty()){
            for(UserDetail user: list){
                UserOnlineVO userOnlineVO = new UserOnlineVO();
                userOnlineVO.setId(userId);
                userOnlineVO.setRealName(user.getRealName());
                userOnlineVO.setUsername(user.getUsername());
                userOnlineVO.setLoginTime(DateUtils.format(user.getLoginTime()));
                userOnlineVO.setAccessToken(user.getPassword());
                result.add(userOnlineVO);
            }
        }
        return Result.ok(result);
    }

    /**
     * 强制退出
     * @param accessToken token
     * @return 提示信息
     */
    @GetMapping("/forceLogout/{accessToken}")
    @PreAuthorize("hasAuthority('monitor:user:logout')")
    public Result<String> forceLogout(@PathVariable("accessToken") String accessToken) {
        // token不能为空
        if (accessToken == null || accessToken.isEmpty()) {
            Result.error("token不能为空");
        }
        UserDetail userDetail = tokenStoreCache.getUser(accessToken);
        Long userId = null;
        if(userDetail != null){
            userId = userDetail.getId();
        }
        // 删除用户信息
        tokenStoreCache.deleteUser(userId, accessToken);

        return Result.ok();
    }

    /**
     * 强制退出
     * @param userId 用户ID
     * @return 提示信息
     */
    @GetMapping("/forceLogoutAll/{userId}")
    @PreAuthorize("hasAuthority('monitor:user:logout')")
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
