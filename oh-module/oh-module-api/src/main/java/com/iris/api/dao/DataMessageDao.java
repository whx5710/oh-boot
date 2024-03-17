package com.iris.api.dao;

import com.iris.api.entity.DataMsgEntity;
import com.iris.framework.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 项目信息表
*
* @author 王小费 whx5710@qq.com
* @since 1.0.0 2023-07-21
*/
@Mapper
public interface DataMessageDao extends BaseDao<DataMsgEntity> {

}