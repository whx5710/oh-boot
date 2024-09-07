package com.iris.system.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.system.base.mapper.SysDictDataMapper;
import com.iris.system.base.mapper.SysDictTypeMapper;
import com.iris.system.base.enums.DictSourceEnum;
import com.iris.system.base.query.SysDictDataQuery;
import com.iris.system.base.query.SysDictTypeQuery;
import com.iris.system.base.vo.SysDictTypeVO;
import com.iris.system.base.vo.SysDictVO;
import com.iris.system.base.entity.SysDictDataEntity;
import com.iris.system.base.entity.SysDictTypeEntity;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.convert.SysDictTypeConvert;
import com.iris.system.base.service.SysDictTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final Logger log = LoggerFactory.getLogger(SysDictTypeServiceImpl.class);

    public SysDictTypeServiceImpl(SysDictDataMapper sysDictDataMapper,
                                  SysDictTypeMapper sysDictTypeMapper) {
        this.sysDictDataMapper = sysDictDataMapper;
        this.sysDictTypeMapper = sysDictTypeMapper;
    }

    @Override
    public PageResult<SysDictTypeVO> page(SysDictTypeQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysDictTypeEntity> list = sysDictTypeMapper.getList(query);
        PageInfo<SysDictTypeEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysDictTypeConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictTypeVO vo) {
        SysDictTypeEntity entity = SysDictTypeConvert.INSTANCE.convert(vo);

        sysDictTypeMapper.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictTypeVO vo) {
        SysDictTypeEntity entity = SysDictTypeConvert.INSTANCE.convert(vo);
        sysDictTypeMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        List<SysDictDataEntity> dataList = sysDictDataMapper.getList(query);

        // 全部字典列表
        List<SysDictVO> dictList = new ArrayList<>(typeList.size());
        for (SysDictTypeEntity type : typeList) {
            SysDictVO dict = new SysDictVO();
            dict.setDictType(type.getDictType());

            for (SysDictDataEntity data : dataList) {
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

    @Override
    public void refreshTransCache() {
        log.info("是否需要刷新呢？");
    }

    @Override
    public SysDictTypeEntity getById(Long id) {
        return sysDictTypeMapper.getById(id);
    }
}
