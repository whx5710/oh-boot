package com.finn.support.mapper;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.support.entity.DictTypeEntity;
import com.finn.support.query.DictTypeQuery;
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
public interface DictTypeMapper {

    List<DictTypeEntity> getList(DictTypeQuery query);

    int save(DictTypeEntity param);

    boolean updateById(DictTypeEntity param);

    @Select("select * from sys_dict_type where id = #{id}")
    DictTypeEntity getById(@Param("id")Long id);
}
