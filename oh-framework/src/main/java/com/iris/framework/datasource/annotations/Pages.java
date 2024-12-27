package com.iris.framework.datasource.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页注解：在Mapper中，注解到具体查询方法上，自动执行 PageHelper.startPage，
 * 查询的参数必须继承 PageFilter，查询后会返回数据总量(total)<br/>
 * 返回值可用List 或 Page 接收
 * @author 王小费 whx5710@qq.com
 * 2024年12月08日 10:34
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pages {

}
