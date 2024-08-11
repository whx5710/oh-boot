package com.iris.system.app.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.system.app.entity.DataMsgEntity;
import com.iris.system.app.query.DataMsgQuery;
import com.iris.system.app.vo.DataMsgVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-21
*/
@Mapper
@DS(Constant.SYS_DB)
public interface DataMessageDao {

    List<DataMsgVO> getList(DataMsgQuery query);

    int insertDataMsg(DataMsgEntity param);

    boolean deleteByDate(@Param("date")String date);

    boolean updateById(DataMsgEntity param);
}