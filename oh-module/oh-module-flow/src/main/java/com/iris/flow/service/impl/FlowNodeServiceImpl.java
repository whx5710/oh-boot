package com.iris.flow.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.flow.convert.FlowNodeConvert;
import com.iris.flow.mapper.FlowNodeMapper;
import com.iris.flow.entity.FlowNodeEntity;
import com.iris.flow.query.FlowNodeQuery;
import com.iris.flow.service.FlowNodeService;
import com.iris.flow.vo.FlowNodeVO;
import com.iris.framework.common.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 环节定义表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-01-31
 */
@Service
public class FlowNodeServiceImpl implements FlowNodeService {

    private final FlowNodeMapper flowNodeMapper;

    public FlowNodeServiceImpl(FlowNodeMapper flowNodeMapper){
        this.flowNodeMapper = flowNodeMapper;
    }

    @Override
    public PageResult<FlowNodeVO> page(FlowNodeQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<FlowNodeEntity> list = flowNodeMapper.getList(query);
        PageInfo<FlowNodeEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(FlowNodeConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(FlowNodeVO vo) {
        FlowNodeEntity entity = FlowNodeConvert.INSTANCE.convert(vo);
        flowNodeMapper.save(entity);
    }

    @Override
    public void update(FlowNodeVO vo) {
        FlowNodeEntity entity = FlowNodeConvert.INSTANCE.convert(vo);
        flowNodeMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            FlowNodeEntity param = new FlowNodeEntity();
            param.setId(id);
            param.setDbStatus(0);
            flowNodeMapper.updateById(param);
        });
    }

    @Override
    public FlowNodeEntity getById(Long id) {
        return flowNodeMapper.getById(id);
    }

}