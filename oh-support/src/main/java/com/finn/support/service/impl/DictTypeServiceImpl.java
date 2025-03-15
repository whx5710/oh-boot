package com.finn.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.finn.support.cache.DictCache;
import com.finn.support.convert.DictDataConvert;
import com.finn.support.mapper.DictDataMapper;
import com.finn.support.mapper.DictTypeMapper;
import com.finn.support.enums.DictSourceEnum;
import com.finn.support.query.DictDataQuery;
import com.finn.support.query.DictTypeQuery;
import com.finn.support.vo.DictDataSingleVO;
import com.finn.support.vo.DictDataVO;
import com.finn.support.vo.DictTypeVO;
import com.finn.support.vo.DictVO;
import com.finn.support.entity.DictTypeEntity;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.support.convert.DictTypeConvert;
import com.finn.support.service.DictTypeService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {
    private final DictDataMapper dictDataMapper;
    private final DictTypeMapper dictTypeMapper;

    private final DictCache dictCache;

    private final Logger log = LoggerFactory.getLogger(DictTypeServiceImpl.class);

    public DictTypeServiceImpl(DictDataMapper dictDataMapper,
                               DictTypeMapper dictTypeMapper,
                               DictCache dictCache) {
        this.dictDataMapper = dictDataMapper;
        this.dictTypeMapper = dictTypeMapper;
        this.dictCache = dictCache;
    }

    @Override
    public PageResult<DictTypeVO> page(DictTypeQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<DictTypeEntity> list = dictTypeMapper.getList(query);
        PageInfo<DictTypeEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(DictTypeConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }
    @Override
    public void save(DictTypeVO vo) {
        DictTypeEntity entity = DictTypeConvert.INSTANCE.convert(vo);

        dictTypeMapper.save(entity);
    }

    @Override
    public void update(DictTypeVO vo) {
        DictTypeEntity entity = DictTypeConvert.INSTANCE.convert(vo);
        dictTypeMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            DictTypeEntity param = new DictTypeEntity();
            param.setId(id);
            param.setDbStatus(0);
            dictTypeMapper.updateById(param);
        });
    }

    @Override
    public List<DictVO.DictData> getDictSql(Long id) {
        DictTypeEntity entity = dictTypeMapper.getById(id);
        try {
            return dictDataMapper.getListForSql(entity.getDictSql());
        } catch (Exception e) {
            throw new ServerException("动态SQL执行失败，请检查SQL是否正确！");
        }
    }

    @Override
    public List<DictVO> getDictList() {
        // 全部字典类型列表
        List<DictTypeEntity> typeList = dictTypeMapper.getList(new DictTypeQuery());

        // 全部字典数据列表
        DictDataQuery query = new DictDataQuery();
        List<DictDataVO> dataList = dictDataMapper.getList(query);

        // 全部字典列表
        List<DictVO> dictList = new ArrayList<>(typeList.size());
        for (DictTypeEntity type : typeList) {
            DictVO dict = new DictVO();
            dict.setDictType(type.getDictType());

            for (DictDataVO data : dataList) {
                if (type.getId().equals(data.getDictTypeId())) {
                    dict.getDataList().add(new DictVO.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass()));
                }
            }

            // 数据来源动态SQL
            if (type.getDictSource() == DictSourceEnum.SQL.getValue()) {
                // 增加动态列表
                String sql = type.getDictSql();
                try {
                    dict.setDataList(dictDataMapper.getListForSql(sql));
                } catch (Exception e) {
                    log.error("增加动态字典异常: type={}", type, e);
                }
            }

            dictList.add(dict);
        }

        return dictList;
    }

    /**
     * 字典缓存刷新
     */
    @Override
    public void refreshTransCache() {
        //保存列表
        List<DictVO> list = getDictList();
        dictCache.saveList(list);
        // 逐一单个保存
        List<DictDataVO>  dataList = dictDataMapper.getList(new DictDataQuery());
        dictCache.delSingleAll(); // 先清除
        if(dataList != null && dataList.size() > 0){
            for(DictDataVO vo: dataList){
                DictDataSingleVO dataSingleVO = DictDataConvert.INSTANCE.convertSingle(vo);
                dictCache.save(dataSingleVO);
            }
        }
    }

    @Override
    public DictTypeEntity getById(Long id) {
        return dictTypeMapper.getById(id);
    }

    @PostConstruct
    public void init() {
        log.debug("加载字典数据到Redis缓存中");
        refreshTransCache();
    }
}
