package com.iris.support.mapper;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.annotations.Ds;
import com.iris.support.entity.SysDictDataEntity;
import com.iris.support.query.SysDictDataQuery;
import com.iris.support.vo.SysDictVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典数据
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
@Ds(Constant.DYNAMIC_SYS_DB)
public interface SysDictDataMapper {

    @Select("${sql}")
    List<SysDictVO.DictData> getListForSql(@Param("sql") String sql);

    List<SysDictDataEntity> getList(SysDictDataQuery query);

    boolean updateById(SysDictDataEntity param);

    int save(SysDictDataEntity param);

    SysDictDataEntity getById(@Param("id")Long id);
}
