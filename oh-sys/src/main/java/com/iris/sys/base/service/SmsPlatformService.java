package com.iris.sys.base.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.sys.base.entity.SmsPlatformEntity;
import com.iris.sys.base.query.SmsPlatformQuery;
import com.iris.sys.base.vo.SmsPlatformVO;
import com.iris.sys.sms.config.SmsConfig;

import java.util.List;

/**
 * 短信平台
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SmsPlatformService {

    PageResult<SmsPlatformVO> page(SmsPlatformQuery query);

    /**
     * 启用的短信平台列表
     */
    List<SmsConfig> listByEnable();

    void save(SmsPlatformVO vo);

    void update(SmsPlatformVO vo);

    void delete(List<Long> idList);

    SmsPlatformEntity getById(Long id);
}