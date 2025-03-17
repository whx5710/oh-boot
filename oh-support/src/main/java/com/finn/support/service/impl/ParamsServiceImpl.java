package com.finn.support.service.impl;

import cn.hutool.core.util.StrUtil;
import com.finn.framework.datasource.mapper.SuperMapper;
import com.finn.framework.service.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.JsonUtils;
import com.finn.support.cache.ParamsCache;
import com.finn.support.mapper.ParamsMapper;
import com.finn.support.query.ParamsQuery;
import com.finn.support.vo.ParamsVO;
import com.finn.support.convert.ParamsConvert;
import com.finn.support.service.ParamsService;
import jakarta.annotation.PostConstruct;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.PageResult;
import com.finn.support.entity.ParamsEntity;
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

    public ParamsServiceImpl(ParamsCache paramsCache, ParamsMapper paramsMapper, SuperMapper<ParamsEntity> superMapper){
        super(superMapper);
        this.paramsCache = paramsCache;
        this.paramsMapper = paramsMapper;
    }

    @PostConstruct
    public void init() {
        // 查询列表
        List<ParamsEntity> list = paramsMapper.getList(null);

        // 保存到缓存
        paramsCache.saveList(list);
    }

    @Override
    public PageResult<ParamsVO> page(ParamsQuery query) {
        Page<ParamsEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<ParamsEntity> list = paramsMapper.getList(query);
        return new PageResult<>(ParamsConvert.INSTANCE.convertList(list), page.getTotal());
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

        paramsMapper.save(entity);

        // 保存到缓存
        paramsCache.save(entity.getParamKey(), entity.getParamValue());
    }

    @Override
    public void update(ParamsVO vo) {
        ParamsEntity entity = paramsMapper.getById(vo.getId());

        // 如果 参数键 修改过
        if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), vo.getParamKey())) {
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
        ParamsQuery query = new ParamsQuery();
        query.setIdList(idList);
        List<ParamsEntity> list = paramsMapper.getList(query);

        // 删除数据
        idList.forEach(id -> {
            ParamsEntity param = new ParamsEntity();
            param.setDbStatus(0);
            param.setId(id);
            paramsMapper.updateById(param);
        });

        // 删除缓存
        String[] keys = list.stream().map(ParamsEntity::getParamKey).toArray(String[]::new);
        paramsCache.del(keys);
    }

    @Override
    public String getString(String paramKey) {
        String value = paramsCache.get(paramKey);
        if (StrUtil.isBlank(value)) {
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
        if (StrUtil.isBlank(value)) {
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
        ParamsQuery query = new ParamsQuery();
        query.setParamKey(key);
        List<ParamsEntity> list = paramsMapper.getList(query);
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

}