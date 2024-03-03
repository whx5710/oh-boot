package com.iris.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.iris.api.dao.DataMessageDao;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.service.DataMsgService;
import com.iris.framework.common.entity.MetaEntity;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DataMsgServiceImpl extends BaseServiceImpl<DataMessageDao, DataMsgEntity> implements DataMsgService {

    private final Logger log = LoggerFactory.getLogger(DataMsgServiceImpl.class);

    @Override
    public void saveMsg(MetaEntity data) {
        DataMsgEntity dataMsg = new DataMsgEntity();
        BeanUtil.copyProperties(data, dataMsg);
        dataMsg.setJsonStr(JsonUtils.toJsonString(data.getData()));
        this.save(dataMsg);
    }
}
