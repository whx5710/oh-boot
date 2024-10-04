package com.iris.sys.monitor.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.query.Query;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.common.utils.Result;
import com.iris.framework.security.cache.TokenStoreCache;
import com.iris.framework.security.user.UserDetail;
import com.iris.sys.monitor.vo.UserOnlineVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("monitor/user")
@Tag(name = "在线用户监控")
public class UserOnlineController {
    private final TokenStoreCache tokenStoreCache;
    private final RedisCache redisCache;

    public UserOnlineController(TokenStoreCache tokenStoreCache, RedisCache redisCache) {
        this.tokenStoreCache = tokenStoreCache;
        this.redisCache = redisCache;
    }

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('monitor:user:all')")
    public Result<PageResult<UserOnlineVO>> page(@ParameterObject @Valid Query query) {
        // 获取登录用户的全部key
        List<String> keys = tokenStoreCache.getUserKeyList();

        // 逻辑分页
        List<String> keyList = ListUtil.page(query.getPage() - 1, query.getLimit(), keys);

        List<UserOnlineVO> userOnlineList = new ArrayList<>();
        keyList.forEach(key -> {
            UserDetail user = (UserDetail) redisCache.get(key);
            if (user != null) {
                UserOnlineVO userOnlineVO = new UserOnlineVO();
                userOnlineVO.setId(user.getId());
                userOnlineVO.setUsername(user.getUsername());
                userOnlineVO.setRealName(user.getRealName());
                userOnlineVO.setGender(user.getGender());
                userOnlineVO.setEmail(user.getEmail());
                userOnlineVO.setAccessToken(key.replace(RedisKeys.getAccessTokenKey(""), ""));
                userOnlineVO.setLoginTime(DateUtil.format(new Date(user.getLoginTime()), "yyyy-MM-dd HH:mm:ss"));
                userOnlineVO.setIp(user.getIp());
                userOnlineList.add(userOnlineVO);
            }

        });

        return Result.ok(new PageResult<>(userOnlineList, keys.size()));
    }

    @DeleteMapping("{accessToken}")
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
}
