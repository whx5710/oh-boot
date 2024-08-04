package com.iris.system.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.system.base.dao.SysDictDataDao;
import com.iris.system.base.query.SysDictDataQuery;
import com.iris.system.base.vo.SysDictDataVO;
import com.iris.system.base.entity.SysDictDataEntity;
import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.convert.SysDictDataConvert;
import com.iris.system.base.service.SysDictDataService;
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
    private final SysDictDataDao sysDictDataDao;
    public SysDictDataServiceImpl(SysDictDataDao sysDictDataDao) {
        this.sysDictDataDao = sysDictDataDao;
    }

    @Override
    public PageResult<SysDictDataVO> page(SysDictDataQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysDictDataEntity> list = sysDictDataDao.getList(query);
        PageInfo<SysDictDataEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysDictDataConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictDataVO vo) {
        SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);

        sysDictDataDao.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDataVO vo) {
        SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);
        sysDictDataDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            SysDictDataEntity param = new SysDictDataEntity();
            param.setId(id);
            param.setDbStatus(0);
            sysDictDataDao.updateById(param);
        });
    }

    @Override
    public SysDictDataEntity getById(Long id) {
        return sysDictDataDao.getById(id);
    }

}