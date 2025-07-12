package com.finn.app.mapper;

import com.finn.framework.datasource.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.finn.app.entity.DataMsgEntity;
import com.finn.app.query.DataMsgQuery;
import com.finn.app.vo.DataMsgVO;
import com.finn.framework.datasource.annotations.Pages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-21
*/
@Mapper
public interface DataMessageMapper extends BaseMapper<DataMsgEntity> {

    @Pages // 分页注解
    Page<DataMsgVO> getList(DataMsgQuery query);

    int insertDataMsg(DataMsgEntity param);

    boolean deleteByDate(@Param("date")String date);

//    boolean updateById(DataMsgEntity param);
}