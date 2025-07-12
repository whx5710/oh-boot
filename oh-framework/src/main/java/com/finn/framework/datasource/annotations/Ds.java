package com.finn.framework.datasource.annotations;

import java.lang.annotation.*;

/**
 * 切换数据源注解<br/>
 * 建议注解到service层，注解到mapper层时，需注意有没有继承BaseMapper，父类方法不能切换
 * @author 王小费 whx5710@qq.com
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Ds {
    /**
     * 数据源名
     * @return str
     */
    String value();
}
