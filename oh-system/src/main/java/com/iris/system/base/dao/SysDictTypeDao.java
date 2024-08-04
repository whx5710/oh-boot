package com.iris.system.base.dao;

import com.iris.system.base.entity.SysDictTypeEntity;
import com.iris.system.base.query.SysDictTypeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典类型
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysDictTypeDao {

    List<SysDictTypeEntity> getList(SysDictTypeQuery query);

    int save(SysDictTypeEntity param);

    boolean updateById(SysDictTypeEntity param);

    SysDictTypeEntity getById(@Param("id")Long id);
}
