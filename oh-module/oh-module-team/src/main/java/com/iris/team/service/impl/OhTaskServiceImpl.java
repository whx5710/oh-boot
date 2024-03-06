package com.iris.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.framework.common.constant.Constant;
import com.iris.team.convert.OhTaskConvert;
import com.iris.team.dao.OhTaskDao;
import com.iris.team.entity.OhTaskEntity;
import com.iris.team.query.OhTaskQuery;
import com.iris.team.service.OhTaskService;
import com.iris.team.vo.OhTaskVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
public class OhTaskServiceImpl extends BaseServiceImpl<OhTaskDao, OhTaskEntity> implements OhTaskService {

    @Override
    public PageResult<OhTaskVO> page(OhTaskQuery query) {
        // 查询参数
        Map<String, Object> params = getParams(query);

        // 分页查询
        IPage<OhTaskEntity> page = getPage(query);
        params.put(Constant.PAGE, page);

        List<OhTaskEntity> list = baseMapper.getList(params);
        return new PageResult<>(OhTaskConvert.INSTANCE.convertList(list), page.getTotal());
    }

    private Map<String, Object> getParams(OhTaskQuery query) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", query.getStatus());
        params.put("projectId", query.getProjectId());
        params.put("keyWord", query.getKeyWord());
        params.put("taskType", query.getTaskType());
        // 数据权限
        params.put(Constant.DATA_SCOPE, getDataScope("t1", null));
        return params;
    }
    private LambdaQueryWrapper<OhTaskEntity> getWrapper(OhTaskQuery query){
        LambdaQueryWrapper<OhTaskEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OhTaskEntity::getDbStatus, 1);
        if(!ObjectUtils.isEmpty(query.getTaskType())){ // 1任务2需求3设计4缺陷9其他
            wrapper.in(OhTaskEntity::getTaskType, query.getTaskType());
        }
        if(!ObjectUtils.isEmpty(query.getStatus())){ // // 状态（1待办项2进行中3已完成）
            wrapper.eq(OhTaskEntity::getStatus, query.getStatus());
        }
        if(!ObjectUtils.isEmpty(query.getProjectId())){ // 所属项目
            wrapper.eq(OhTaskEntity::getProjectId, query.getProjectId());
        }
        if(!ObjectUtils.isEmpty(query.getKeyWord())){ // 搜索
            wrapper.and(w -> w.like(OhTaskEntity::getTaskTitle, query.getKeyWord())
                    .or().like(OhTaskEntity::getRemark, query.getKeyWord())
                    .or().like(OhTaskEntity::getNote, query.getKeyWord())
            );
        }
        return wrapper;
    }

    @Override
    public void save(OhTaskVO vo) {
        OhTaskEntity entity = OhTaskConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(OhTaskVO vo) {
        OhTaskEntity entity = OhTaskConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}