package com.iris.team.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.team.convert.OhProjectLogConvert;
import com.iris.team.dao.OhProjectLogDao;
import com.iris.team.entity.OhProjectLogEntity;
import com.iris.team.query.OhProjectLogQuery;
import com.iris.team.service.OhProjectLogService;
import com.iris.team.vo.OhProjectLogVO;
import com.iris.framework.common.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目、任务操作日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@Service
public class OhProjectLogServiceImpl implements OhProjectLogService {

    private final OhProjectLogDao ohProjectLogDao;

    public OhProjectLogServiceImpl(OhProjectLogDao ohProjectLogDao){
        this.ohProjectLogDao = ohProjectLogDao;
    }

    @Override
    public PageResult<OhProjectLogVO> page(OhProjectLogQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<OhProjectLogEntity> list = ohProjectLogDao.getList(query);
        PageInfo<OhProjectLogEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(OhProjectLogConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(OhProjectLogVO vo) {
        OhProjectLogEntity entity = OhProjectLogConvert.INSTANCE.convert(vo);
        ohProjectLogDao.save(entity);
    }

    @Override
    public void update(OhProjectLogVO vo) {
        OhProjectLogEntity entity = OhProjectLogConvert.INSTANCE.convert(vo);

        ohProjectLogDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            OhProjectLogEntity param = new OhProjectLogEntity();
            param.setId(id);
            param.setDbStatus(0);
            ohProjectLogDao.updateById(param);
        });
    }

    @Override
    public OhProjectLogEntity getById(Long id) {
        return ohProjectLogDao.getById(id);
    }

}