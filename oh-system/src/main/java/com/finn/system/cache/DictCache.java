package com.finn.system.cache;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.system.vo.DictDataVO;
import com.finn.system.vo.DictVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 字典数据缓存
 * 2024-12-27 18:50:11
 * @author 王小费 whx5710@qq.com
 */
@Service
public class DictCache {
    private final RedisCache redisCache;

    private final String SYSTEM_DICT_SINGLE_KEY = RedisKeys.PREFIX + "dict:key:";
    String SYSTEM_DICT_KEY = RedisKeys.PREFIX + "dict:list:";

    public DictCache(RedisCache redisCache){
        this.redisCache = redisCache;
    }

    /**
     * 保存list和单个值
     * @param vo v
     */
    public void save(DictVO vo){
        // 删除list
        redisCache.deleteAll(SYSTEM_DICT_KEY + vo.getDictType());
        // 删除key
        redisCache.delete(SYSTEM_DICT_SINGLE_KEY + vo.getDictType() + ":*");
        if(vo.getDataList() != null){
            // 存list
            redisCache.set(SYSTEM_DICT_KEY + vo.getDictType(), vo.getDataList(), RedisCache.NOT_EXPIRE);
            // 存key 单个
            if(!vo.getDataList().isEmpty()){
                for(DictDataVO dictData: vo.getDataList()){
                    redisCache.set(SYSTEM_DICT_SINGLE_KEY + vo.getDictType() + ":" + dictData.getDictValue(), dictData, RedisCache.NOT_EXPIRE);
                }
            }
        }
    }

    /**
     * 保存字典列表
     * @param list
     */
    public void saveAllList(List<DictVO> list) {
        if(list == null || list.isEmpty()){
            return;
        }
        for(DictVO vo: list){
            this.save(vo);
        }
    }

    /**
     * 根据类型和值获取对象
     * @param dictType
     * @param value
     * @return
     */
    public DictDataVO get(String dictType, String value){
        String key = SYSTEM_DICT_SINGLE_KEY + dictType + ":" + value;
        if(redisCache.hasKey(key)){
            return (DictDataVO) redisCache.get(key);
        }else {
            return new DictDataVO();
        }
    }

    /**
     * 获取字典列表
     * @param dictType 字典类型编码
     * @return list
     */
    public List<DictDataVO> getListData(String dictType){
        String key = SYSTEM_DICT_KEY + dictType ;
        if(redisCache.hasKey(key)){
            return (List<DictDataVO>) redisCache.get(key);
        }else {
            return new ArrayList<>();
        }
    }

}
