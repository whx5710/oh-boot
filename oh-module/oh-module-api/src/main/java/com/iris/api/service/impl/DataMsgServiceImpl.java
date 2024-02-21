package com.iris.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.iris.api.dao.DataMessageDao;
import com.iris.api.entity.DataMsgEntity;
import com.iris.api.service.DataMsgService;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DataMsgServiceImpl extends BaseServiceImpl<DataMessageDao, DataMsgEntity> implements DataMsgService {

    private final Logger log = LoggerFactory.getLogger(DataMsgServiceImpl.class);

    @Override
    public DataMsgEntity saveData(DataMsgEntity params) {
        params.setJsonStr(JSON.toJSONString(params.getJsonObj(),0));
        this.save(params);
        return params;
    }
}
