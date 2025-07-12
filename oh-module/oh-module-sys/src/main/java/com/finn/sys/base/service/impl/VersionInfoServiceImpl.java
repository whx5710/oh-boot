package com.finn.sys.base.service.impl;

import com.finn.core.utils.PageResult;
import com.finn.framework.utils.ParamsBuilder;
import com.finn.sys.base.convert.VersionInfoConvert;
import com.finn.sys.base.entity.VersionInfoEntity;
import com.finn.sys.base.mapper.VersionInfoMapper;
import com.finn.sys.base.query.VersionInfoQuery;
import com.finn.sys.base.service.VersionInfoService;
import com.finn.sys.base.vo.VersionInfoVO;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 版本信息
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-09-16
 */
@Service
public class VersionInfoServiceImpl implements VersionInfoService {

    private final VersionInfoMapper versionInfoMapper;

    public VersionInfoServiceImpl(VersionInfoMapper versionInfoMapper){
        this.versionInfoMapper = versionInfoMapper;
    }

    @Override
    public PageResult<VersionInfoVO> page(VersionInfoQuery query) {
        ParamsBuilder<VersionInfoEntity> param = ParamsBuilder.of(VersionInfoEntity.class)
                .eq(VersionInfoEntity::getIsCurrVersion, query.getCurrVersion())
                .eq(VersionInfoEntity::getDbStatus, 1)
                .pageNum(query.getPageNum()).pageSize(query.getPageSize())
                .jointSQL("(content like concat('%',#{keyWord}, '%') or version_num like concat('%', #{keyWord},'%'))",
                        "keyWord", query.getKeyWord());
        try (Page<VersionInfoEntity> page = versionInfoMapper.selectPageByParam(param)) {
            return new PageResult<>(VersionInfoConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
        }
    }

    @Override
    public void save(VersionInfoVO vo) {
        // 修改当前版本标记
        versionInfoMapper.updateCurVersion(false);

        VersionInfoEntity entity = VersionInfoConvert.INSTANCE.convert(vo);
        entity.setIsCurrVersion(true);
        versionInfoMapper.save(entity);
    }

    @Override
    public void update(VersionInfoVO vo) {
        VersionInfoEntity entity = VersionInfoConvert.INSTANCE.convert(vo);
        versionInfoMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            VersionInfoEntity param = new VersionInfoEntity();
            param.setId(id);
            param.setDbStatus(0);
            versionInfoMapper.updateById(param);
        });
    }

    /**
     * 获取最新版本信息
     * @return
     */
    @Override
    public VersionInfoEntity latestVersion() {
        VersionInfoQuery query = new VersionInfoQuery();
        query.setCurrVersion(true);
        List<VersionInfoEntity> list = versionInfoMapper.getList(query);
        return list.getFirst();
    }

    @Override
    public VersionInfoEntity getById(Long id) {
        return versionInfoMapper.getById(id);
    }

}