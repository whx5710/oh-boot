package com.iris.system.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.sms.config.SmsConfig;
import com.iris.system.entity.SmsPlatformEntity;
import com.iris.system.query.SmsPlatformQuery;
import com.iris.system.vo.SmsPlatformVO;

import java.util.List;

/**
 * 短信平台
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SmsPlatformService extends BaseService<SmsPlatformEntity> {

    PageResult<SmsPlatformVO> page(SmsPlatformQuery query);

    /**
     * 启用的短信平台列表
     */
    List<SmsConfig> listByEnable();

    void save(SmsPlatformVO vo);

    void update(SmsPlatformVO vo);

    void delete(List<Long> idList);

}