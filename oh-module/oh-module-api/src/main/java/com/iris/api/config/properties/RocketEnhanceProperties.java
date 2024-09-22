//package com.iris.api.config.properties;
//
//import com.iris.framework.common.constant.Constant;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
///**
// * rokcetmq 增强属性
// */
//@Component
//@ConfigurationProperties(prefix = "rocketmq.enhance")
//public class RocketEnhanceProperties {
//
//    // 是否启动时监听
//    private boolean autoListener;
//
//    // 启动隔离，用于激活配置类EnvironmentIsolationConfig,启动后会自动在topic上拼接激活的配置文件，达到自动隔离的效果
//    private boolean enabledIsolation;
//
//    // 隔离环境名称，拼接到topic后,如 topic_dev
//    private String environment;
//
//    public boolean isAutoListener() {
//        return autoListener;
//    }
//
//    public void setAutoListener(boolean autoListener) {
//        this.autoListener = autoListener;
//    }
//
//    public boolean isEnabledIsolation() {
//        return enabledIsolation;
//    }
//
//    public void setEnabledIsolation(boolean enabledIsolation) {
//        this.enabledIsolation = enabledIsolation;
//    }
//
//    public String getEnvironment() {
//        return environment;
//    }
//
//    public void setEnvironment(String environment) {
//        this.environment = environment;
//    }
//
//    /**
//     * 默认主题
//     * @return str
//     */
//    public String defaultTopic(){
//        if(isEnabledIsolation() && StringUtils.hasText(getEnvironment())){
//            return Constant.TOPIC_SUBMIT + "_" + getEnvironment();
//        }else{
//            return Constant.TOPIC_SUBMIT;
//        }
//    }
//}
