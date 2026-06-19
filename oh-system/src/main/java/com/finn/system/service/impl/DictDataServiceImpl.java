package com.finn.system.service.impl;

import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.entity.PageResult;
import com.finn.framework.datasource.wrapper.CountWrapper;
import com.finn.system.cache.DictCache;
import com.finn.system.convert.DictDataConvert;
import com.finn.system.entity.DictDataEntity;
import com.finn.system.entity.DictTypeEntity;
import com.finn.system.mapper.DictDataMapper;
import com.finn.system.mapper.DictTypeMapper;
import com.finn.system.query.DictDataQuery;
import com.finn.system.service.DictDataService;
import com.finn.system.vo.DictDataVO;
import com.finn.system.vo.DictVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class DictDataServiceImpl implements DictDataService {
    private final DictDataMapper dictDataMapper;
    private final DictTypeMapper dictTypeMapper;
    private final DictCache dictCache;
    public DictDataServiceImpl(DictDataMapper dictDataMapper, DictTypeMapper dictTypeMapper,
                               DictCache dictCache) {
        this.dictDataMapper = dictDataMapper;
        this.dictTypeMapper = dictTypeMapper;
        this.dictCache = dictCache;
    }

    @Override
    public PageResult<DictDataVO> page(DictDataQuery query) {
        Page<DictDataEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<DictDataVO> list = dictDataMapper.getList(query);
        return new PageResult<>(list, page.getTotal());
    }

    @Override
    public void save(DictDataVO vo) {
        if(vo.getDictTypeId() == null || vo.getDictValue() == null){
            throw new ServerException("字典数据不能为空");
        }
        long l = dictDataMapper.count(CountWrapper.of(DictDataEntity.class).eq(DictDataEntity::getDbStatus, 1)
                .eq(DictDataEntity::getDictTypeId, vo.getDictTypeId()).eq(DictDataEntity::getDictValue, vo.getDictValue()));
        if(l > 0){
            throw new ServerException("字典数据已存在");
        }
        DictDataEntity entity = DictDataConvert.INSTANCE.convert(vo);
        dictDataMapper.insert(entity);

        // 缓存
        DictTypeEntity dictType = dictTypeMapper.findById(entity.getDictTypeId(), DictTypeEntity.class);
        QueryWrapper<DictDataEntity> queryWrapper = QueryWrapper.of(DictDataEntity.class);
        queryWrapper.eq(DictDataEntity::getDbStatus, 1).eq(DictDataEntity::getDictTypeId, dictType.getId())
                .orderBy(DictDataEntity::getSort);
        List<DictDataEntity> list = dictDataMapper.listByWrapper(queryWrapper);
        if(list != null){
            List<DictDataVO> dataVOList = new ArrayList<>(list.size());
            for(DictDataEntity item: list){
                DictDataVO dictDataVO = DictDataConvert.INSTANCE.convert(item);
                dictDataVO.setDictType(dictType.getDictType());
                dictDataVO.setDictTypeId(dictType.getId());
                dataVOList.add(dictDataVO);
            }
            DictVO dictVO = new DictVO();
            dictVO.setDictType(dictType.getDictType());
            dictVO.setDataList(dataVOList);
            dictCache.save(dictVO);
        }
    }

    @Override
    public void update(DictDataVO vo) {
        if(vo.getId() == null){
            throw new ServerException("字典数据ID不能为空");
        }
        DictDataEntity entityDb = getById(vo.getId());
        if(entityDb == null || entityDb.getId() == null){
            throw new ServerException("字典数据不存在，修改失败");
        }
        if(vo.getDictTypeId() != null && vo.getDictValue() != null){
            long l = dictDataMapper.count(CountWrapper.of(DictDataEntity.class).eq(DictDataEntity::getDictTypeId, vo.getDictTypeId())
                    .eq(DictDataEntity::getDbStatus, 1).eq(DictDataEntity::getDictValue, vo.getDictValue())
                    .ne(DictDataEntity::getId, vo.getId()));
            if(l > 0){
                throw new ServerException("字典数据重复，修改失败");
            }
        }
        DictDataEntity entity = DictDataConvert.INSTANCE.convert(vo);
        dictDataMapper.updateById(entity);

        // 缓存
        entity = dictDataMapper.findById(entity.getId(), DictDataEntity.class);

        DictTypeEntity dictType = dictTypeMapper.findById(entity.getDictTypeId(), DictTypeEntity.class);
        QueryWrapper<DictDataEntity> queryWrapper = QueryWrapper.of(DictDataEntity.class);
        queryWrapper.eq(DictDataEntity::getDbStatus, 1).eq(DictDataEntity::getDictTypeId, dictType.getId())
                .orderBy(DictDataEntity::getSort);
        List<DictDataEntity> list = dictDataMapper.listByWrapper(queryWrapper);
        if(list != null){
            List<DictDataVO> dataVOList = new ArrayList<>(list.size());
            for(DictDataEntity item: list){
                DictDataVO dictDataVO = DictDataConvert.INSTANCE.convert(item);
                dictDataVO.setDictType(dictType.getDictType());
                dictDataVO.setDictTypeId(dictType.getId());
                dataVOList.add(dictDataVO);
            }
            DictVO dictVO = new DictVO();
            dictVO.setDictType(dictType.getDictType());
            dictVO.setDataList(dataVOList);
            dictCache.save(dictVO);
        }

    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            DictDataEntity param = new DictDataEntity();
            param.setId(id);
            param.setDbStatus(0);
            dictDataMapper.updateById(param);
        });
    }

    @Override
    public DictDataEntity getById(Long id) {
        return dictDataMapper.findById(id, DictDataEntity.class);
    }

}