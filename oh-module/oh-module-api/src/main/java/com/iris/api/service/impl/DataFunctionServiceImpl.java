package com.iris.api.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.api.convert.DataFunctionConvert;
import com.iris.api.entity.DataFunctionEntity;
import com.iris.api.query.DataFunctionQuery;
import com.iris.api.vo.DataFunctionVO;
import com.iris.api.dao.DataFunctionDao;
import com.iris.api.service.DataFunctionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能列表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-30
 */
@DS("extendDB")
@Service
public class DataFunctionServiceImpl extends BaseServiceImpl<DataFunctionDao, DataFunctionEntity> implements DataFunctionService {

    @Override
    public PageResult<DataFunctionVO> page(DataFunctionQuery query) {
        IPage<DataFunctionEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<>(DataFunctionConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    @Override
    public PageResult<DataFunctionVO> pageByClientId(DataFunctionQuery query) {
        IPage<DataFunctionEntity> page =  this.baseMapper.pageByClientId(getPage(query), query.getClientId());
        return new PageResult<>(DataFunctionConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    @Override
    public List<DataFunctionVO> list(DataFunctionQuery query) {
        return DataFunctionConvert.INSTANCE.convertList(baseMapper.selectList(getWrapper(query)));
    }

    private LambdaQueryWrapper<DataFunctionEntity> getWrapper(DataFunctionQuery query){
        LambdaQueryWrapper<DataFunctionEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataFunctionEntity::getDeleted, 0).orderByAsc(DataFunctionEntity::getFuncCode);
        return wrapper;
    }

    @Override
    public void save(DataFunctionVO vo) {
        DataFunctionEntity entity = DataFunctionConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(DataFunctionVO vo) {
        DataFunctionEntity entity = DataFunctionConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}