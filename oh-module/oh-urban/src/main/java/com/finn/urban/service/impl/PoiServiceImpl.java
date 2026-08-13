package com.finn.urban.service.impl;

import com.finn.common.entity.PageResult;
import com.finn.urban.convert.PoiConvert;
import com.finn.urban.entity.Poi;
import com.finn.urban.mapper.PoiMapper;
import com.finn.urban.query.PoiQuery;
import com.finn.urban.service.PoiService;
import com.finn.urban.vo.PoiVO;
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
    public PageResult<PoiVO> page(PoiQuery query) {
        List<Poi> list = poiMapper.getList(query);
        return new PageResult<>(PoiConvert.INSTANCE.convertList(list), query.getTotal());
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
        idList.forEach(id -> {
            Poi param = new Poi();
            param.setId(id);
            param.setDbStatus(0);
            poiMapper.updateById(param);
        });
    }

}
