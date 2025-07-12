package com.finn.app.service.impl;

import com.finn.app.mapper.DataAppMapper;
import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.utils.PageResult;
import com.finn.core.exception.ServerException;
import com.finn.app.convert.DataFunctionAuthorityConvert;
import com.finn.app.entity.DataFunctionAuthorityEntity;
import com.finn.app.mapper.DataFunctionMapper;
import com.finn.app.query.DataFunctionAuthorityQuery;
import com.finn.app.service.DataFunctionAuthorityService;
import com.finn.app.vo.DataFunctionAuthorityVO;
import com.finn.framework.entity.api.DataAppDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户端接口授权
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
@Service
public class DataFunctionAuthorityServiceImpl implements DataFunctionAuthorityService {

    private final DataFunctionMapper dataFunctionMapper;

    private final RedisCache redisCache;

    private final DataAppMapper dataAppMapper;

    public DataFunctionAuthorityServiceImpl(DataFunctionMapper dataFunctionMapper, RedisCache redisCache,
                                            DataAppMapper dataAppMapper){
        this.dataFunctionMapper = dataFunctionMapper;
        this.redisCache = redisCache;
        this.dataAppMapper = dataAppMapper;
    }

    @Override
    public PageResult<DataFunctionAuthorityVO> page(DataFunctionAuthorityQuery query) {
        List<DataFunctionAuthorityEntity> list = dataFunctionMapper.getAuthorityList(query);
        return new PageResult<>(DataFunctionAuthorityConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public void save(DataFunctionAuthorityVO vo) {
        DataFunctionAuthorityEntity entity = DataFunctionAuthorityConvert.INSTANCE.convert(vo);
        dataFunctionMapper.insertFuncAuthority(entity);
    }

    @Override
    public void update(DataFunctionAuthorityVO vo) {
        DataFunctionAuthorityEntity entity = DataFunctionAuthorityConvert.INSTANCE.convert(vo);

        dataFunctionMapper.updateFuncAuthorityById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            DataFunctionAuthorityEntity param = new DataFunctionAuthorityEntity();
            param.setId(id);
            param.setDbStatus(0);
            dataFunctionMapper.updateFuncAuthorityById(param);
        });
    }

    /**
     * 客户端功能授权或取消授权
     * @param data
     */
    @Override
    public void make(DataFunctionAuthorityVO data) {
        // 1 新增 0 删除
        int i = data.getDbStatus();
        if(ObjectUtils.isEmpty(data.getFuncCode())){
            throw new ServerException("功能号不能为空");
        }
        if(ObjectUtils.isEmpty(data.getClientId())){
            throw new ServerException("客户端不能为空");
        }
        if(i == 0){ // 删除
            dataFunctionMapper.updateFuncAuthorityStatus(data.getClientId(), data.getFuncCode(), 0);
        }else{
            DataFunctionAuthorityEntity dae = new DataFunctionAuthorityEntity();
            dae.setFuncCode(data.getFuncCode());
            dae.setClientId(data.getClientId());
            dataFunctionMapper.insertFuncAuthority(dae);
        }
        // 刷新
        refreshAppAuthority(data.getClientId());
    }

    @Override
    public DataFunctionAuthorityEntity getById(Long id) {
        return dataFunctionMapper.getFuncAuthorityById(id);
    }

    /**
     * 刷新缓存
     * @param clientId 客户端ID
     */
    @Override
    public void refreshAppAuthority(String clientId) {
        DataFunctionAuthorityQuery params = new DataFunctionAuthorityQuery();
        // 先清除
        if(clientId == null || clientId.isEmpty()){
            redisCache.deleteAll(RedisKeys.getClientKey(""));
        }else{
            redisCache.deleteAll(RedisKeys.getClientKey(clientId));
            params.setClientId(clientId);
        }
        // 客户端权限信息
        List<DataAppDTO> list = dataAppMapper.listAuthority(params);
        if(!ObjectUtils.isEmpty(list)){
            // 根据客户端ID+ funcCode 进行分组
            Map<String,List<DataAppDTO>> map = list.stream().collect(
                    Collectors.groupingBy(item -> item.getClientId() + ":" + item.getFuncCode())
            );
            for (Map.Entry<String, List<DataAppDTO>> entry : map.entrySet()) {
                redisCache.set(RedisKeys.getClientKey(entry.getKey()), entry.getValue(), RedisCache.NOT_EXPIRE);
            }
        }
    }

    /**
     * 启动时初始化
     */
    @PostConstruct
    public void initData(){
        this.refreshAppAuthority(null);
    }
}