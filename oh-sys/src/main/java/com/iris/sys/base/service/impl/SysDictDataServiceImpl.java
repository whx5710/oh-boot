package com.iris.sys.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.sys.base.mapper.SysDictDataMapper;
import com.iris.sys.base.query.SysDictDataQuery;
import com.iris.sys.base.vo.SysDictDataVO;
import com.iris.sys.base.entity.SysDictDataEntity;
import com.iris.framework.common.utils.PageResult;
import com.iris.sys.base.convert.SysDictDataConvert;
import com.iris.sys.base.service.SysDictDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysDictDataEntity> list = sysDictDataMapper.getList(query);
        PageInfo<SysDictDataEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysDictDataConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictDataVO vo) {
        SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);

        sysDictDataMapper.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDataVO vo) {
        SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);
        sysDictDataMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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