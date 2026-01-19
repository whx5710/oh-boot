package com.finn.system.mapper;

import com.finn.system.entity.DictDataEntity;
import com.finn.system.query.DictDataQuery;
import com.finn.system.vo.DictDataVO;
import com.finn.system.vo.DictVO;
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
public interface DictDataMapper {

    @Select("${sql}")
    List<DictVO.DictData> getListForSql(@Param("sql") String sql);

    List<DictDataVO> getList(DictDataQuery query);

    boolean updateById(DictDataEntity param);

    int save(DictDataEntity param);

    DictDataEntity getById(@Param("id")Long id);
}
