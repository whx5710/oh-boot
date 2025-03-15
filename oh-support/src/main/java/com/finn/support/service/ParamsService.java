package com.finn.support.service;

import com.finn.support.query.ParamsQuery;
import com.finn.support.vo.ParamsVO;
import com.finn.support.entity.ParamsEntity;
import com.finn.core.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface ParamsService {

    PageResult<ParamsVO> page(ParamsQuery query);

    void save(ParamsVO vo);

    void update(ParamsVO vo);

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
    ParamsEntity getByKey(String key);

    /**
     *  根据key获取数据库中的值
     * @param keys 参数Key
     * @return 集合
     */
    Map<String, String> getByKeys(List<String> keys);

    ParamsEntity getById(Long id);
}