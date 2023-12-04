package com.iris.api.service;

import com.iris.api.entity.DataMsgEntity;
import com.iris.framework.mybatis.service.BaseService;

public interface DataMsgService extends BaseService<DataMsgEntity> {

    // 保存或更新数据
    DataMsgEntity saveData(DataMsgEntity params);

}
