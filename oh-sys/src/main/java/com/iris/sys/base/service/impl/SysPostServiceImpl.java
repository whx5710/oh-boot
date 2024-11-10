package com.iris.sys.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.core.exception.ServerException;
import com.iris.core.utils.AssertUtils;
import com.iris.sys.base.mapper.SysPostMapper;
import com.iris.sys.base.query.SysPostQuery;
import com.iris.sys.base.vo.SysPostVO;
import com.iris.sys.base.convert.SysPostConvert;
import com.iris.sys.base.service.SysUserPostService;
import com.iris.core.utils.PageResult;
import com.iris.sys.base.entity.SysPostEntity;
import com.iris.sys.base.service.SysPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 岗位管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class SysPostServiceImpl implements SysPostService {
    private final SysUserPostService sysUserPostService;
    private final SysPostMapper sysPostMapper;

    public SysPostServiceImpl(SysUserPostService sysUserPostService, SysPostMapper sysPostMapper) {
        this.sysUserPostService = sysUserPostService;
        this.sysPostMapper = sysPostMapper;
    }

    @Override
    public PageResult<SysPostVO> page(SysPostQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysPostEntity> entityList = sysPostMapper.getList(query);
        PageInfo<SysPostEntity> pageInfo = new PageInfo<>(entityList);
        return new PageResult<>(SysPostConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public List<SysPostVO> getList() {
        SysPostQuery query = new SysPostQuery();
        //正常岗位列表
        query.setStatus(1);
        List<SysPostEntity> entityList = sysPostMapper.getList(query);

        return SysPostConvert.INSTANCE.convertList(entityList);
    }

    @Override
    public void save(SysPostVO vo) {
        SysPostEntity entity = SysPostConvert.INSTANCE.convert(vo);
        AssertUtils.isBlank(entity.getPostCode(), "岗位编码");
        SysPostQuery params = new SysPostQuery();
        params.setPostCode(entity.getPostCode());
        List<SysPostEntity> list = sysPostMapper.getList(params);
        if(!ObjectUtils.isEmpty(list)){
            throw new ServerException("岗位编码已存在");
        }
        sysPostMapper.insertPost(entity);
    }

    @Override
    public void update(SysPostVO vo) {
        SysPostEntity entity = SysPostConvert.INSTANCE.convert(vo);
        String postCode = entity.getPostCode();
        if(postCode != null && !postCode.isEmpty()){
            SysPostQuery params = new SysPostQuery();
            params.setPostCode(postCode);
            List<SysPostEntity> list = sysPostMapper.getList(params);
            if(!ObjectUtils.isEmpty(list)){
                for(SysPostEntity item : list){
                    if(!Objects.equals(item.getId(), entity.getId())){
                        throw new ServerException("岗位编码已存在!");
                    }
                }
            }
        }
        sysPostMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 删除岗位
        idList.forEach(id -> {
            SysPostEntity p = new SysPostEntity();
            p.setId(id);
            p.setDbStatus(0);
            sysPostMapper.updateById(p);
        });
        // 删除岗位用户关系
        sysUserPostService.deleteByPostIdList(idList);
    }

    @Override
    public SysPostEntity getById(Long id) {
        return sysPostMapper.getById(id);
    }

}