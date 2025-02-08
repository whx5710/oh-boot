package com.finn.sys.base.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.convert.SysAttachmentConvert;
import com.finn.sys.base.entity.SysAttachmentEntity;
import com.finn.sys.base.mapper.SysAttachmentMapper;
import com.finn.sys.base.query.SysAttachmentQuery;
import com.finn.sys.base.service.SysAttachmentService;
import com.finn.sys.base.vo.SysAttachmentVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysAttachmentServiceImpl implements SysAttachmentService {

    private final SysAttachmentMapper sysAttachmentMapper;
    public SysAttachmentServiceImpl(SysAttachmentMapper sysAttachmentMapper) {
        this.sysAttachmentMapper = sysAttachmentMapper;
    }

    @Override
    public PageResult<SysAttachmentVO> page(SysAttachmentQuery query) {
        List<SysAttachmentEntity> list = sysAttachmentMapper.getList(query);
        return new PageResult<>(SysAttachmentConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public void save(SysAttachmentVO vo) {
        SysAttachmentEntity entity = SysAttachmentConvert.INSTANCE.convert(vo);

        sysAttachmentMapper.insertAttach(entity);
    }

    @Override
    public void update(SysAttachmentVO vo) {
        SysAttachmentEntity entity = SysAttachmentConvert.INSTANCE.convert(vo);
        sysAttachmentMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            SysAttachmentEntity param = new SysAttachmentEntity();
            param.setId(id);
            param.setDbStatus(0);
            sysAttachmentMapper.updateById(param);
        });
    }

}