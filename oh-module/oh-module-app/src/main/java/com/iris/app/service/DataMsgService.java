package com.iris.app.service;

import com.iris.core.utils.PageResult;
import com.iris.core.utils.Result;
import com.iris.framework.entity.api.MsgEntity;
import com.iris.app.query.DataMsgQuery;
import com.iris.app.vo.DataMsgVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * mq日志
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-04-21
 */
public interface DataMsgService {

    PageResult<DataMsgVO> page(DataMsgQuery query);

    void delete(List<Long> idList);

    void deleteByDate(String date);

    // 保存报文
    void saveMsgLog();
    /**
     * 校验接口基本信息
     * @param request
     * @return
     */
    MsgEntity basicCheck(HttpServletRequest request);

    /**
     * 调用业务
     * @param params
     * @return
     */
    Result<?> submit(Map<String, Object> params, MsgEntity msgEntity);
}
