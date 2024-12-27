package com.iris.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.utils.PageResult;
import com.iris.app.convert.DataFunctionConvert;
import com.iris.app.entity.DataFunctionEntity;
import com.iris.app.mapper.DataFunctionMapper;
import com.iris.app.query.DataFunctionQuery;
import com.iris.app.service.DataFunctionService;
import com.iris.app.vo.DataFunctionVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能列表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-30
 */
@Service
public class DataFunctionServiceImpl implements DataFunctionService {

    private final DataFunctionMapper dataFunctionMapper;

    public DataFunctionServiceImpl(DataFunctionMapper dataFunctionMapper){
        this.dataFunctionMapper = dataFunctionMapper;
    }

    @Override
    public PageResult<DataFunctionVO> page(DataFunctionQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<DataFunctionEntity> list = dataFunctionMapper.getList(query);
        PageInfo<DataFunctionEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(DataFunctionConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public PageResult<DataFunctionVO> pageByClientId(DataFunctionQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<DataFunctionEntity> list = dataFunctionMapper.pageByClientId(query.getClientId());
        PageInfo<DataFunctionEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(DataFunctionConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public DataFunctionEntity getById(Long id) {
        return dataFunctionMapper.getById(id);
    }

    @Override
    public List<DataFunctionVO> list(DataFunctionQuery query) {
        return DataFunctionConvert.INSTANCE.convertList(dataFunctionMapper.getList(query));
    }

    @Override
    public void save(DataFunctionVO vo) {
        DataFunctionEntity entity = DataFunctionConvert.INSTANCE.convert(vo);
        dataFunctionMapper.insertFunc(entity);
    }

    @Override
    public void update(DataFunctionVO vo) {
        DataFunctionEntity entity = DataFunctionConvert.INSTANCE.convert(vo);
        dataFunctionMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // removeByIds(idList);
        idList.forEach(id -> {
            DataFunctionEntity param = new DataFunctionEntity();
            param.setId(id);
            param.setDbStatus(0);
            dataFunctionMapper.updateById(param);
        });
    }

}