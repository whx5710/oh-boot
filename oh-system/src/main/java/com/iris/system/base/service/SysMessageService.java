package com.iris.system.base.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.entity.SysMessageEntity;
import com.iris.system.base.query.SysMessageQuery;
import com.iris.system.base.vo.SysMessageVO;

import java.util.List;

/**
 * 系统消息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-10-10
 */
public interface SysMessageService{

    PageResult<SysMessageVO> page(SysMessageQuery query);

    void save(SysMessageVO vo);

    void update(SysMessageVO vo);

    void delete(List<Long> idList);

    List<SysMessageVO> unSendMsg(Long userId);

    List<SysMessageVO> unReadMsg(Long userId);

    SysMessageEntity getById(Long id);

    boolean updateById(SysMessageEntity param);
}