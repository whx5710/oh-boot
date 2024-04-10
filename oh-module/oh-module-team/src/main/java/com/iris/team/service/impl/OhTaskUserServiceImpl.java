package com.iris.team.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.team.convert.OhTaskUserConvert;
import com.iris.team.dao.OhTaskUserDao;
import com.iris.team.entity.OhTaskUserEntity;
import com.iris.team.query.OhTaskUserQuery;
import com.iris.team.service.OhTaskUserService;
import com.iris.team.vo.OhTaskUserVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务人员表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@Service
public class OhTaskUserServiceImpl extends BaseServiceImpl<OhTaskUserDao, OhTaskUserEntity> implements OhTaskUserService {

    @Override
    public PageResult<OhTaskUserVO> page(OhTaskUserQuery query) {
        IPage<OhTaskUserEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(OhTaskUserConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<OhTaskUserEntity> getWrapper(OhTaskUserQuery query){
        LambdaQueryWrapper<OhTaskUserEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(OhTaskUserVO vo) {
        OhTaskUserEntity entity = OhTaskUserConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(OhTaskUserVO vo) {
        OhTaskUserEntity entity = OhTaskUserConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}