package com.finn.framework.security.exception;

import com.finn.core.exception.ErrorCode;
import com.finn.core.utils.HttpContextUtils;
import com.finn.core.utils.IpUtils;
import com.finn.core.utils.JsonUtils;
import com.finn.core.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 匿名用户(token不存在、错误)，异常处理器
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final Logger log = LoggerFactory.getLogger(SecurityAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if(HttpContextUtils.getOrigin() != null){
            response.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        }
        // authException.printStackTrace();
        Result<Object> result = Result.error(ErrorCode.UNAUTHORIZED);
        String ip = IpUtils.getIpAddress(request);
        log.warn("IP:{} 请求方法:{} 请求路径:{} {}", ip, request.getMethod(), request.getRequestURI(), result.getMsg());
        response.getWriter().print(JsonUtils.toJsonString(result));
    }
}