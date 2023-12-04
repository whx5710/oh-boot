package com.iris.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.generator.entity.TableEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据表
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface TableDao extends BaseMapper<TableEntity> {

}
