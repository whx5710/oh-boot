package com.finn.system.service;

import com.finn.framework.common.properties.SysDataSourceProperties;
import com.finn.framework.datasource.DynamicDataSource;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.system.cache.DictCache;
import com.finn.system.cache.ParamsCache;
import com.finn.system.cache.UserCache;
import com.finn.system.entity.ParamsEntity;
import com.finn.system.entity.UserEntity;
import com.finn.system.mapper.DictDataMapper;
import com.finn.system.mapper.DictTypeMapper;
import com.finn.system.mapper.ParamsMapper;
import com.finn.system.mapper.UserMapper;
import com.finn.system.service.impl.DictTypeServiceImpl;
import jakarta.annotation.PostConstruct;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化缓存数据
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-06-29
 */
@Component
public class InitDataService {
    private final Logger log = LoggerFactory.getLogger(InitDataService.class);

    private final DynamicDataSource dynamicDataSource;
    private final SysDataSourceProperties sysDataSourceProperties;
    private final ParamsCache paramsCache;
    private final DictCache dictCache;
    private final UserCache userCache;

    public InitDataService(DynamicDataSource dynamicDataSource, SysDataSourceProperties sysDataSourceProperties,
                           ParamsCache paramsCache, DictCache dictCache,
                           UserCache userCache){
        this.dynamicDataSource = dynamicDataSource;
        this.sysDataSourceProperties = sysDataSourceProperties;
        this.paramsCache = paramsCache;
        this.dictCache = dictCache;
        this.userCache = userCache;
    }

    @PostConstruct
    public void cacheData(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = dynamicDataSource.getSqlSessionFactory(sysDataSourceProperties.getSysDefault());
            sqlSession = sqlSessionFactory.openSession();

            // 缓存参数
            ParamsMapper paramsMapper = sqlSession.getMapper(ParamsMapper.class);
            List<ParamsEntity> paramsList = paramsMapper.selectListByWrapper(QueryWrapper.of(ParamsEntity.class)
                    .eq(ParamsEntity::getDbStatus, 1));
            paramsCache.saveList(paramsList);
            log.debug("缓存参数{}条", paramsList.size());

            // 缓存数据字典
            DictTypeMapper dictTypeMapper = sqlSession.getMapper(DictTypeMapper.class);
            DictDataMapper dictDataMapper = sqlSession.getMapper(DictDataMapper.class);
            DictTypeServiceImpl dictTypeService = new DictTypeServiceImpl(dictDataMapper, dictTypeMapper, dictCache);
            dictTypeService.refreshTransCache();
            log.debug("缓存数据字典");

            // 缓存用户
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<UserEntity> list = userMapper.selectListByWrapper(QueryWrapper.of(UserEntity.class)
                    .eq(UserEntity::getDbStatus, 1));
            userCache.saveList(list);
            log.debug("缓存用户信息！{}", list.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(sqlSession != null){
                sqlSession.commit();
                sqlSession.clearCache();
                sqlSession.close();// 用完关闭
            }
        }
    }
}
