package com.zods.largescreen.common.annotation;
import java.lang.annotation.*;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface HashKey {

    String key();

    String[] replace() default {};
}
