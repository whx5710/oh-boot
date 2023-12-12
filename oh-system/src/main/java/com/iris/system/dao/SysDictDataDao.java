package com.iris.system.dao;

import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.system.entity.SysDictDataEntity;
import com.iris.system.vo.SysDictVO;
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
public interface SysDictDataDao extends BaseDao<SysDictDataEntity> {

    @Select("${sql}")
    List<SysDictVO.DictData> getListForSql(@Param("sql") String sql);
}
