package com.iris.app.mapper;

import com.github.pagehelper.Page;
import com.iris.app.entity.DataMsgEntity;
import com.iris.app.query.DataMsgQuery;
import com.iris.app.vo.DataMsgVO;
import com.iris.framework.datasource.annotations.Pages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-21
*/
@Mapper
public interface DataMessageMapper {

    @Pages // 分页注解
    Page<DataMsgVO> getList(DataMsgQuery query);

    int insertDataMsg(DataMsgEntity param);

    boolean deleteByDate(@Param("date")String date);

    boolean updateById(DataMsgEntity param);
}