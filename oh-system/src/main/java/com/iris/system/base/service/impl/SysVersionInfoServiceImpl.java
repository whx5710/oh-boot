package com.iris.system.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.dao.SysVersionInfoDao;
import com.iris.system.base.query.SysVersionInfoQuery;
import com.iris.system.base.vo.SysVersionInfoVO;
import com.iris.system.base.convert.SysVersionInfoConvert;
import com.iris.system.base.entity.SysVersionInfoEntity;
import com.iris.system.base.service.SysVersionInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
@Service
public class SysVersionInfoServiceImpl implements SysVersionInfoService {

    private final SysVersionInfoDao sysVersionInfoDao;

    public SysVersionInfoServiceImpl(SysVersionInfoDao sysVersionInfoDao){
        this.sysVersionInfoDao = sysVersionInfoDao;
    }

    @Override
    public PageResult<SysVersionInfoVO> page(SysVersionInfoQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysVersionInfoEntity> list = sysVersionInfoDao.getList(query);
        PageInfo<SysVersionInfoEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysVersionInfoConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(SysVersionInfoVO vo) {
        // 修改当前版本标记
        sysVersionInfoDao.updateCurVersion(false);

        SysVersionInfoEntity entity = SysVersionInfoConvert.INSTANCE.convert(vo);
        entity.setIsCurrVersion(true);
        sysVersionInfoDao.save(entity);
    }

    @Override
    public void update(SysVersionInfoVO vo) {
        SysVersionInfoEntity entity = SysVersionInfoConvert.INSTANCE.convert(vo);
        sysVersionInfoDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            SysVersionInfoEntity param = new SysVersionInfoEntity();
            param.setId(id);
            param.setDbStatus(0);
            sysVersionInfoDao.updateById(param);
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
        List<SysVersionInfoEntity> list = sysVersionInfoDao.getList(query);
        return list.getFirst();
    }

    @Override
    public SysVersionInfoEntity getById(Long id) {
        return sysVersionInfoDao.getById(id);
    }

}