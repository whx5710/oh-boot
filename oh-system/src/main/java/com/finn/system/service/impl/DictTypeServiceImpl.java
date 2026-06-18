package com.finn.system.service.impl;

import com.finn.framework.datasource.wrapper.Wrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.entity.PageResult;
import com.finn.framework.datasource.wrapper.CountWrapper;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.datasource.wrapper.UpdateWrapper;
import com.finn.system.cache.DictCache;
import com.finn.system.convert.DictDataConvert;
import com.finn.system.convert.DictTypeConvert;
import com.finn.system.entity.DictDataEntity;
import com.finn.system.entity.DictTypeEntity;
import com.finn.system.enums.DictSourceEnum;
import com.finn.system.mapper.DictDataMapper;
import com.finn.system.mapper.DictTypeMapper;
import com.finn.system.query.DictDataQuery;
import com.finn.system.query.DictTypeQuery;
import com.finn.system.service.DictTypeService;
import com.finn.system.vo.DictDataVO;
import com.finn.system.vo.DictTypeVO;
import com.finn.system.vo.DictVO;
import com.github.pagehelper.Page;
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
        Page<DictTypeEntity> page = dictTypeMapper.listByWrapper(getDictTypeQuery(query));
        return new PageResult<>(DictTypeConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }
    @Override
    public void save(DictTypeVO vo) {
        DictTypeEntity entity = DictTypeConvert.INSTANCE.convert(vo);
        // 判断类型是否存在
        long l = dictTypeMapper.count(CountWrapper.of(DictTypeEntity.class).eq(DictTypeEntity::getDbStatus, 1)
                .eq(DictTypeEntity::getDictType, entity.getDictType()));
        if(l > 0){
            throw new ServerException("字典类型已存在");
        }
        dictTypeMapper.insert(entity);

        // 缓存数据
        QueryWrapper<DictDataEntity> queryWrapper = QueryWrapper.of(DictDataEntity.class);
        queryWrapper.eq(DictDataEntity::getDbStatus, 1).eq(DictDataEntity::getDictTypeId, entity.getId())
                .orderBy(DictDataEntity::getSort);
        List<DictDataEntity> list = dictDataMapper.listByWrapper(queryWrapper);
        if(list != null){
            List<DictDataVO> dataVOList = new ArrayList<>(list.size());
            for(DictDataEntity item: list){
                DictDataVO dictDataVO = DictDataConvert.INSTANCE.convert(item);
                dictDataVO.setDictType(entity.getDictType());
                dictDataVO.setDictTypeId(entity.getId());
                dataVOList.add(dictDataVO);
            }
            DictVO dictVO = new DictVO();
            dictVO.setDictType(entity.getDictType());
            dictVO.setDataList(dataVOList);
            dictCache.save(dictVO);
        }
    }

    @Override
    public void update(DictTypeVO vo) {
        if(vo.getId() == null){
            throw new ServerException("字典类型ID不能为空");
        }
        if(vo.getDictType() != null && !vo.getDictType().isEmpty()){
            List<DictTypeEntity> list = dictTypeMapper.listByWrapper(QueryWrapper.of(DictTypeEntity.class).eq(DictTypeEntity::getDbStatus, 1)
                    .eq(DictTypeEntity::getDictType, vo.getDictType()).ne(DictTypeEntity::getId, vo.getId()));
            if(list != null && !list.isEmpty()){
                throw new ServerException("字典类型重复，请检查");
            }
        }
        DictTypeEntity entity = DictTypeConvert.INSTANCE.convert(vo);
        dictTypeMapper.updateById(entity);

        // 缓存数据
        entity = dictTypeMapper.findById(entity.getId(), DictTypeEntity.class);
        QueryWrapper<DictDataEntity> queryWrapper = QueryWrapper.of(DictDataEntity.class);
        queryWrapper.eq(DictDataEntity::getDbStatus, 1).eq(DictDataEntity::getDictTypeId, entity.getId())
                .orderBy(DictDataEntity::getSort);
        List<DictDataEntity> list = dictDataMapper.listByWrapper(queryWrapper);
        if(list != null){
            List<DictDataVO> dataVOList = new ArrayList<>(list.size());
            for(DictDataEntity item: list){
                DictDataVO dictDataVO = DictDataConvert.INSTANCE.convert(item);
                dictDataVO.setDictType(entity.getDictType());
                dictDataVO.setDictTypeId(entity.getId());
                dataVOList.add(dictDataVO);
            }
            DictVO dictVO = new DictVO();
            dictVO.setDictType(entity.getDictType());
            dictVO.setDataList(dataVOList);
            dictCache.save(dictVO);
        }
    }

    @Override
    public void delete(List<Long> idList) {
        // 判断是否有字典数据
        long l = dictDataMapper.count(CountWrapper.of(DictDataEntity.class).eq(DictDataEntity::getDbStatus, 1)
                .in(DictDataEntity::getDictTypeId, idList));
        if(l > 0){
            throw new ServerException("请先删除字典数据");
        }
        int i = dictTypeMapper.updateByWrapper(UpdateWrapper.of(DictTypeEntity.class).set(DictTypeEntity::getDbStatus, 0)
                .in(DictTypeEntity::getId, idList));
        log.info("更新{}条记录", i);
    }

    @Override
    public List<DictDataVO> getDictSql(Long id) {
        DictTypeEntity entity = dictTypeMapper.findById(id, DictTypeEntity.class);
        try {
            return dictDataMapper.getListForSql(entity.getDictSql());
        } catch (Exception e) {
            throw new ServerException("动态SQL执行失败，请检查SQL是否正确！");
        }
    }

    @Override
    public List<DictVO> getDictList() {
        // 全部字典类型列表
        List<DictTypeEntity> typeList = dictTypeMapper.listByWrapper(getDictTypeQuery(null));

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
                    DictDataVO dictDataVO = new DictDataVO();
                    dictDataVO.setId(data.getId());
                    dictDataVO.setDictTypeId(type.getId());
                    dictDataVO.setDictType(type.getDictName());
                    dictDataVO.setDictLabel(data.getDictLabel());
                    dictDataVO.setDictValue(data.getDictValue());
                    dictDataVO.setLabelClass(data.getLabelClass());
                    dictDataVO.setSort(data.getSort());
                    dict.getDataList().add(dictDataVO);
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
        dictCache.saveAllList(list);
    }

    @Override
    public DictTypeEntity getById(Long id) {
        return dictTypeMapper.findById(id, DictTypeEntity.class);
    }

    /**
     * 构建查询条件
     * @param query
     * @return
     */
    private Wrapper<DictTypeEntity> getDictTypeQuery(DictTypeQuery query){
        if(query == null){
            return QueryWrapper.of(DictTypeEntity.class).eq(DictTypeEntity::getDbStatus, 1);
        }else{
            return QueryWrapper.of(DictTypeEntity.class).eq(DictTypeEntity::getDbStatus, 1)
                    .like(DictTypeEntity::getDictType, query.getDictType())
                    .like(DictTypeEntity::getDictName, query.getDictName())
                    .jointSQL("(dict_name like concat('%',#{keyWord}, '%') " +
                            "or dict_type like concat('%',#{keyWord}, '%') " +
                            "or remark like concat('%',#{keyWord}, '%'))","keyWord", query.getKeyWord())
                    .orderBy("sort").page(query.getPageNum(), query.getPageSize());
        }
    }
}
