package com.finn.sys.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.sys.convert.AttachmentConvert;
import com.finn.sys.entity.AttachmentEntity;
import com.finn.sys.mapper.AttachmentMapper;
import com.finn.sys.query.AttachmentQuery;
import com.finn.sys.service.AttachmentService;
import com.finn.sys.vo.AttachmentVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentMapper attachmentMapper;
    public AttachmentServiceImpl(AttachmentMapper attachmentMapper) {
        this.attachmentMapper = attachmentMapper;
    }

    @Override
    public PageResult<AttachmentVO> page(AttachmentQuery query) {
        List<AttachmentEntity> list = attachmentMapper.getList(query);
        return new PageResult<>(AttachmentConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public Long save(AttachmentVO vo) {
        AttachmentEntity entity = AttachmentConvert.INSTANCE.convert(vo);

        attachmentMapper.insertAttach(entity);
        return entity.getId();
    }

    @Override
    public void update(AttachmentVO vo) {
        AttachmentEntity entity = AttachmentConvert.INSTANCE.convert(vo);
        attachmentMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            AttachmentEntity param = new AttachmentEntity();
            param.setId(id);
            param.setDbStatus(0);
            attachmentMapper.updateById(param);
        });
    }

}