package com.iris.sys.base.mapper;

import com.iris.sys.base.entity.SmsPlatformEntity;
import com.iris.sys.base.query.SmsPlatformQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 短信平台
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsPlatformMapper {

    List<SmsPlatformEntity> getList(SmsPlatformQuery query);

    int insertPlatform(SmsPlatformEntity entity);

    boolean updateById(SmsPlatformEntity entity);

    SmsPlatformEntity getById(@Param("id")Long id);
}