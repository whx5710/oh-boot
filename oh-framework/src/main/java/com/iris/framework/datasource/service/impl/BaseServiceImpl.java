package com.iris.framework.datasource.service.impl;

import com.iris.core.constant.Constant;
import com.iris.framework.datasource.service.BaseService;
import com.iris.framework.security.user.SecurityUser;
import com.iris.framework.security.user.UserDetail;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 基础服务类，所有Service都要继承
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class BaseServiceImpl implements BaseService {
    /**
     * 原生SQL 数据权限
     *
     * @param tableAlias 表别名，多表关联时，需要填写表别名
     * @param orgIdAlias 机构ID别名，null：表示org_id
     * @return 返回数据权限
     */
    protected String getDataScopeFilter(String tableAlias, String orgIdAlias) {
        UserDetail user = SecurityUser.getUser();
        // 如果是超级管理员，则不进行数据过滤
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            return null;
        }

        // 如果为null，则设置成空字符串
        if (tableAlias == null) {
            tableAlias = "";
        }

        // 获取表的别名
        if (StringUtils.hasText(tableAlias)) {
            tableAlias += ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        // 数据权限范围
        List<Long> dataScopeList = user.getDataScopeList();
        // 全部数据权限
        if (dataScopeList == null) {
            return null;
        }
        // 数据过滤
        if (!dataScopeList.isEmpty()) {
            if (!StringUtils.hasText(orgIdAlias)) {
                orgIdAlias = "org_id";
            }
            sqlFilter.append(tableAlias).append(orgIdAlias);
            StringBuilder inStr = new StringBuilder();
            for(int i = 0; i < dataScopeList.size(); i++){
                if(i == (dataScopeList.size() - 1)){
                    inStr.append(dataScopeList.get(i));
                }else{
                    inStr.append(dataScopeList.get(i)).append(",");
                }
            }
            sqlFilter.append(" in(").append(inStr).append(")");

            sqlFilter.append(" or ");
        }

        // 查询本人数据
        sqlFilter.append(tableAlias).append("creator").append("=").append(user.getId());

        sqlFilter.append(")");

        return sqlFilter.toString();
    }
}