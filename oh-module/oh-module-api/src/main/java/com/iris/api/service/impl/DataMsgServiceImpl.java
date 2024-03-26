package com.iris.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.api.convert.DataMsgConvert;
import com.iris.api.dao.DataMessageDao;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.query.DataAppQuery;
import com.iris.api.service.DataMsgService;
import com.iris.api.vo.DataMsgVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class DataMsgServiceImpl extends BaseServiceImpl<DataMessageDao, DataMsgEntity> implements DataMsgService {

    private final Logger log = LoggerFactory.getLogger(DataMsgServiceImpl.class);

    @Override
    public PageResult<DataMsgVO> page(DataAppQuery query) {
        LambdaQueryWrapper<DataMsgEntity> wrapper = Wrappers.lambdaQuery();
        if(!ObjectUtils.isEmpty(query.getKeyWord())){
            wrapper.and(w -> w.like(DataMsgEntity::getFunCode, query.getKeyWord())
                    .or().like(DataMsgEntity::getClientId, query.getKeyWord()));
        }
        wrapper.orderByDesc(DataMsgEntity::getCreateTime);
        IPage<DataMsgEntity> page = baseMapper.selectPage(getPage(query), wrapper);
        return new PageResult<>(DataMsgConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }
}
