package com.iris.system.pim.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.sms.config.SmsConfig;
import com.iris.system.pim.entity.SmsPlatformEntity;
import com.iris.system.pim.query.SmsPlatformQuery;
import com.iris.system.pim.vo.SmsPlatformVO;

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