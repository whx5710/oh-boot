package com.iris.system.service;

import cn.hutool.json.JSONObject;
import com.iris.system.entity.SysParamsEntity;
import com.iris.system.query.SysParamsQuery;
import com.iris.system.vo.SysParamsVO;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.BaseService;

import java.util.List;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface SysParamsService extends BaseService<SysParamsEntity> {

    PageResult<SysParamsVO> page(SysParamsQuery query);

    void save(SysParamsVO vo);

    void update(SysParamsVO vo);

    void delete(List<Long> idList);

    /**
     * 根据paramKey，获取字符串值
     *
     * @param paramKey 参数Key
     */
    String getString(String paramKey);

    /**
     * 根据paramKey，获取字符串值，如果没有获取到，默认返回null(不抛出异常)
     * @param paramKey
     * @return
     */
    String getDefaultString(String paramKey);

    /**
     * 根据paramKey，获取整型值
     *
     * @param paramKey 参数Key
     */
    int getInt(String paramKey);

    /**
     * 根据paramKey，获取整型值，默认返回一个值 -1
     *
     * @param paramKey 参数Key
     */
    int getDefaultInt(String paramKey);

    /**
     * 根据paramKey，获取布尔值
     *
     * @param paramKey 参数Key
     */
    boolean getBoolean(String paramKey);

    /**
     * 根据paramKey，获取对象值（redis）
     *
     * @param paramKey  参数Key
     * @param valueType 类型
     */
    <T> T getJSONObject(String paramKey, Class<T> valueType);

    /**
     * 根据key获取数据库中的值
     *
     * @param key  参数Key
     */
    SysParamsEntity getByKey(String key);

    /**
     *  根据key获取数据库中的值
     * @param keys 参数Key
     * @return 集合
     */
    JSONObject getByKeys(List<String> keys);
}