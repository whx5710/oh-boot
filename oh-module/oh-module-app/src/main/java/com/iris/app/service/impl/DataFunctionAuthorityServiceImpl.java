package com.iris.app.service.impl;

import com.iris.core.utils.PageResult;
import com.iris.core.exception.ServerException;
import com.iris.app.convert.DataFunctionAuthorityConvert;
import com.iris.app.entity.DataFunctionAuthorityEntity;
import com.iris.app.mapper.DataFunctionMapper;
import com.iris.app.query.DataFunctionAuthorityQuery;
import com.iris.app.service.DataFunctionAuthorityService;
import com.iris.app.vo.DataFunctionAuthorityVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 客户端接口授权
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
@Service
public class DataFunctionAuthorityServiceImpl implements DataFunctionAuthorityService {

    private final DataFunctionMapper dataFunctionMapper;

    public DataFunctionAuthorityServiceImpl(DataFunctionMapper dataFunctionMapper){
        this.dataFunctionMapper = dataFunctionMapper;
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
        if(ObjectUtils.isEmpty(data.getFuncCodes())){
            throw new ServerException("功能号不能为空");
        }
        if(ObjectUtils.isEmpty(data.getClientId())){
            throw new ServerException("客户端不能为空");
        }
        if(i == 0){ // 删除
            for(String funcCode: data.getFuncCodes()){
                dataFunctionMapper.updateFuncAuthorityStatus(data.getClientId(), funcCode, 0);
            }
        }else{
            for(String funcCode: data.getFuncCodes()){
                DataFunctionAuthorityEntity dae = new DataFunctionAuthorityEntity();
                dae.setFuncCode(funcCode);
                dae.setClientId(data.getClientId());
                dataFunctionMapper.insertFuncAuthority(dae);
            }

        }
    }

    @Override
    public DataFunctionAuthorityEntity getById(Long id) {
        return dataFunctionMapper.getFuncAuthorityById(id);
    }

}