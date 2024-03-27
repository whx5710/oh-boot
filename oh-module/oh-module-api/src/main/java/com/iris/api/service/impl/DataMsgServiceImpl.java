package com.iris.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.api.convert.DataMsgConvert;
import com.iris.api.dao.DataMessageDao;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.query.DataMsgQuery;
import com.iris.api.service.DataMsgService;
import com.iris.api.vo.DataMsgVO;
import com.iris.framework.common.utils.DateUtils;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
public class DataMsgServiceImpl extends BaseServiceImpl<DataMessageDao, DataMsgEntity> implements DataMsgService {

    private final Logger log = LoggerFactory.getLogger(DataMsgServiceImpl.class);

    /**
     * 分页查询
     * @param query
     * @return
     */
    @Override
    public PageResult<DataMsgVO> page(DataMsgQuery query) {
        LambdaQueryWrapper<DataMsgEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataMsgEntity::getDbStatus, 1);
        if(!ObjectUtils.isEmpty(query.getClientId())){
            wrapper.eq(DataMsgEntity::getClientId, query.getClientId()); // 客户端ID
        }
        if(!ObjectUtils.isEmpty(query.getState())){
            wrapper.eq(DataMsgEntity::getState, query.getState()); // 状态0未处理1处理2未找到对应的服务类3业务处理失败
        }
        if(!ObjectUtils.isEmpty(query.getFunCode())){
            wrapper.like(DataMsgEntity::getFunCode, query.getFunCode()); // 功能号
        }
        if(!ObjectUtils.isEmpty(query.getTopic())){
            wrapper.like(DataMsgEntity::getTopic, query.getTopic()); // topic
        }
        // 搜索功能号、客户端
        if(!ObjectUtils.isEmpty(query.getKeyWord())){
            wrapper.and(w -> w.like(DataMsgEntity::getFunCode, query.getKeyWord())
                    .or().like(DataMsgEntity::getClientId, query.getKeyWord()));
        }
        wrapper.orderByDesc(DataMsgEntity::getCreateTime);
        IPage<DataMsgEntity> page = baseMapper.selectPage(getPage(query), wrapper);
        return new PageResult<>(DataMsgConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 批量删除
     * @param idList
     */
    @Override
    public void delete(List<Long> idList) {
        this.removeByIds(idList);
    }

    /**
     * 删除日期之前的数据
     * @param date
     */
    @Override
    public void deleteByDate(String date) {
        LambdaUpdateWrapper<DataMsgEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.le(DataMsgEntity::getCreateTime, DateUtils.parseLocalDateTime(date))
                .eq(DataMsgEntity::getDbStatus, 1);
        DataMsgEntity dataMsgEntity = new DataMsgEntity();
        dataMsgEntity.setDbStatus(0); // 删除标志
        this.baseMapper.update(dataMsgEntity, updateWrapper);
    }

}
