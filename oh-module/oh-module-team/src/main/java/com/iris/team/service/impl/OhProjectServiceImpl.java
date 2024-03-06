package com.iris.team.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.team.convert.OhProjectConvert;
import com.iris.team.dao.OhProjectDao;
import com.iris.team.entity.OhProjectEntity;
import com.iris.team.query.OhProjectQuery;
import com.iris.team.service.OhProjectService;
import com.iris.team.vo.OhProjectVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 项目信息表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@Service
public class OhProjectServiceImpl extends BaseServiceImpl<OhProjectDao, OhProjectEntity> implements OhProjectService {

    @Override
    public PageResult<OhProjectVO> page(OhProjectQuery query) {
        IPage<OhProjectEntity> page = this.baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(OhProjectConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<OhProjectEntity> getWrapper(OhProjectQuery query){
        LambdaQueryWrapper<OhProjectEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OhProjectEntity::getDbStatus, 1);

        // 状态（1开始2暂停3关闭）
        Integer status = query.getStatus();
        if(status != null && status > 0){
            wrapper.eq(OhProjectEntity::getStatus, status);
        }
        // 时间范围
        if(!ObjectUtils.isEmpty(query.getStartTime())){
            wrapper.ge(OhProjectEntity::getStartTime , query.getStartTime());
        }
        if(!ObjectUtils.isEmpty(query.getEndTime())){
            wrapper.le(OhProjectEntity::getStartTime, query.getEndTime());
        }
        String keyWord = query.getKeyWord();
        // 模糊查询
        if(!ObjectUtils.isEmpty(keyWord)){
            wrapper.and(w -> w.like(OhProjectEntity::getProjectName, keyWord)
                    .or().like(OhProjectEntity::getProjectAlias, keyWord)
                    .or().like(OhProjectEntity::getProjectCode, keyWord)
                    .or().like(OhProjectEntity::getDirectorName, keyWord));
        }
        return wrapper;
    }

    @Override
    public void save(OhProjectVO vo) {
        OhProjectEntity entity = OhProjectConvert.INSTANCE.convert(vo);

        this.baseMapper.insert(entity);
    }

    @Override
    public void update(OhProjectVO vo) {
        OhProjectEntity entity = OhProjectConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}