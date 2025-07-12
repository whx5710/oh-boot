package com.finn.sys.base.service;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.entity.SmsPlatformEntity;
import com.finn.sys.base.query.SmsPlatformQuery;
import com.finn.sys.base.vo.SmsPlatformVO;
import com.finn.sys.sms.config.SmsConfig;

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