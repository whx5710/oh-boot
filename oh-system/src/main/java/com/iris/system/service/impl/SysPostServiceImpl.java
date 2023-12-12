package com.iris.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iris.system.convert.SysPostConvert;
import com.iris.system.service.SysUserPostService;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.system.dao.SysPostDao;
import com.iris.system.entity.SysPostEntity;
import com.iris.system.service.SysPostService;
import com.iris.system.query.SysPostQuery;
import com.iris.system.vo.SysPostVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPostDao, SysPostEntity> implements SysPostService {
    private final SysUserPostService sysUserPostService;

    public SysPostServiceImpl(SysUserPostService sysUserPostService) {
        this.sysUserPostService = sysUserPostService;
    }

    @Override
    public PageResult<SysPostVO> page(SysPostQuery query) {
        IPage<SysPostEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysPostConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    @Override
    public List<SysPostVO> getList() {
        SysPostQuery query = new SysPostQuery();
        //正常岗位列表
        query.setStatus(1);
        List<SysPostEntity> entityList = baseMapper.selectList(getWrapper(query));

        return SysPostConvert.INSTANCE.convertList(entityList);
    }

    private Wrapper<SysPostEntity> getWrapper(SysPostQuery query){
        LambdaQueryWrapper<SysPostEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(query.getPostCode()), SysPostEntity::getPostCode, query.getPostCode());
        wrapper.like(StrUtil.isNotBlank(query.getPostName()), SysPostEntity::getPostName, query.getPostName());
        wrapper.eq(query.getStatus() != null, SysPostEntity::getStatus, query.getStatus());
        wrapper.orderByAsc(SysPostEntity::getSort);

        return wrapper;
    }

    @Override
    public void save(SysPostVO vo) {
        SysPostEntity entity = SysPostConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(SysPostVO vo) {
        SysPostEntity entity = SysPostConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 删除岗位
        removeByIds(idList);

        // 删除岗位用户关系
        sysUserPostService.deleteByPostIdList(idList);
    }

}