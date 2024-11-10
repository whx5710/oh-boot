package com.iris.team.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.constant.Constant;
import com.iris.core.utils.PageResult;
import com.iris.framework.datasource.service.impl.BaseServiceImpl;
import com.iris.team.convert.OhTaskConvert;
import com.iris.team.entity.OhTaskEntity;
import com.iris.team.mapper.OhTaskMapper;
import com.iris.team.query.OhTaskQuery;
import com.iris.team.service.OhTaskService;
import com.iris.team.vo.OhTaskVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@Service
public class OhTaskServiceImpl extends BaseServiceImpl implements OhTaskService {

    private final OhTaskMapper ohTaskMapper;

    public OhTaskServiceImpl(OhTaskMapper ohTaskMapper){
        this.ohTaskMapper = ohTaskMapper;
    }

    @Override
    public PageResult<OhTaskVO> page(OhTaskQuery query) {
        // 查询参数
        Map<String, Object> params = getParams(query);
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<OhTaskEntity> list = ohTaskMapper.getList(params);
        PageInfo<OhTaskEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(OhTaskConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    private Map<String, Object> getParams(OhTaskQuery query) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", query.getStatus());
        params.put("projectId", query.getProjectId());
        params.put("keyWord", query.getKeyWord());
        params.put("taskType", query.getTaskType());
        // 数据权限
        params.put(Constant.DATA_SCOPE, getDataScopeFilter("t1", null));
        return params;
    }

    @Override
    public void save(OhTaskVO vo) {
        OhTaskEntity entity = OhTaskConvert.INSTANCE.convert(vo);
        ohTaskMapper.save(entity);
    }

    @Override
    public void update(OhTaskVO vo) {
        OhTaskEntity entity = OhTaskConvert.INSTANCE.convert(vo);

        ohTaskMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            OhTaskEntity param = new OhTaskEntity();
            param.setId(id);
            param.setDbStatus(0);
            ohTaskMapper.updateById(param);
        });
    }

    @Override
    public OhTaskEntity getById(Long id) {
        return ohTaskMapper.getById(id);
    }

}