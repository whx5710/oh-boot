package com.finn.flow.service.impl;

import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.Wrapper;
import com.github.pagehelper.Page;
import com.finn.flow.convert.FlowConvert;
import com.finn.flow.entity.FlowEntity;
import com.finn.flow.mapper.FlowMapper;
import com.finn.flow.query.FlowQuery;
import com.finn.flow.service.FlowService;
import com.finn.flow.vo.FlowVO;
import com.finn.framework.utils.AssertUtils;
import com.finn.framework.entity.PageResult;
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
        Wrapper<FlowEntity> queryWrapper = QueryWrapper.of(FlowEntity.class).eq(FlowEntity::getDbStatus, 1)
                .eq(FlowEntity::getKeyCode, query.getKeyCode()).like(FlowEntity::getName, query.getName());
        if(query.getKeyWord() != null && !query.getKeyWord().isEmpty()){
            queryWrapper.jointSQL("(name like concat('%', #{keyWord}, '%') or key_code like concat('%', #{keyWord}, '%'))", "keyWord", query.getKeyWord());
        }
        queryWrapper.page(query.getPageNum(), query.getPageSize());
        try (Page<FlowEntity> page = flowMapper.selectPageByWrapper(queryWrapper)) {
            return new PageResult<>(FlowConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
        }
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
        String xml = unescapeXml(entity.getXml());
        entity.setXml(xml);
        if(entity.getSvgStr() != null){
            entity.setSvgStr(unescapeXml(entity.getSvgStr()));
        }
        if(flowEntity == null){
            flowMapper.insert(entity);
        }else{
            entity.setId(flowEntity.getId());
            entity.setDbStatus(1);
            flowMapper.updateById(entity);
        }
    }

    @Override
    public void update(FlowVO vo) {
        AssertUtils.isNull(vo.getId(), "ID不能为空");
        if(vo.getXml() != null){
            String xml = unescapeXml(vo.getXml());
            vo.setXml(xml);
        }
        if(vo.getSvgStr() != null){
            String svg = unescapeXml(vo.getSvgStr());
            vo.setSvgStr(svg);
        }
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

    /**
     * xml 去除转义
     * @param xml
     * @return
     */
    public static String unescapeXml(String xml) {
        if (xml == null) {
            return null;
        }
        return xml.replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&apos;", "'");
    }

}