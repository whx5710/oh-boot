package com.iris.api.service;

import com.iris.api.entity.DataMsgEntity;
import com.iris.api.query.DataAppQuery;
import com.iris.api.vo.DataMsgVO;
import com.iris.framework.common.entity.MetaEntity;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;

public interface DataMsgService extends BaseService<DataMsgEntity> {

    void saveMsg(MetaEntity data);

    PageResult<DataMsgVO> page(DataAppQuery query);

}
