package com.finn.flow.service.impl;

import cn.hutool.core.util.EscapeUtil;
import com.github.pagehelper.Page;
import com.finn.flow.convert.FlowConvert;
import com.finn.flow.entity.FlowEntity;
import com.finn.flow.mapper.FlowMapper;
import com.finn.flow.query.FlowQuery;
import com.finn.flow.service.FlowService;
import com.finn.flow.vo.FlowVO;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自定义流程表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-12-19
 */
@Service
public class FlowServiceImpl implements FlowService {

    private final FlowMapper flowMapper;

    public FlowServiceImpl(FlowMapper flowMapper){
        this.flowMapper = flowMapper;
    }

    @Override
    public PageResult<FlowVO> page(FlowQuery query) {
        Page<FlowEntity> page = flowMapper.getList(query);
        return new PageResult<>(FlowConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }

    /**
     * 保存或更新
     * @param vo
     */
    @Override
    public void save(FlowVO vo) {
        FlowEntity entity = FlowConvert.INSTANCE.convert(vo);
        String keyCode = entity.getKeyCode();
        FlowEntity flowEntity = getByKey(keyCode);
        // 反转义
        String xml = EscapeUtil.unescapeXml(entity.getXml());
        entity.setXml(xml);
        if(entity.getSvgStr() != null){
            entity.setSvgStr(EscapeUtil.unescapeXml(entity.getSvgStr()));
        }
        if(flowEntity == null){
            flowMapper.save(entity);
        }else{
            entity.setId(flowEntity.getId());
            entity.setDbStatus(1);
            flowMapper.updateById(entity);
        }
    }

    @Override
    public void update(FlowVO vo) {
        FlowEntity entity = FlowConvert.INSTANCE.convert(vo);
        flowMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            FlowEntity flowEntity = new FlowEntity();
            flowEntity.setId(id);
            flowEntity.setDbStatus(0);
            flowMapper.updateById(flowEntity);
        });
    }

    /**
     * 根据流程key获取自定义的流程信息
     * @param key
     * @return
     */
    @Override
    public FlowEntity getByKey(String key) {
        AssertUtils.isBlank(key, "流程key");
        List<FlowEntity> list = flowMapper.getByKey(key);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list.getFirst();
    }

    @Override
    public FlowEntity getById(Long id) {
        return flowMapper.getById(id);
    }

}