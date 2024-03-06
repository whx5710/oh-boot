package com.iris.framework.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.iris.framework.security.user.SecurityUser;
import com.iris.framework.security.user.UserDetail;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * mybatis-plus 自动填充字段
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_TIME = "createTime";
    private final static String CREATOR = "creator";
    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATER = "updater";
    private final static String ORG_ID = "orgId";

    /**
     * 数据状态标记，0删除1有效
     */
    private final static String DB_STATUS = "dbStatus";

    /**
     * 新增
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        UserDetail user = SecurityUser.getUser();
        LocalDateTime date = LocalDateTime.now();

        // 用户字段填充
        if (user != null) {
            // 创建人
            setFieldValByName(CREATOR, user.getId(), metaObject);
            // 创建者所属机构
            setFieldValByName(ORG_ID, user.getOrgId(), metaObject);
        }
        // 创建时间
        setFieldValByName(CREATE_TIME, date, metaObject);
        // 数据状态标记，0删除1有效
        setFieldValByName(DB_STATUS, 1, metaObject);
    }

    /**
     * 更新
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新者
        setFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
        // 更新时间
        setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
        // 更新删除标志
        if(metaObject.hasGetter(DB_STATUS) && metaObject.getValue(DB_STATUS) == null){
            setFieldValByName(DB_STATUS, 1, metaObject);
        }
    }
}