package com.iris.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iris.generator.entity.BaseClassEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 基类管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface BaseClassDao extends BaseMapper<BaseClassEntity> {

}