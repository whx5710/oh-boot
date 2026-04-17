/**
 * MIT License

	Copyright (c) 2024 王小费 whx5710@qq.com

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
 */
package com.finn;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.common.constant.CommConstant;
import com.finn.framework.common.properties.SysDataSourceProperties;
import com.finn.framework.datasource.DynamicDataSource;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.system.cache.DictCache;
import com.finn.system.cache.ParamsCache;
import com.finn.system.cache.UserCache;
import com.finn.system.entity.ParamsEntity;
import com.finn.system.entity.TenantMemberEntity;
import com.finn.system.entity.UserEntity;
import com.finn.system.mapper.*;
import com.finn.system.service.impl.DictTypeServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.server.autoconfigure.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;

/**
 * 应用初始化器<br/>
 * 负责应用启动后的初始化操作,缓存数据
 */
@Component
public class ApplicationInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(ApplicationInitializer.class);

    private final ServerProperties serverProperties;
    private final DynamicDataSource dynamicDataSource;
    private final SysDataSourceProperties sysDataSourceProperties;
    private final ParamsCache paramsCache;
    private final DictCache dictCache;
    private final UserCache userCache;
    private final RedisCache redisCache;

    public ApplicationInitializer(ServerProperties serverProperties, DynamicDataSource dynamicDataSource,
                                  SysDataSourceProperties sysDataSourceProperties, ParamsCache paramsCache,
                                  DictCache dictCache, UserCache userCache, RedisCache redisCache){
        this.serverProperties = serverProperties;
        this.dynamicDataSource = dynamicDataSource;
        this.sysDataSourceProperties = sysDataSourceProperties;
        this.paramsCache = paramsCache;
        this.dictCache = dictCache;
        this.userCache = userCache;
        this.redisCache = redisCache;
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 缓存数据
        this.cacheData();

        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        Integer port = serverProperties.getPort();
        String contextPath = serverProperties.getServlet().getContextPath();
        String baseUrl = String.format("%s://%s:%s%s", "http", hostAddress, port, contextPath);
        log.info("-----------------------------------------------");
        log.info("后台服务启动成功.");
        log.info("API 地址：{}", baseUrl);
        log.info("-----------------------------------------------");
    }
    
    public static void initialize(ConfigurableApplicationContext applicationContext) {
        ApplicationInitializer initializer = applicationContext.getBean(ApplicationInitializer.class);
        try {
            initializer.run(null);
        } catch (Exception e) {
            log.error("initialize 发生错误！", e);
        }
    }

    /**
     * 缓存系统数据
     */
    private void cacheData(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = dynamicDataSource.getSqlSessionFactory(sysDataSourceProperties.getSysDefault());
            sqlSession = sqlSessionFactory.openSession();

            // 缓存参数
            ParamsMapper paramsMapper = sqlSession.getMapper(ParamsMapper.class);
            List<ParamsEntity> paramsList = paramsMapper.listByWrapper(QueryWrapper.of(ParamsEntity.class)
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
            List<UserEntity> list = userMapper.listByWrapper(QueryWrapper.of(UserEntity.class)
                    .eq(UserEntity::getDbStatus, 1));
            userCache.saveList(list);
            log.debug("缓存用户信息！{}", list.size());

            // 缓存租户信息
            TenantMemberMapper tenantMemberMapper = sqlSession.getMapper(TenantMemberMapper.class);
            List<TenantMemberEntity> tenantList = tenantMemberMapper.listByWrapper(QueryWrapper.of(TenantMemberEntity.class)
                    .eq(TenantMemberEntity::getDbStatus, 1).orderBy(TenantMemberEntity::getSort));
            // redisCache.deleteAll(CommConstant.TENANT_PREFIX + "*");
            for(TenantMemberEntity item : tenantList){
                // 以json格式缓存到redis，方便直接读取
                redisCache.set(CommConstant.TENANT_PREFIX + item.getTenantId(), item.toJson());
            }
            log.debug("缓存租户信息！{}", tenantList.size());
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