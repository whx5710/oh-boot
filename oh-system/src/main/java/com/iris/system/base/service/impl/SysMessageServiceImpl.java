package com.iris.system.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.PageResult;
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
public class SysMessageServiceImpl implements SysMessageService {

    private final SysMessageDao sysMessageDao;

    public SysMessageServiceImpl(SysMessageDao sysMessageDao){
        this.sysMessageDao = sysMessageDao;
    }

    @Override
    public PageResult<SysMessageVO> page(SysMessageQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysMessageEntity> list = sysMessageDao.getList(query);
        PageInfo<SysMessageEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysMessageConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(SysMessageVO vo) {
        SysMessageEntity entity = SysMessageConvert.INSTANCE.convert(vo);
        if(ObjectUtils.isEmpty(entity.getFromId())){
            entity.setFromId(SecurityUser.getUserId());
            entity.setFromName(SecurityUser.getUser().getRealName());
        }
        entity.setType("success");
        sysMessageDao.save(entity);
    }

    @Override
    public void update(SysMessageVO vo) {
        SysMessageEntity entity = SysMessageConvert.INSTANCE.convert(vo);
        sysMessageDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            SysMessageEntity param = new SysMessageEntity();
            param.setId(id);
            param.setDbStatus(0);
            sysMessageDao.updateById(param);
        });
    }

    @Override
    public List<SysMessageVO> unSendMsg(Long userId) {
        List<SysMessageEntity> list = sysMessageDao.getUnSendMsg(userId, "0");
        return SysMessageConvert.INSTANCE.convertList(list.subList(0, 10));
    }

    /**
     * 获取未读消息
     * @param userId
     * @return
     */
    @Override
    public List<SysMessageVO> unReadMsg(Long userId) {
        List<SysMessageEntity> list = sysMessageDao.getUnSendMsg(userId, "1");

        return SysMessageConvert.INSTANCE.convertList(list.subList(0, 10));
    }

    @Override
    public SysMessageEntity getById(Long id) {
        return sysMessageDao.getById(id);
    }

    @Override
    public boolean updateById(SysMessageEntity param) {
        return sysMessageDao.updateById(param);
    }

}