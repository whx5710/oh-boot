package com.iris.framework.common.service;

import cn.hutool.json.JSONObject;

/**
 * 任务接口
 *
 * @author 王小费 whx5710@qq.com
 */
public interface JobService {

    // 校验参数
    void check(JSONObject data);
    // 业务处理
    JSONObject handle(JSONObject data);
}
