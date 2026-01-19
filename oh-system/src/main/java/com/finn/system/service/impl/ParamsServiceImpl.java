package com.finn.system.service.impl;

import com.finn.core.exception.ServerException;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.JsonUtils;
import com.finn.core.utils.PageResult;
import com.finn.framework.datasource.utils.QueryWrapper;
import com.finn.framework.datasource.utils.UpdateWrapper;
import com.finn.framework.datasource.utils.Wrapper;
import com.finn.framework.service.impl.BaseServiceImpl;
import com.finn.system.cache.ParamsCache;
import com.finn.system.convert.ParamsConvert;
import com.finn.system.entity.ParamsEntity;
import com.finn.system.mapper.ParamsMapper;
import com.finn.system.query.ParamsQuery;
import com.finn.system.service.ParamsService;
import com.finn.system.vo.ParamsVO;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class ParamsServiceImpl extends BaseServiceImpl<ParamsEntity> implements ParamsService {

    private final ParamsCache paramsCache;

    private final ParamsMapper paramsMapper;

    public ParamsServiceImpl(ParamsCache paramsCache, ParamsMapper paramsMapper){
        this.paramsCache = paramsCache;
        this.paramsMapper = paramsMapper;
    }

    @Override
    public PageResult<ParamsVO> page(ParamsQuery query) {
        Page<ParamsEntity> page = paramsMapper.selectPageByWrapper(getQueryWrapper(query));
        return new PageResult<>(ParamsConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
    }

    @Override
    public void save(ParamsVO vo) {
        AssertUtils.isBlank(vo.getParamKey(), "参数paramKey");
        vo.setParamKey(vo.getParamKey().toUpperCase());
        // 判断 参数键 是否存在
        boolean exist = paramsMapper.isExist(vo.getParamKey());
        if (exist) {
            throw new ServerException("参数键已存在");
        }

        ParamsEntity entity = ParamsConvert.INSTANCE.convert(vo);

        paramsMapper.insert(entity);

        // 保存到缓存
        paramsCache.save(entity.getParamKey(), entity.getParamValue());
    }

    @Override
    public void update(ParamsVO vo) {
        ParamsEntity entity = paramsMapper.getById(vo.getId());

        // 如果 参数键 修改过
        if (!entity.getParamKey().equalsIgnoreCase(vo.getParamKey())) {
            // 判断 新参数键 是否存在
            boolean exist = paramsMapper.isExist(vo.getParamKey());
            if (exist) {
                throw new ServerException("参数键已存在");
            }

            // 删除修改前的缓存
            paramsCache.del(entity.getParamKey());
        }

        // 修改数据
        paramsMapper.updateById(ParamsConvert.INSTANCE.convert(vo));

        // 保存到缓存
        paramsCache.save(vo.getParamKey(), vo.getParamValue());
    }

    @Override
    public void delete(List<Long> idList) {
        // 查询列表
        List<ParamsEntity> list = paramsMapper.selectListByWrapper(QueryWrapper.of(ParamsEntity.class)
                .in(ParamsEntity::getId, idList));
        if(!list.isEmpty()){
            for(ParamsEntity entity: list){
                if(entity.getParamType() == 1){
                    throw new ServerException(entity.getParamName() + "是系统内置参数禁止删除");
                }
            }
        }
        // 删除数据
        paramsMapper.updateByWrapper(UpdateWrapper.of(ParamsEntity.class).set(ParamsEntity::getDbStatus, 0)
                .in(ParamsEntity::getId, idList));
        // 删除缓存
        String[] keys = list.stream().map(ParamsEntity::getParamKey).toArray(String[]::new);
        if(keys.length > 0){
            paramsCache.del(keys);
        }
    }

    @Override
    public String getString(String paramKey) {
        String value = paramsCache.get(paramKey);
        if (value == null || value.isEmpty()) {
            // redis为空，则从数据库中获取
            ParamsEntity paramsEntity = this.getByKey(paramKey);
            if(ObjectUtils.isEmpty(paramsEntity)){
                throw new ServerException("参数不能为空，paramKey：" + paramKey);
            }else{
                value = paramsEntity.getParamValue();
            }
        }
        return value;
    }

    @Override
    public String getDefaultString(String paramKey) {
        String value = paramsCache.get(paramKey);
        if (value == null || value.isEmpty()) {
            // redis为空，则从数据库中获取
            ParamsEntity paramsEntity = this.getByKey(paramKey);
            if(ObjectUtils.isEmpty(paramsEntity)){
                return null;
            }else{
                value = paramsEntity.getParamValue();
            }
        }
        return value;
    }

    @Override
    public int getInt(String paramKey) {
        String value = getString(paramKey);
        return Integer.parseInt(value);
    }

    @Override
    public int getDefaultInt(String paramKey) {
        String value = getDefaultString(paramKey);
        if(value == null){
            return -1;
        }
        return Integer.parseInt(value);
    }

    @Override
    public boolean getBoolean(String paramKey) {
        String value = getString(paramKey);
        return Boolean.parseBoolean(value);
    }

    @Override
    public <T> T getJSONObject(String paramKey, Class<T> valueType) {
        String value = getString(paramKey);
        return JsonUtils.parseObject(value, valueType);
    }

    /**
     * 根据key获取数据库中的值
     * @param key  参数Key
     * @return e
     */
    @Override
    public ParamsEntity getByKey(String key) {
        List<ParamsEntity> list = paramsMapper.selectListByWrapper(QueryWrapper.of(ParamsEntity.class)
                .eq(ParamsEntity::getDbStatus,1).eq(ParamsEntity::getParamKey, key));
        if(!list.isEmpty()){
            return list.getFirst();
        }
        return null;
    }

    /**
     *  根据key获取数据库中的值
     * @param keys 参数Key
     * @return 集合
     */
    @Override
    public Map<String, String> getByKeys(List<String> keys) {
        Map<String, String> data = new HashMap<>();
        for(String key: keys){
            String value = getDefaultString(key.toUpperCase());
            if(value != null){
                data.put(key.toUpperCase(), value);
            }
        }
        return data;
    }

    @Override
    public ParamsEntity getById(Long id) {
        return paramsMapper.getById(id);
    }

    @Override
    public Boolean del(Long id) {
        ParamsEntity entity = paramsMapper.getById(id);
        if(entity == null || entity.getParamKey() == null){
            return false;
        }else {
            entity.setDbStatus(0);
            return paramsMapper.updateById(entity) > 0;
        }
    }

    /**
     *
     * @param query
     * @return
     */
    private Wrapper<ParamsEntity> getQueryWrapper(ParamsQuery query){
        if(query == null){
            return QueryWrapper.of(ParamsEntity.class);
        }else{
            return QueryWrapper.of(ParamsEntity.class).eq(ParamsEntity::getParamKey, query.getParamKey())
                    .eq(ParamsEntity::getParamValue, query.getParamValue())
                    .eq(ParamsEntity::getParamType, query.getParamType())
                    .in(ParamsEntity::getId, query.getIdList())
                    .eq(ParamsEntity::getDbStatus, 1)
                    .jointSQL("(param_name like concat('%',#{keyWord}, '%') " +
                            "or param_value like concat('%',#{keyWord}, '%') " +
                            "or param_key like concat('%',#{keyWord}, '%') " +
                            "or remark like concat('%',#{keyWord}, '%'))","keyWord", query.getKeyWord())
                    .page(query.getPageNum(), query.getPageSize());
        }
    }
}