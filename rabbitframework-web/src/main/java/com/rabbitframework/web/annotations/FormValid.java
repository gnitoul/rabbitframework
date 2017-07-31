	package com.rabbitframework.web.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)  
@Documented
@Inherited
public @interface FormValid {
	abstract String type() default "ajax";
}
