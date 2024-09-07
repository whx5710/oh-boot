package com.iris.system.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.common.utils.JsonUtils;
import com.iris.system.base.cache.SysParamsCache;
import com.iris.system.base.mapper.SysParamsMapper;
import com.iris.system.base.query.SysParamsQuery;
import com.iris.system.base.vo.SysParamsVO;
import com.iris.system.base.convert.SysParamsConvert;
import com.iris.system.base.service.SysParamsService;
import jakarta.annotation.PostConstruct;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.entity.SysParamsEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class SysParamsServiceImpl implements SysParamsService {

    private final SysParamsCache sysParamsCache;

    private final SysParamsMapper sysParamsMapper;

    public SysParamsServiceImpl(SysParamsCache sysParamsCache, SysParamsMapper sysParamsMapper){
        this.sysParamsCache = sysParamsCache;
        this.sysParamsMapper = sysParamsMapper;
    }

    @PostConstruct
    public void init() {
        // 查询列表
        List<SysParamsEntity> list = sysParamsMapper.getList(null);

        // 保存到缓存
        sysParamsCache.saveList(list);
    }

    @Override
    public PageResult<SysParamsVO> page(SysParamsQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysParamsEntity> list = sysParamsMapper.getList(query);
        PageInfo<SysParamsEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysParamsConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(SysParamsVO vo) {
        AssertUtils.isBlank(vo.getParamKey(), "参数paramKey");
        vo.setParamKey(vo.getParamKey().toUpperCase());
        // 判断 参数键 是否存在
        boolean exist = sysParamsMapper.isExist(vo.getParamKey());
        if (exist) {
            throw new ServerException("参数键已存在");
        }

        SysParamsEntity entity = SysParamsConvert.INSTANCE.convert(vo);

        sysParamsMapper.save(entity);

        // 保存到缓存
        sysParamsCache.save(entity.getParamKey(), entity.getParamValue());
    }

    @Override
    public void update(SysParamsVO vo) {
        SysParamsEntity entity = sysParamsMapper.getById(vo.getId());

        // 如果 参数键 修改过
        if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), vo.getParamKey())) {
            // 判断 新参数键 是否存在
            boolean exist = sysParamsMapper.isExist(vo.getParamKey());
            if (exist) {
                throw new ServerException("参数键已存在");
            }

            // 删除修改前的缓存
            sysParamsCache.del(entity.getParamKey());
        }

        // 修改数据
        sysParamsMapper.updateById(SysParamsConvert.INSTANCE.convert(vo));

        // 保存到缓存
        sysParamsCache.save(vo.getParamKey(), vo.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 查询列表
        SysParamsQuery query = new SysParamsQuery();
        query.setIdList(idList);
        List<SysParamsEntity> list = sysParamsMapper.getList(query);

        // 删除数据
        idList.forEach(id -> {
            SysParamsEntity param = new SysParamsEntity();
            param.setDbStatus(0);
            param.setId(id);
            sysParamsMapper.updateById(param);
        });

        // 删除缓存
        String[] keys = list.stream().map(SysParamsEntity::getParamKey).toArray(String[]::new);
        sysParamsCache.del(keys);
    }

    @Override
    public String getString(String paramKey) {
        String value = sysParamsCache.get(paramKey);
        if (StrUtil.isBlank(value)) {
            // redis为空，则从数据库中获取
            SysParamsEntity sysParamsEntity = this.getByKey(paramKey);
            if(ObjectUtils.isEmpty(sysParamsEntity)){
                throw new ServerException("参数不能为空，paramKey：" + paramKey);
            }else{
                value = sysParamsEntity.getParamValue();
            }
        }
        return value;
    }

    @Override
    public String getDefaultString(String paramKey) {
        String value = sysParamsCache.get(paramKey);
        if (StrUtil.isBlank(value)) {
            // redis为空，则从数据库中获取
            SysParamsEntity sysParamsEntity = this.getByKey(paramKey);
            if(ObjectUtils.isEmpty(sysParamsEntity)){
                return null;
            }else{
                value = sysParamsEntity.getParamValue();
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
    public SysParamsEntity getByKey(String key) {
        SysParamsQuery query = new SysParamsQuery();
        query.setParamKey(key);
        List<SysParamsEntity> list = sysParamsMapper.getList(query);
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
    public SysParamsEntity getById(Long id) {
        return sysParamsMapper.getById(id);
    }

}