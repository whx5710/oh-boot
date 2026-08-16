package com.finn.app.service;

import com.finn.common.entity.HashDto;
import com.finn.common.entity.MsgEntity;
import com.finn.common.entity.PageResult;
import com.finn.common.entity.Result;
import com.finn.app.query.DataMsgQuery;
import com.finn.app.vo.DataMsgVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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
    Result<?> submit(HashDto params, MsgEntity msgEntity);
}
