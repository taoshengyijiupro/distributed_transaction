package com.xlinclass.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/24 16:05
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XlTransactional {

    boolean isStart() default false;

    boolean isEnd() default false;
}
