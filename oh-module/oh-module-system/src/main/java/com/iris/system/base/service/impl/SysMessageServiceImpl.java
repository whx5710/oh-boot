package com.iris.system.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.impl.BaseServiceImpl;
import com.iris.framework.security.user.SecurityUser;
import com.iris.system.base.dao.SysMessageDao;
import com.iris.system.base.query.SysMessageQuery;
import com.iris.system.base.vo.SysMessageVO;
import com.iris.system.base.convert.SysMessageConvert;
import com.iris.system.base.entity.SysMessageEntity;
import com.iris.system.base.service.SysMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 系统消息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-10-10
 */
@Service
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessageDao, SysMessageEntity> implements SysMessageService {

    @Override
    public PageResult<SysMessageVO> page(SysMessageQuery query) {
        IPage<SysMessageEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysMessageConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysMessageEntity> getWrapper(SysMessageQuery query){
        LambdaQueryWrapper<SysMessageEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMessageEntity::getDbStatus, 1);
        if(!ObjectUtils.isEmpty(query.getTitle())){
            wrapper.like(SysMessageEntity::getTitle, query.getTitle());
        }
        if(!ObjectUtils.isEmpty(query.getContent())){
            wrapper.like(SysMessageEntity::getContent, query.getContent());
        }
        if(!ObjectUtils.isEmpty(query.getState())){
            wrapper.eq(SysMessageEntity::getState, query.getState());
        }
        if(!ObjectUtils.isEmpty(query.getToId()) && query.getToId() > 0){
            wrapper.eq(SysMessageEntity::getToId, query.getToId());
        }
        if(!ObjectUtils.isEmpty(query.getFromId()) && query.getFromId() > 0){
            wrapper.eq(SysMessageEntity::getFromId, query.getFromId());
        }
        if(!ObjectUtils.isEmpty(query.getNoState())){
            wrapper.ne(SysMessageEntity::getState, query.getNoState());
        }
        return wrapper;
    }

    @Override
    public void save(SysMessageVO vo) {
        SysMessageEntity entity = SysMessageConvert.INSTANCE.convert(vo);
        if(ObjectUtils.isEmpty(entity.getFromId())){
            entity.setFromId(SecurityUser.getUserId());
            entity.setFromName(SecurityUser.getUser().getRealName());
        }
        entity.setType("success");
        baseMapper.insert(entity);
    }

    @Override
    public void update(SysMessageVO vo) {
        SysMessageEntity entity = SysMessageConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<SysMessageVO> unSendMsg(Long userId) {
        LambdaQueryWrapper<SysMessageEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMessageEntity::getToId, userId).eq(SysMessageEntity::getState, "0").orderByDesc(SysMessageEntity::getCreateTime);
        wrapper.last(" limit 10");
        return SysMessageConvert.INSTANCE.convertList(this.baseMapper.selectList(wrapper));
    }

    /**
     * 获取未读消息
     * @param userId
     * @return
     */
    @Override
    public List<SysMessageVO> unReadMsg(Long userId) {
        LambdaQueryWrapper<SysMessageEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMessageEntity::getToId, userId).eq(SysMessageEntity::getState, "1").orderByDesc(SysMessageEntity::getCreateTime);
        wrapper.last(" limit 10");
        return SysMessageConvert.INSTANCE.convertList(this.baseMapper.selectList(wrapper));
    }

}