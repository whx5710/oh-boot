package com.finn.support.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.finn.support.cache.SysDictCache;
import com.finn.support.convert.SysDictDataConvert;
import com.finn.support.mapper.SysDictDataMapper;
import com.finn.support.mapper.SysDictTypeMapper;
import com.finn.support.enums.DictSourceEnum;
import com.finn.support.query.SysDictDataQuery;
import com.finn.support.query.SysDictTypeQuery;
import com.finn.support.vo.SysDictDataSingleVO;
import com.finn.support.vo.SysDictDataVO;
import com.finn.support.vo.SysDictTypeVO;
import com.finn.support.vo.SysDictVO;
import com.finn.support.entity.SysDictTypeEntity;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.support.convert.SysDictTypeConvert;
import com.finn.support.service.SysDictTypeService;
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
public class SysDictTypeServiceImpl implements SysDictTypeService {
    private final SysDictDataMapper sysDictDataMapper;
    private final SysDictTypeMapper sysDictTypeMapper;

    private final SysDictCache sysDictCache;

    private final Logger log = LoggerFactory.getLogger(SysDictTypeServiceImpl.class);

    public SysDictTypeServiceImpl(SysDictDataMapper sysDictDataMapper,
                                  SysDictTypeMapper sysDictTypeMapper,
                                  SysDictCache sysDictCache) {
        this.sysDictDataMapper = sysDictDataMapper;
        this.sysDictTypeMapper = sysDictTypeMapper;
        this.sysDictCache = sysDictCache;
    }

    @Override
    public PageResult<SysDictTypeVO> page(SysDictTypeQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<SysDictTypeEntity> list = sysDictTypeMapper.getList(query);
        PageInfo<SysDictTypeEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysDictTypeConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }
    @Override
    public void save(SysDictTypeVO vo) {
        SysDictTypeEntity entity = SysDictTypeConvert.INSTANCE.convert(vo);

        sysDictTypeMapper.save(entity);
    }

    @Override
    public void update(SysDictTypeVO vo) {
        SysDictTypeEntity entity = SysDictTypeConvert.INSTANCE.convert(vo);
        sysDictTypeMapper.updateById(entity);
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            SysDictTypeEntity param = new SysDictTypeEntity();
            param.setId(id);
            param.setDbStatus(0);
            sysDictTypeMapper.updateById(param);
        });
    }

    @Override
    public List<SysDictVO.DictData> getDictSql(Long id) {
        SysDictTypeEntity entity = sysDictTypeMapper.getById(id);
        try {
            return sysDictDataMapper.getListForSql(entity.getDictSql());
        } catch (Exception e) {
            throw new ServerException("动态SQL执行失败，请检查SQL是否正确！");
        }
    }

    @Override
    public List<SysDictVO> getDictList() {
        // 全部字典类型列表
        List<SysDictTypeEntity> typeList = sysDictTypeMapper.getList(new SysDictTypeQuery());

        // 全部字典数据列表
        SysDictDataQuery query = new SysDictDataQuery();
        List<SysDictDataVO> dataList = sysDictDataMapper.getList(query);

        // 全部字典列表
        List<SysDictVO> dictList = new ArrayList<>(typeList.size());
        for (SysDictTypeEntity type : typeList) {
            SysDictVO dict = new SysDictVO();
            dict.setDictType(type.getDictType());

            for (SysDictDataVO data : dataList) {
                if (type.getId().equals(data.getDictTypeId())) {
                    dict.getDataList().add(new SysDictVO.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass()));
                }
            }

            // 数据来源动态SQL
            if (type.getDictSource() == DictSourceEnum.SQL.getValue()) {
                // 增加动态列表
                String sql = type.getDictSql();
                try {
                    dict.setDataList(sysDictDataMapper.getListForSql(sql));
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
        List<SysDictVO> list = getDictList();
        sysDictCache.saveList(list);
        // 逐一单个保存
        List<SysDictDataVO>  dataList = sysDictDataMapper.getList(new SysDictDataQuery());
        sysDictCache.delSingleAll(); // 先清除
        if(dataList != null && dataList.size() > 0){
            for(SysDictDataVO vo: dataList){
                SysDictDataSingleVO dataSingleVO = SysDictDataConvert.INSTANCE.convertSingle(vo);
                sysDictCache.save(dataSingleVO);
            }
        }
    }

    @Override
    public SysDictTypeEntity getById(Long id) {
        return sysDictTypeMapper.getById(id);
    }

    @PostConstruct
    public void init() {
        log.debug("加载字典数据到Redis缓存中");
        refreshTransCache();
    }
}
