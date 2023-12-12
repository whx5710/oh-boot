package com.iris.system.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;
import com.iris.system.entity.SysMessageEntity;
import com.iris.system.query.SysMessageQuery;
import com.iris.system.vo.SysMessageVO;

import java.util.List;

/**
 * 系统消息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-10-10
 */
public interface SysMessageService extends BaseService<SysMessageEntity> {

    PageResult<SysMessageVO> page(SysMessageQuery query);

    void save(SysMessageVO vo);

    void update(SysMessageVO vo);

    void delete(List<Long> idList);

    List<SysMessageVO> unSendMsg(Long userId);
    List<SysMessageVO> unReadMsg(Long userId);
}