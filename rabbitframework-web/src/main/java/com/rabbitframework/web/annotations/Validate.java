package com.rabbitframework.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface Validate {
	/**
	 * 验证前端必须提交的参数,作为hibernate validate的功能补充
	 * @return
	 */
	String value() default "";
}
