package com.finn.framework.service.impl;

import com.finn.core.constant.Constant;
import com.finn.core.utils.JsonUtils;
import com.finn.framework.datasource.mapper.SuperMapper;
import com.finn.framework.service.BaseService;
import com.finn.framework.security.user.SecurityUser;
import com.finn.framework.security.user.UserDetail;
import com.finn.framework.utils.ParamsBuilder;
import com.github.pagehelper.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


/**
 * 基础服务类，ServiceImpl继承
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
     * 通用分页查询
     * @param param 参数
     * @return page结合
     */
    protected Page<T> selectPageByParam(ParamsBuilder<T> param){
        String sql = param.buildSelectSQL();
        Page<T> page = superMapper.selectPageByParam(sql, param);
        List<T> list = page.getResult();
        // 数据类型转换
        if(list != null && !list.isEmpty()){
            List<T> result = JsonUtils.parseArray(list, param.getClazz());
            page.removeAll(list);
            page.addAll(result);
        }
        return page;
    }


    /**
     * 通用查询列表
     * @param param 参数
     * @return list集合
     */
    protected List<T> selectByParam(ParamsBuilder<T> param){
        String sql = param.buildSelectSQL();
        List<HashMap> list = superMapper.selectListByParam(sql, param);
        return JsonUtils.parseArray(list, param.getClazz());
    }
}