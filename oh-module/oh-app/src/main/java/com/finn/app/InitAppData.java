package com.finn.app;

import com.finn.app.service.DataFunctionAuthorityService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * 客户端授权信息缓存
 */
@Component
public class InitAppData {

    private final DataFunctionAuthorityService dataFunctionAuthorityService;

    public InitAppData(DataFunctionAuthorityService dataFunctionAuthorityService){
        this.dataFunctionAuthorityService = dataFunctionAuthorityService;
    }

    @PostConstruct
    public void run(){
        // 客户端授权信息缓存
        dataFunctionAuthorityService.refreshAppAuthority(null);
    }
}
