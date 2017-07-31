package com.rabbitframework.commons.exl.annotations;

import java.lang.annotation.*;

/**
 * Excel表格标识注解
 *
 * @author: justin.liang
 * @date: 2017/2/18 10:07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Exl {
    /**
     * 标题索引
     *
     * @return
     */
    int titleIndex() default 0;

    /**
     * 工作表索引
     *
     * @return
     */
    int sheetIndex() default 0;
}
