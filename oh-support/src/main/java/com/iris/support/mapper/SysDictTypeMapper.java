package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.support.entity.SysDictTypeEntity;
import com.iris.support.query.SysDictTypeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典类型
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface SysDictTypeMapper {

    List<SysDictTypeEntity> getList(SysDictTypeQuery query);

    int save(SysDictTypeEntity param);

    boolean updateById(SysDictTypeEntity param);

    @Select("select * from sys_dict_type where id = #{id}")
    SysDictTypeEntity getById(@Param("id")Long id);
}
