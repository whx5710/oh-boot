package com.iris.system.app.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.app.entity.DataMsgEntity;
import com.iris.system.app.query.DataMsgQuery;
import com.iris.system.app.vo.DataMsgVO;

import java.util.List;

/**
 * mq日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-04-21
 */
public interface DataMsgService extends BaseService<DataMsgEntity> {

    PageResult<DataMsgVO> page(DataMsgQuery query);

    void delete(List<Long> idList);

    void deleteByDate(String date);

    // 保存报文
    void saveMsgLog();
}
