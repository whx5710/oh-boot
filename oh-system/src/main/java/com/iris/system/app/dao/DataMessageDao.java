package com.iris.system.app.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.iris.framework.common.constant.Constant;
import com.iris.framework.datasource.dao.BaseDao;
import com.iris.system.app.entity.DataMsgEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-21
*/
@Mapper
@DS(Constant.SYS_DB)
public interface DataMessageDao extends BaseDao<DataMsgEntity> {

}