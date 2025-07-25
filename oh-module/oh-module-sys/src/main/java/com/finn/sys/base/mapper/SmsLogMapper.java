package com.finn.sys.base.mapper;

import com.finn.framework.datasource.annotations.Pages;
import com.finn.sys.base.entity.SmsLogEntity;
import com.finn.sys.base.query.SmsLogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 短信日志
*
* @author 王小费 whx5710@qq.com
*/
@Mapper
public interface SmsLogMapper {

    @Pages
    List<SmsLogEntity> getList(SmsLogQuery query);

    SmsLogEntity getById(@Param("id")Long id);

    int insertSmsLog(SmsLogEntity entity);
}