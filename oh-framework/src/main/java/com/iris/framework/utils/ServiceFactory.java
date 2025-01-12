package com.iris.framework.utils;

import com.iris.core.exception.ServerException;
import com.iris.core.utils.AssertUtils;
import com.iris.framework.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务类 注册服务和获取服务
 * 注册的服务编号唯一;根据上下文获取bean
 * @author 王小费 whx5710@qq.com
 */
public class ServiceFactory {

    // 上下文
    private static ApplicationContext ac;

    private final static Logger log = LoggerFactory.getLogger(ServiceFactory.class);
    // 保存服务类
    private static final Map<String, JobService> serviceMap = new ConcurrentHashMap<String, JobService>();
    // 描述
    private static final Map<String, String> serviceNote = new ConcurrentHashMap<>();

    /**
     * 根据指令，获取消息处理服务
     * @param funcCode 消息指令
     * @return Optional
     */
    public static Optional<JobService> getService(String funcCode){
        Optional<JobService> optionalService = Optional.ofNullable(serviceMap.get(funcCode));
        if(optionalService.isPresent()){
            return optionalService;
        }else{
            throw new ServerException("找不到服务【"+funcCode+"】");
        }
    }

    /**
     * 注册消息处理服务
     * @param funcCode 指令
     * @param jobService 消息服务
     */
    public static void register(String funcCode , JobService jobService) {
        register(funcCode, jobService, null);
    }

    /**
     * 注册消息处理服务
     * @param funcCode 指令
     * @param jobService 消息服务
     * @param note 描述
     */
    public static void register(String funcCode , JobService jobService, String note) {
        AssertUtils.isBlank(funcCode, "注册的服务编码不能为空");
        if(serviceMap.containsKey(funcCode)){
            log.warn("服务编号【{} {}: {}】已存在，已注册 {}，请检查！", funcCode, serviceNote.get(funcCode), serviceMap.get(funcCode).getClass().getName(), jobService.getClass().getName());
        }else{
            serviceMap.put(funcCode, jobService);
            note = note==null?"":note;
            serviceNote.put(funcCode, note);
            log.debug("注册服务:编码【{}】 {}", funcCode, note);
        }
    }

    /**
     * 获取服务名
     * @return map
     */
    public static Map<String, String> getServiceNames(){
        return serviceNote;
    }

    /**
     * 获取服务描述
     * @param funcCode 服务ID
     * @return 描述
     */
    public static String getServiceNote(String funcCode){
        return serviceNote.get(funcCode);
    }

    /**
     * 根据beanName获取java bean
     * @param beanName 名
     * @param clazz 类
     * @return class
     * @param <T> t
     */
    public static <T>  T getBean(String beanName, Class<T> clazz) {
        return ac.getBean(beanName, clazz);
    }

    /**
     * 设置上下文
     * @param applicationContext a
     */
    public static void setApplicationContext(ApplicationContext applicationContext){
        ac = applicationContext;
    }
}
