package com.finn.support.cache;

import com.finn.core.cache.RedisCache;
import com.finn.support.vo.SysDictDataSingleVO;
import com.finn.support.vo.SysDictVO;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 字典数据缓存
 * 2024-12-27 18:50:11
 * @author 王小费 whx5710@qq.com
 */
@Service
public class SysDictCache {
    private final RedisCache redisCache;

    /**
     * 字典管理 KEY
     */
    private final String SYSTEM_DICT_KEY = "system:dict:list:";

    private final String SYSTEM_DICT_SINGLE_KEY = "system:dict:key:";

    public SysDictCache(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 保存字典列表
     * @param list
     */
    public void saveList(List<SysDictVO> list) {
        redisCache.deleteAll(SYSTEM_DICT_KEY);
        if(list == null || list.size() == 0){
            return;
        }
        for(SysDictVO vo: list){
            redisCache.set(SYSTEM_DICT_KEY + vo.getDictType(), vo.getDataList(), RedisCache.NOT_EXPIRE);
        }
    }

    /**
     * 逐一保存单个
     * @param vo
     */
    public void save(SysDictDataSingleVO vo){
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
    public SysDictDataSingleVO get(String dictType, String value){
        String key = SYSTEM_DICT_SINGLE_KEY + dictType + ":" + value;
        if(redisCache.hasKey(key)){
            return (SysDictDataSingleVO) redisCache.get(key);
        }else {
            return new SysDictDataSingleVO();
        }
    }

    /**
     * 删除所有
     */
    public void delSingleAll(){
        redisCache.deleteAll(SYSTEM_DICT_SINGLE_KEY);
    }

}
