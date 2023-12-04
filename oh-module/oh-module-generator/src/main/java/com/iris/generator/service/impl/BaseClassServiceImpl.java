package com.iris.generator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.generator.common.page.PageResult;
import com.iris.generator.common.query.Query;
import com.iris.generator.common.service.impl.BaseServiceImpl;
import com.iris.generator.dao.BaseClassDao;
import com.iris.generator.entity.BaseClassEntity;
import com.iris.generator.service.BaseClassService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 基类管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class BaseClassServiceImpl extends BaseServiceImpl<BaseClassDao, BaseClassEntity> implements BaseClassService {

    @Override
    public PageResult<BaseClassEntity> page(Query query) {
        IPage<BaseClassEntity> page = baseMapper.selectPage(
                getPage(query), getWrapper(query)
        );

        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public List<BaseClassEntity> getList() {
        return baseMapper.selectList(null);
    }

    @Override
    public boolean save(BaseClassEntity entity) {
        entity.setCreateTime(LocalDateTime.now());
        return super.save(entity);
    }
}