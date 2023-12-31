package com.iris.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.system.convert.SysVersionInfoConvert;
import com.iris.system.entity.SysVersionInfoEntity;
import com.iris.system.query.SysVersionInfoQuery;
import com.iris.system.vo.SysVersionInfoVO;
import com.iris.system.dao.SysVersionInfoDao;
import com.iris.system.service.SysVersionInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
@Service
public class SysVersionInfoServiceImpl extends BaseServiceImpl<SysVersionInfoDao, SysVersionInfoEntity> implements SysVersionInfoService {

    @Override
    public PageResult<SysVersionInfoVO> page(SysVersionInfoQuery query) {
        IPage<SysVersionInfoEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysVersionInfoConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysVersionInfoEntity> getWrapper(SysVersionInfoQuery query){
        LambdaQueryWrapper<SysVersionInfoEntity> wrapper = Wrappers.lambdaQuery();
        if(!ObjectUtils.isEmpty(query.getKeyWord())){
            wrapper.and(w -> w.like(SysVersionInfoEntity::getContent, query.getKeyWord())
                    .or().like(SysVersionInfoEntity::getVersionNum, query.getKeyWord()));
        }
        wrapper.orderByDesc(SysVersionInfoEntity::getReleaseTime);
        return wrapper;
    }

    @Override
    public void save(SysVersionInfoVO vo) {
        SysVersionInfoEntity entity = SysVersionInfoConvert.INSTANCE.convert(vo);
        entity.setIsCurrVersion(true);
        baseMapper.insert(entity);
        // 修改当前版本标记
        UpdateWrapper<SysVersionInfoEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().ne(SysVersionInfoEntity::getId, entity.getId()).eq(SysVersionInfoEntity::getIsCurrVersion, true)
                .set(SysVersionInfoEntity::getIsCurrVersion, false);
        baseMapper.update(null, updateWrapper);
    }

    @Override
    public void update(SysVersionInfoVO vo) {
        SysVersionInfoEntity entity = SysVersionInfoConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    /**
     * 获取最新版本信息
     * @return
     */
    @Override
    public SysVersionInfoEntity latestVersion() {
        LambdaQueryWrapper<SysVersionInfoEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysVersionInfoEntity::getIsCurrVersion, true).eq(SysVersionInfoEntity::getDeleted, 0);
        return baseMapper.selectOne(wrapper);
    }

}