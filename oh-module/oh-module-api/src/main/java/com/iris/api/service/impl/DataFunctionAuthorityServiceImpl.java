package com.iris.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.api.convert.DataFunctionAuthorityConvert;
import com.iris.api.dao.DataFunctionAuthorityDao;
import com.iris.api.entity.DataFunctionAuthorityEntity;
import com.iris.api.query.DataFunctionAuthorityQuery;
import com.iris.api.service.DataFunctionAuthorityService;
import com.iris.api.vo.DataFunctionAuthorityVO;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
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
public class DataFunctionAuthorityServiceImpl extends BaseServiceImpl<DataFunctionAuthorityDao, DataFunctionAuthorityEntity> implements DataFunctionAuthorityService {

    @Override
    public PageResult<DataFunctionAuthorityVO> page(DataFunctionAuthorityQuery query) {
        IPage<DataFunctionAuthorityEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(DataFunctionAuthorityConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<DataFunctionAuthorityEntity> getWrapper(DataFunctionAuthorityQuery query){
        LambdaQueryWrapper<DataFunctionAuthorityEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataFunctionAuthorityEntity::getDbStatus,1);
        if(!ObjectUtils.isEmpty(query.getClientId())){
            wrapper.eq(DataFunctionAuthorityEntity::getClientId, query.getClientId());
        }
        return wrapper;
    }

    @Override
    public void save(DataFunctionAuthorityVO vo) {
        DataFunctionAuthorityEntity entity = DataFunctionAuthorityConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(DataFunctionAuthorityVO vo) {
        DataFunctionAuthorityEntity entity = DataFunctionAuthorityConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
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
                QueryWrapper<DataFunctionAuthorityEntity> wrappers = new QueryWrapper<>();
                wrappers.lambda().eq(DataFunctionAuthorityEntity::getClientId, data.getClientId())
                        .eq(DataFunctionAuthorityEntity::getFuncCode, funcCode);
                remove(wrappers);
            }
        }else{
            for(String funcCode: data.getFuncCodes()){
                DataFunctionAuthorityEntity dae = new DataFunctionAuthorityEntity();
                dae.setFuncCode(funcCode);
                dae.setClientId(data.getClientId());
                this.baseMapper.insert(dae);
            }

        }
    }

}