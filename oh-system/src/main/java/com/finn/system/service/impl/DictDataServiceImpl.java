package com.finn.system.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.system.convert.DictDataConvert;
import com.finn.system.entity.DictDataEntity;
import com.finn.system.mapper.DictDataMapper;
import com.finn.system.query.DictDataQuery;
import com.finn.system.service.DictDataService;
import com.finn.system.vo.DictDataVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class DictDataServiceImpl implements DictDataService {
    private final DictDataMapper dictDataMapper;
    public DictDataServiceImpl(DictDataMapper dictDataMapper) {
        this.dictDataMapper = dictDataMapper;
    }

    @Override
    public PageResult<DictDataVO> page(DictDataQuery query) {
        Page<DictDataEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<DictDataVO> list = dictDataMapper.getList(query);
        return new PageResult<>(list, page.getTotal());
    }

    @Override
    public void save(DictDataVO vo) {
        DictDataEntity entity = DictDataConvert.INSTANCE.convert(vo);

        dictDataMapper.save(entity);
    }

    @Override
    public void update(DictDataVO vo) {
        DictDataEntity entity = DictDataConvert.INSTANCE.convert(vo);
        dictDataMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            DictDataEntity param = new DictDataEntity();
            param.setId(id);
            param.setDbStatus(0);
            dictDataMapper.updateById(param);
        });
    }

    @Override
    public DictDataEntity getById(Long id) {
        return dictDataMapper.getById(id);
    }

}