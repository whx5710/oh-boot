package com.iris.api.service;

import com.iris.api.entity.DataMsgEntity;
import com.iris.api.query.DataMsgQuery;
import com.iris.api.vo.DataMsgVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;

import java.util.List;

public interface DataMsgService extends BaseService<DataMsgEntity> {

    PageResult<DataMsgVO> page(DataMsgQuery query);

    void delete(List<Long> idList);

    void deleteByDate(String date);
}
