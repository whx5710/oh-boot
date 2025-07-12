package com.finn.support.cache;

import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.support.vo.DictDataSingleVO;
import com.finn.support.vo.DictVO;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 字典数据缓存
 * 2024-12-27 18:50:11
 * @author 王小费 whx5710@qq.com
 */
@Service
public class DictCache {
    private final RedisCache redisCache;

    /**
     * 字典管理 KEY
     */
    private final String SYSTEM_DICT_KEY = RedisKeys.PREFIX +  "dict:list:";

    private final String SYSTEM_DICT_SINGLE_KEY = RedisKeys.PREFIX + "dict:key:";

    public DictCache(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 保存字典列表
     * @param list
     */
    public void saveList(List<DictVO> list) {
        redisCache.deleteAll(SYSTEM_DICT_KEY);
        if(list == null || list.size() == 0){
            return;
        }
        for(DictVO vo: list){
            redisCache.set(SYSTEM_DICT_KEY + vo.getDictType(), vo.getDataList(), RedisCache.NOT_EXPIRE);
        }
    }

    /**
     * 逐一保存单个
     * @param vo
     */
    public void save(DictDataSingleVO vo){
        String key = SYSTEM_DICT_SINGLE_KEY + vo.getDictType() + ":" + vo.getDictValue();
        redisCache.delete(key);
        redisCache.set(key, vo, RedisCache.NOT_EXPIRE);
    }

    /**
     * 根据类型和值获取对象
     * @param dictType
     * @param value
     * @return
     */
    public DictDataSingleVO get(String dictType, String value){
        String key = SYSTEM_DICT_SINGLE_KEY + dictType + ":" + value;
        if(redisCache.hasKey(key)){
            return (DictDataSingleVO) redisCache.get(key);
        }else {
            return new DictDataSingleVO();
        }
    }

    /**
     * 删除所有
     */
    public void delSingleAll(){
        redisCache.deleteAll(SYSTEM_DICT_SINGLE_KEY);
    }

}
