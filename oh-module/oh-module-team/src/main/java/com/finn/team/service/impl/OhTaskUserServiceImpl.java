package com.finn.team.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.team.convert.OhTaskUserConvert;
import com.finn.team.entity.OhTaskUserEntity;
import com.finn.team.mapper.OhTaskUserMapper;
import com.finn.team.query.OhTaskUserQuery;
import com.finn.team.service.OhTaskUserService;
import com.finn.team.vo.OhTaskUserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务人员表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2022-11-25
 */
@Service
public class OhTaskUserServiceImpl implements OhTaskUserService {

    private final OhTaskUserMapper ohTaskUserMapper;
    public OhTaskUserServiceImpl(OhTaskUserMapper ohTaskUserMapper){
        this.ohTaskUserMapper = ohTaskUserMapper;
    }
    @Override
    public PageResult<OhTaskUserVO> page(OhTaskUserQuery query) {
        List<OhTaskUserEntity> list = ohTaskUserMapper.getList(query);
        return new PageResult<>(OhTaskUserConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public void save(OhTaskUserVO vo) {
        OhTaskUserEntity entity = OhTaskUserConvert.INSTANCE.convert(vo);
        ohTaskUserMapper.save(entity);
    }

    @Override
    public void update(OhTaskUserVO vo) {
        OhTaskUserEntity entity = OhTaskUserConvert.INSTANCE.convert(vo);
        ohTaskUserMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            OhTaskUserEntity param = new OhTaskUserEntity();
            param.setId(id);
            param.setDbStatus(0);
            ohTaskUserMapper.updateById(param);
        });
    }

    @Override
    public OhTaskUserEntity getById(Long id) {
        return ohTaskUserMapper.getById(id);
    }

}