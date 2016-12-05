package com.cqf.annotation;

import java.lang.annotation.*;

/**
 * Created by 78544 on 2016/11/26.
 */

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface View {
    String value();
}
