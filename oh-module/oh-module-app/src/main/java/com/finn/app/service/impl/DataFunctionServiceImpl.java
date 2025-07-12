package com.finn.app.service.impl;

import com.finn.core.exception.ServerException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.utils.PageResult;
import com.finn.app.convert.DataFunctionConvert;
import com.finn.app.entity.DataFunctionEntity;
import com.finn.app.mapper.DataFunctionMapper;
import com.finn.app.query.DataFunctionQuery;
import com.finn.app.service.DataFunctionService;
import com.finn.app.vo.DataFunctionVO;
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
        Page<DataFunctionEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<DataFunctionEntity> list = dataFunctionMapper.getList(query);
        return new PageResult<>(DataFunctionConvert.INSTANCE.convertList(list), page.getTotal());
    }

    @Override
    public PageResult<DataFunctionVO> pageByClientId(DataFunctionQuery query) {
        if(query.getClientId() == null || query.getClientId().isEmpty()){
            throw new ServerException("客户端ID不能为空！");
        }
        Page<DataFunctionEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<DataFunctionVO> list = dataFunctionMapper.pageByClientId(query.getClientId());
        return new PageResult<>(list, page.getTotal());
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