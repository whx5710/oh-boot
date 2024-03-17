package com.iris.flow.service.impl;

import cn.hutool.core.util.EscapeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.flow.convert.FlowConvert;
import com.iris.flow.dao.FlowDao;
import com.iris.flow.entity.FlowEntity;
import com.iris.flow.query.FlowQuery;
import com.iris.flow.service.FlowService;
import com.iris.flow.vo.FlowVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
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
public class FlowServiceImpl extends BaseServiceImpl<FlowDao, FlowEntity> implements FlowService {

    @Override
    public PageResult<FlowVO> page(FlowQuery query) {
        IPage<FlowEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(FlowConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<FlowEntity> getWrapper(FlowQuery query){
        LambdaQueryWrapper<FlowEntity> wrapper = Wrappers.lambdaQuery();
        if(query.getName() != null && !query.getName().equals("")){
            wrapper.like(FlowEntity::getName, query.getName()).or().like(FlowEntity::getKeyCode, query.getName());
        }
        return wrapper;
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
            baseMapper.insert(entity);
        }else{
            entity.setId(flowEntity.getId());
            entity.setDbStatus(1);
            baseMapper.updateById(entity);
        }
    }

    @Override
    public void update(FlowVO vo) {
        FlowEntity entity = FlowConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    /**
     * 根据流程key获取自定义的流程信息
     * @param key
     * @return
     */
    @Override
    public FlowEntity getByKey(String key) {
        AssertUtils.isBlank(key, "流程key");
        LambdaQueryWrapper<FlowEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FlowEntity::getKeyCode, key);
        List<FlowEntity> list = this.list(wrapper);
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }

}