package com.iris.framework.security.config;

import com.iris.framework.common.config.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 允许访问的资源
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Component
public class PermitResource {

    private final Logger log = LoggerFactory.getLogger(PermitResource.class);

    private final SecurityProperties securityProperties;
    public PermitResource(SecurityProperties securityProperties){
        this.securityProperties = securityProperties;
    }

    /**
     * 指定被 spring security 忽略的URL
     */
    public List<String> getPermitList() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = new Resource[0];
        try {
            resources = resolver.getResources("classpath*:auth.yml");
        } catch (IOException e) {
            log.error("【auth.yml】读取忽略鉴权的配置失败！" + e.getMessage());
            throw new RuntimeException(e);
        }
        String key = "auth.ignore-urls";
        List<String> list = getPropertiesList(key, resources);
        list.addAll(securityProperties.getIgnoreUrls());
        // 去重
        list = list.stream().distinct().collect(Collectors.toList());
        log.info("忽略鉴权的URL总计：{}个 ---> {}", list.size(), list);
        return list;
    }

    private List<String> getPropertiesList(String key, Resource... resources) {
        List<String> list = new ArrayList<>();
        // 解析资源文件
        for (Resource resource : resources) {
            Properties properties = loadYamlProperties(resource);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String tmpKey = StringUtils.substringBefore(entry.getKey().toString(), "[");
                if (tmpKey.equalsIgnoreCase(key)) {
                    list.add(entry.getValue().toString());
                }
            }
        }
        return list;
    }

    private Properties loadYamlProperties(Resource... resources) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resources);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
