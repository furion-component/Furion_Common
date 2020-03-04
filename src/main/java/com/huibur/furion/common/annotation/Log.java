package com.huibur.furion.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*******************************************************
 * Copyright(c)2019-2020 HuiBur. All rights reserved.
 * Header: Log.java
 * Discussion: log annotation
 * Create Date: 2019/6/6
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String value() default "";
}
