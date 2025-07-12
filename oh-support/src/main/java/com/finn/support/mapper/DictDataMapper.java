package com.finn.support.mapper;

import com.finn.support.entity.DictDataEntity;
import com.finn.support.query.DictDataQuery;
import com.finn.support.vo.DictDataVO;
import com.finn.support.vo.DictVO;
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
