package com.iris.sys.base.mapper;

import com.iris.sys.base.entity.SysDictTypeEntity;
import com.iris.sys.base.query.SysDictTypeQuery;
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
public interface SysDictTypeMapper {

    List<SysDictTypeEntity> getList(SysDictTypeQuery query);

    int save(SysDictTypeEntity param);

    boolean updateById(SysDictTypeEntity param);

    SysDictTypeEntity getById(@Param("id")Long id);
}
