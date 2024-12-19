package com.iris.sys.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.utils.PageResult;
import com.iris.sys.base.convert.SysAttachmentConvert;
import com.iris.sys.base.entity.SysAttachmentEntity;
import com.iris.sys.base.mapper.SysAttachmentMapper;
import com.iris.sys.base.query.SysAttachmentQuery;
import com.iris.sys.base.service.SysAttachmentService;
import com.iris.sys.base.vo.SysAttachmentVO;
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
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysAttachmentEntity> list = sysAttachmentMapper.getList(query);
        PageInfo<SysAttachmentEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysAttachmentConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
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