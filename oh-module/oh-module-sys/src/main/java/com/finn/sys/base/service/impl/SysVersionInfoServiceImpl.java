package com.finn.sys.base.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.sys.base.convert.SysVersionInfoConvert;
import com.finn.sys.base.entity.SysVersionInfoEntity;
import com.finn.sys.base.mapper.SysVersionInfoMapper;
import com.finn.sys.base.query.SysVersionInfoQuery;
import com.finn.sys.base.service.SysVersionInfoService;
import com.finn.sys.base.vo.SysVersionInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
@Service
public class SysVersionInfoServiceImpl implements SysVersionInfoService {

    private final SysVersionInfoMapper sysVersionInfoMapper;

    public SysVersionInfoServiceImpl(SysVersionInfoMapper sysVersionInfoMapper){
        this.sysVersionInfoMapper = sysVersionInfoMapper;
    }

    @Override
    public PageResult<SysVersionInfoVO> page(SysVersionInfoQuery query) {
        List<SysVersionInfoEntity> list = sysVersionInfoMapper.getList(query);
        return new PageResult<>(SysVersionInfoConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public void save(SysVersionInfoVO vo) {
        // 修改当前版本标记
        sysVersionInfoMapper.updateCurVersion(false);

        SysVersionInfoEntity entity = SysVersionInfoConvert.INSTANCE.convert(vo);
        entity.setIsCurrVersion(true);
        sysVersionInfoMapper.save(entity);
    }

    @Override
    public void update(SysVersionInfoVO vo) {
        SysVersionInfoEntity entity = SysVersionInfoConvert.INSTANCE.convert(vo);
        sysVersionInfoMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            SysVersionInfoEntity param = new SysVersionInfoEntity();
            param.setId(id);
            param.setDbStatus(0);
            sysVersionInfoMapper.updateById(param);
        });
    }

    /**
     * 获取最新版本信息
     * @return
     */
    @Override
    public SysVersionInfoEntity latestVersion() {
        SysVersionInfoQuery query = new SysVersionInfoQuery();
        query.setCurrVersion(true);
        List<SysVersionInfoEntity> list = sysVersionInfoMapper.getList(query);
        return list.getFirst();
    }

    @Override
    public SysVersionInfoEntity getById(Long id) {
        return sysVersionInfoMapper.getById(id);
    }

}