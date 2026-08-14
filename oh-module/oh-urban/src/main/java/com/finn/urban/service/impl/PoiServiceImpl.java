package com.finn.urban.service.impl;

import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.urban.convert.PoiConvert;
import com.finn.urban.entity.Poi;
import com.finn.urban.mapper.PoiMapper;
import com.finn.urban.query.PoiQuery;
import com.finn.urban.service.PoiService;
import com.finn.urban.vo.PoiVO;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 兴趣点
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-13 15:09:28
 *
 */
@Service
public class PoiServiceImpl implements PoiService {

    private final PoiMapper poiMapper;
    public PoiServiceImpl(PoiMapper poiMapper) {
        this.poiMapper = poiMapper;
    }

    @Override
    public Page<Poi> page(PoiQuery query) {
        QueryWrapper<Poi> queryWrapper = QueryWrapper.of(Poi.class);
        queryWrapper.like(Poi::getLocation, query.getLocation()).eq(Poi::getGeoType, query.getGeoType())
                .orderBy("create_time desc").page(query.getPageNum(), query.getPageSize());
        if(query.getKeyWord() != null && !query.getKeyWord().isEmpty()){
            queryWrapper.jointSQL("(location like concat('%',#{keyWord}, '%') or area_name like concat('%',#{keyWord}, '%') " +
                    " or area_code like concat('%',#{keyWord}, '%') or remark like concat('%',#{keyWord}, '%'))", "keyWord", query.getKeyWord());
        }
        return poiMapper.listByWrapper(queryWrapper);
    }

    @Override
    public Long save(PoiVO vo) {
        Poi entity = PoiConvert.INSTANCE.convert(vo);

        poiMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void update(PoiVO vo) {
        Poi entity = PoiConvert.INSTANCE.convert(vo);
        poiMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        UpdateWrapper<Poi> updateWrapper = UpdateWrapper.of(Poi.class);
        updateWrapper.set(Poi::getDbStatus, 0).in(Poi::getId, idList);
        poiMapper.updateByWrapper(updateWrapper);
    }

}
