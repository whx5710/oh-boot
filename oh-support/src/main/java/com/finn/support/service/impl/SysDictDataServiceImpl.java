package com.finn.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.finn.support.mapper.SysDictDataMapper;
import com.finn.support.query.SysDictDataQuery;
import com.finn.support.vo.SysDictDataVO;
import com.finn.support.entity.SysDictDataEntity;
import com.finn.core.utils.PageResult;
import com.finn.support.convert.SysDictDataConvert;
import com.finn.support.service.SysDictDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {
    private final SysDictDataMapper sysDictDataMapper;
    public SysDictDataServiceImpl(SysDictDataMapper sysDictDataMapper) {
        this.sysDictDataMapper = sysDictDataMapper;
    }

    @Override
    public PageResult<SysDictDataVO> page(SysDictDataQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<SysDictDataVO> list = sysDictDataMapper.getList(query);
        PageInfo<SysDictDataVO> pageInfo = new PageInfo<>(list);
        return new PageResult<>(list, pageInfo.getTotal());
    }

    @Override
    public void save(SysDictDataVO vo) {
        SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);

        sysDictDataMapper.save(entity);
    }

    @Override
    public void update(SysDictDataVO vo) {
        SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);
        sysDictDataMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            SysDictDataEntity param = new SysDictDataEntity();
            param.setId(id);
            param.setDbStatus(0);
            sysDictDataMapper.updateById(param);
        });
    }

    @Override
    public SysDictDataEntity getById(Long id) {
        return sysDictDataMapper.getById(id);
    }

}