package com.finn.framework.tenant;

/**
 * 租户标识信息
 * @author 王小费 whx5710@qq.com
 */
public class TenantContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setTenant(String tenant){
        CONTEXT.set(tenant);
    }

    public static String getTenant(){
        return CONTEXT.get();
    }

    public static void clear(){
        CONTEXT.remove();
    }

}
