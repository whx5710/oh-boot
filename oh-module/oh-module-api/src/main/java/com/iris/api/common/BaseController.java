package com.iris.api.common;

import com.iris.api.constant.ConstantApi;
import com.iris.api.vo.DataAppVO;
import com.iris.api.vo.MsgVO;
import com.iris.framework.common.cache.RedisCache;
import com.iris.framework.common.cache.RedisKeys;
import com.iris.framework.common.entity.MetaEntity;
import com.iris.framework.common.exception.ServerException;
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
    protected MsgVO basicCheck(HttpServletRequest request){
        String clientId = request.getHeader(ConstantApi.CLIENT_ID);
        String secretKey = request.getHeader(ConstantApi.SECRET_KEY);
        String funcCode = request.getHeader(ConstantApi.FUNC_CODE);
        if(clientId == null || clientId.isEmpty()){
            throw new ServerException("客户端ID不能为空，请求非法");
        }
        if(secretKey == null || secretKey.isEmpty()){
            throw new ServerException("密钥不能为空，请求非法");
        }
        if(funcCode == null || funcCode.isEmpty()){
            throw new ServerException("参数错误【功能号 "+ ConstantApi.FUNC_CODE +" 不能为空】");
        }

        Object obj = redisCache.get(RedisKeys.getClientKey(clientId + ":" + funcCode));
        if(obj == null){
            throw new ServerException("客户端未注册或接口未授权，请求非法");
        }

        List<DataAppVO> list = (List<DataAppVO>) obj;
        MsgVO msgVO = new MsgVO();
        if(!list.isEmpty()){
            DataAppVO dataAppVO = list.getFirst();
            if(!dataAppVO.getSecretKey().equals(secretKey)){
                throw new ServerException("密钥错误，请求非法");
            }
            msgVO.setAsync(dataAppVO.getAsync()); // 接口是否支持异步
        }
        MetaEntity metaEntity = new MetaEntity();
        metaEntity.setClientId(clientId);
        metaEntity.setFuncCode(funcCode);
        msgVO.setMetaEntity(metaEntity);
        return msgVO;
    }
}
