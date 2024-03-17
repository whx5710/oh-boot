package com.iris.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.flow.convert.FlowNodeConvert;
import com.iris.flow.dao.FlowNodeDao;
import com.iris.flow.entity.FlowNodeEntity;
import com.iris.flow.query.FlowNodeQuery;
import com.iris.flow.service.FlowNodeService;
import com.iris.flow.vo.FlowNodeVO;
import com.iris.framework.common.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 环节定义表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-01-31
 */
@Service
public class FlowNodeServiceImpl extends BaseServiceImpl<FlowNodeDao, FlowNodeEntity> implements FlowNodeService {

    @Override
    public PageResult<FlowNodeVO> page(FlowNodeQuery query) {
        IPage<FlowNodeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<>(FlowNodeConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 处理参数
     * @param query
     * @return
     */
    private LambdaQueryWrapper<FlowNodeEntity> getWrapper(FlowNodeQuery query){
        LambdaQueryWrapper<FlowNodeEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FlowNodeEntity::getDbStatus, 1);
        if(!ObjectUtils.isEmpty(query.getProcDefId())){
            wrapper.eq(FlowNodeEntity::getProcDefId, query.getProcDefId());
        }
        if(!ObjectUtils.isEmpty(query.getActDefId())){
            wrapper.eq(FlowNodeEntity::getActDefId, query.getActDefId());
        }
        return wrapper;
    }

    @Override
    public void save(FlowNodeVO vo) {
        FlowNodeEntity entity = FlowNodeConvert.INSTANCE.convert(vo);
        baseMapper.insert(entity);
    }

    @Override
    public void update(FlowNodeVO vo) {
        FlowNodeEntity entity = FlowNodeConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}