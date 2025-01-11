package com.iris.sys.monitor.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.framework.query.Query;
import com.iris.framework.security.cache.TokenStoreCache;
import com.iris.support.cache.SysUserCache;
import com.iris.support.entity.SysUserEntity;
import com.iris.sys.monitor.vo.UserOnlineVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("monitor/user")
@Tag(name = "在线用户监控")
public class UserOnlineController {
    private final TokenStoreCache tokenStoreCache;
    private final SysUserCache sysUserCache;

    public UserOnlineController(TokenStoreCache tokenStoreCache, SysUserCache sysUserCache) {
        this.tokenStoreCache = tokenStoreCache;
        this.sysUserCache = sysUserCache;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('monitor:user:all')")
    public Result<PageResult<UserOnlineVO>> page(@ParameterObject @Valid Query query) {
        // 获取登录用户的全部key
        List<String> userIds = tokenStoreCache.getUserIdList(); // tokenStoreCache.getUserKeyList();

        // 逻辑分页
        List<String> keyList = ListUtil.page(query.getPageNum() - 1, query.getPageSize(), userIds);

        List<UserOnlineVO> userOnlineList = new ArrayList<>();
        keyList.forEach(key -> {
            SysUserEntity user = sysUserCache.getUser(Long.valueOf(key));
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

    @GetMapping("/forceLogout/{accessToken}")
    @Operation(summary = "强制退出")
    @PreAuthorize("hasAuthority('monitor:user:user')")
    public Result<String> forceLogout(@PathVariable("accessToken") String accessToken) {
        // token不能为空
        if (StrUtil.isBlank(accessToken)) {
            Result.error("token不能为空");
        }
        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);

        return Result.ok();
    }

    @GetMapping("/forceLogoutAll/{userId}")
    @Operation(summary = "强制退出")
    @PreAuthorize("hasAuthority('monitor:user:user')")
    public Result<String> forceLogoutAll(@PathVariable("userId") String userId) {
        // token不能为空
        if (StrUtil.isBlank(userId)) {
            Result.error("用户ID不能为空");
        }
        // 删除用户信息
        tokenStoreCache.deleteUserById(Long.valueOf(userId));
        return Result.ok();
    }

}
