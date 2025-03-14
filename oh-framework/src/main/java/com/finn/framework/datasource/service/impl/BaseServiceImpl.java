package com.finn.framework.datasource.service.impl;

import com.finn.core.constant.Constant;
import com.finn.framework.datasource.mapper.SuperMapper;
import com.finn.framework.datasource.service.BaseService;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.framework.utils.ParameterBuilder;
import com.github.pagehelper.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 基础服务类，所有Service都要继承
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class BaseServiceImpl<T> implements BaseService {

    @Resource
    private SuperMapper<T> superMapper;

    /**
     * 原生SQL 数据权限
     *
     * @param tableAlias 表别名，多表关联时，需要填写表别名
     * @param orgIdAlias 机构ID别名，null：表示org_id
     * @return 返回数据权限
     */
    protected String getDataScopeFilter(String tableAlias, String orgIdAlias) {
        UserDetail user = SecurityUser.getUser();
        if(user == null){
            return null;
        }
        // 如果是超级管理员，则不进行数据过滤
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            return null;
        }

        // 如果为null，则设置成空字符串
        if (tableAlias == null) {
            tableAlias = "";
        }

        // 获取表的别名
        if (!tableAlias.isEmpty()) {
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
            if (orgIdAlias == null || orgIdAlias.isEmpty()) {
                orgIdAlias = "org_id";
            }
            sqlFilter.append(tableAlias).append(orgIdAlias);

            StringBuilder result = new StringBuilder();
            for(Long dsl: dataScopeList){
                result.append(dsl).append(",");
            }
            if (!result.isEmpty()) {
                result.deleteCharAt(result.length() - 1); // 移除最后一个多余的逗号
            }
            sqlFilter.append(" in(").append(result).append(")");
            sqlFilter.append(" or ");
        }

        // 查询本人数据
        sqlFilter.append(tableAlias).append("creator").append("=").append(user.getId());

        sqlFilter.append(")");

        return sqlFilter.toString();
    }

    /**
     * 通用查询
     * @param param 参数
     * @return page
     */
    protected Page<T> selectPageByParam(ParameterBuilder<T> param){
        String sql = param.buildSelectSQL();
        Map<String, Object> selectParams = param.getSelectParams();
        return superMapper.selectPageByParam(sql, selectParams);
    }
}