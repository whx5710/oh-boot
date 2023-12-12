package com.iris.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.generator.entity.TableFieldEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 表字段
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface TableFieldDao extends BaseMapper<TableFieldEntity> {

    List<TableFieldEntity> getByTableId(Long tableId);

    void deleteBatchTableIds(Long[] tableIds);
}
