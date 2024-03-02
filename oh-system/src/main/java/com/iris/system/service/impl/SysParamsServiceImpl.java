package com.iris.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.system.convert.SysParamsConvert;
import jakarta.annotation.PostConstruct;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.system.cache.SysParamsCache;
import com.iris.system.dao.SysParamsDao;
import com.iris.system.entity.SysParamsEntity;
import com.iris.system.query.SysParamsQuery;
import com.iris.system.service.SysParamsService;
import com.iris.system.vo.SysParamsVO;
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
public class SysParamsServiceImpl extends BaseServiceImpl<SysParamsDao, SysParamsEntity> implements SysParamsService {

    private final SysParamsCache sysParamsCache;

    private final ObjectMapper objectMapper;

    public SysParamsServiceImpl(SysParamsCache sysParamsCache, ObjectMapper objectMapper){
        this.sysParamsCache = sysParamsCache;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        // 查询列表
        List<SysParamsEntity> list = baseMapper.selectList(null);

        // 保存到缓存
        sysParamsCache.saveList(list);
    }

    @Override
    public PageResult<SysParamsVO> page(SysParamsQuery query) {
        IPage<SysParamsEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<>(SysParamsConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysParamsEntity> getWrapper(SysParamsQuery query) {
        LambdaQueryWrapper<SysParamsEntity> wrapper = Wrappers.lambdaQuery();

        wrapper.like(StrUtil.isNotBlank(query.getParamKey()), SysParamsEntity::getParamKey, query.getParamKey());
        wrapper.eq(StrUtil.isNotBlank(query.getParamValue()), SysParamsEntity::getParamValue, query.getParamValue());
        wrapper.eq(query.getParamType() != null, SysParamsEntity::getParamType, query.getParamType());
        wrapper.orderByDesc(SysParamsEntity::getId);

        return wrapper;
    }

    @Override
    public void save(SysParamsVO vo) {
        AssertUtils.isBlank(vo.getParamKey(), "参数paramKey");
        vo.setParamKey(vo.getParamKey().toUpperCase());
        // 判断 参数键 是否存在
        boolean exist = baseMapper.isExist(vo.getParamKey());
        if (exist) {
            throw new ServerException("参数键已存在");
        }

        SysParamsEntity entity = SysParamsConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);

        // 保存到缓存
        sysParamsCache.save(entity.getParamKey(), entity.getParamValue());
    }

    @Override
    public void update(SysParamsVO vo) {
        SysParamsEntity entity = baseMapper.selectById(vo.getId());

        // 如果 参数键 修改过
        if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), vo.getParamKey())) {
            // 判断 新参数键 是否存在
            boolean exist = baseMapper.isExist(vo.getParamKey());
            if (exist) {
                throw new ServerException("参数键已存在");
            }

            // 删除修改前的缓存
            sysParamsCache.del(entity.getParamKey());
        }

        // 修改数据
        updateById(SysParamsConvert.INSTANCE.convert(vo));

        // 保存到缓存
        sysParamsCache.save(vo.getParamKey(), vo.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 查询列表
        List<SysParamsEntity> list = baseMapper.selectBatchIds(idList);

        // 删除数据
        removeByIds(idList);

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
        return objectMapper.convertValue(value, valueType);
    }

    /**
     * 根据key获取数据库中的值
     * @param key  参数Key
     * @return e
     */
    @Override
    public SysParamsEntity getByKey(String key) {
        LambdaQueryWrapper<SysParamsEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysParamsEntity::getParamKey, key).eq(SysParamsEntity::getDeleted, 0);
        return this.baseMapper.selectOne(wrapper);
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

}