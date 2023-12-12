package com.iris.team.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.team.convert.OhProjectLogConvert;
import com.iris.team.dao.OhProjectLogDao;
import com.iris.team.entity.OhProjectLogEntity;
import com.iris.team.query.OhProjectLogQuery;
import com.iris.team.service.OhProjectLogService;
import com.iris.team.vo.OhProjectLogVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
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
public class OhProjectLogServiceImpl extends BaseServiceImpl<OhProjectLogDao, OhProjectLogEntity> implements OhProjectLogService {
    @Override
    public PageResult<OhProjectLogVO> page(OhProjectLogQuery query) {
        IPage<OhProjectLogEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(OhProjectLogConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<OhProjectLogEntity> getWrapper(OhProjectLogQuery query){
        LambdaQueryWrapper<OhProjectLogEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(OhProjectLogVO vo) {
        OhProjectLogEntity entity = OhProjectLogConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(OhProjectLogVO vo) {
        OhProjectLogEntity entity = OhProjectLogConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}