package com.iris.team.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.utils.PageResult;
import com.iris.team.convert.OhProjectConvert;
import com.iris.team.entity.OhProjectEntity;
import com.iris.team.mapper.OhProjectMapper;
import com.iris.team.query.OhProjectQuery;
import com.iris.team.service.OhProjectService;
import com.iris.team.vo.OhProjectVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目信息表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@Service
public class OhProjectServiceImpl implements OhProjectService {

    private final OhProjectMapper ohProjectMapper;

    public OhProjectServiceImpl(OhProjectMapper ohProjectMapper){
        this.ohProjectMapper = ohProjectMapper;
    }

    @Override
    public PageResult<OhProjectVO> page(OhProjectQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<OhProjectEntity> list = ohProjectMapper.getList(query);
        PageInfo<OhProjectEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(OhProjectConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(OhProjectVO vo) {
        OhProjectEntity entity = OhProjectConvert.INSTANCE.convert(vo);
        ohProjectMapper.save(entity);
    }

    @Override
    public void update(OhProjectVO vo) {
        OhProjectEntity entity = OhProjectConvert.INSTANCE.convert(vo);
        ohProjectMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            OhProjectEntity param = new OhProjectEntity();
            param.setId(id);
            param.setDbStatus(0);
            ohProjectMapper.updateById(param);
        });
    }

    @Override
    public OhProjectEntity getById(Long id) {
        return ohProjectMapper.getById(id);
    }

}