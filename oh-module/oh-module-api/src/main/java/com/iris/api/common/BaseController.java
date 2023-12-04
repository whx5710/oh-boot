package com.iris.api.common;

import cn.hutool.json.JSONObject;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.vo.DataAppVO;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.exception.ServerException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class BaseController {

    @Resource
    RedisCache redisCache;

    protected JSONObject checkData(HttpServletRequest request){
//        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String clientId = request.getHeader("OH-CLIENT-ID");
        String secretKey = request.getHeader("OH-SECRET-KEY");
        String funcCode = request.getHeader("OH-FUNC-CODE");
        if(clientId == null || clientId.isEmpty()){
            throw new ServerException("客户端ID不能为空，请求非法");
        }
        if(secretKey == null || secretKey.isEmpty()){
            throw new ServerException("密钥不能为空，请求非法");
        }
        if(funcCode == null || funcCode.isEmpty()){
            throw new ServerException("参数错误【功能号 OH-FUNC-CODE 不能为空】");
        }

        Object obj = redisCache.get(RedisKeys.getClientKey(clientId + ":" + funcCode));
        if(obj == null){
            throw new ServerException("客户端未注册或接口未授权，请求非法");
        }

        List<DataAppVO> list = (List<DataAppVO>) obj;
        JSONObject object = new JSONObject();
        if(!list.isEmpty()){
            DataAppVO dataAppVO = list.get(0);
            if(!dataAppVO.getSecretKey().equals(secretKey)){
                throw new ServerException("密钥错误，请求非法");
            }
            object.put("isAsync", dataAppVO.getAsync());// 接口是否支持异步
        }

        DataMsgEntity data = new DataMsgEntity();
        data.setClientId(clientId);
        data.setFunCode(funcCode);
        object.put("data", data);
        return object;
    }
}
