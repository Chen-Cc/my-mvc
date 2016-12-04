package com.cqf.annotation;

import java.lang.annotation.*;

/**
 * Created by 78544 on 2016/11/24.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {
    String value();
}
