package com.rabbitframework.commons.exl.annotations;

import java.lang.annotation.*;

/**
 * excel列标识
 *
 * @author: justin.liang
 * @date: 2017/2/18 12:34
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExlColumnIndex {
    /**
     * 索引
     *
     * @return
     */
    int index();

    /**
     * 值类型
     *
     * @return
     */
    Class<?> type() default String.class;

}
