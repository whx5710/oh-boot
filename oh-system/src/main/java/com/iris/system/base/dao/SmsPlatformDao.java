package com.iris.system.base.dao;

import com.iris.system.base.entity.SmsPlatformEntity;
import com.iris.system.base.query.SmsPlatformQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 短信平台
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsPlatformDao {

    List<SmsPlatformEntity> getList(SmsPlatformQuery query);

    int insertPlatform(SmsPlatformEntity entity);

    boolean updateById(SmsPlatformEntity entity);

    SmsPlatformEntity getById(@Param("id")Long id);
}