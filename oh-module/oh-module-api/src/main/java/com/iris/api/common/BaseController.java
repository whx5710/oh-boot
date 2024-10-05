package com.iris.api.common;

import com.iris.framework.cache.RedisCache;
import com.iris.framework.cache.RedisKeys;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.entity.api.DataAppDTO;
import com.iris.framework.entity.api.MsgEntity;
import com.iris.framework.exception.ServerException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class BaseController {

    @Resource
    RedisCache redisCache;

    /**
     * 校验接口基本信息
     * @param request
     * @return
     */
    protected MsgEntity basicCheck(HttpServletRequest request){
        String clientId = request.getHeader(Constant.CLIENT_ID);
        String secretKey = request.getHeader(Constant.SECRET_KEY);
        String funcCode = request.getHeader(Constant.FUNC_CODE);
        if(clientId == null || clientId.isEmpty()){
            throw new ServerException("客户端ID不能为空，请求非法");
        }
        if(secretKey == null || secretKey.isEmpty()){
            throw new ServerException("密钥不能为空，请求非法");
        }
        if(funcCode == null || funcCode.isEmpty()){
            throw new ServerException("参数错误【功能号 "+ Constant.FUNC_CODE +" 不能为空】");
        }

        Object obj = redisCache.get(RedisKeys.getClientKey(clientId + ":" + funcCode));
        if(obj == null){
            throw new ServerException("客户端未注册或接口未授权，请求非法");
        }

        List<DataAppDTO> list = (List<DataAppDTO>) obj;
        MsgEntity msgEntity = new MsgEntity();
        if(!list.isEmpty()){
            DataAppDTO dataAppDTO = list.getFirst();
            if(!dataAppDTO.getSecretKey().equals(secretKey)){
                throw new ServerException("密钥错误，请求非法");
            }
            msgEntity.setAsync(dataAppDTO.getAsync()); // 接口是否支持异步
        }
        msgEntity.setClientId(clientId);
        msgEntity.setFuncCode(funcCode);
        return msgEntity;
    }
}
