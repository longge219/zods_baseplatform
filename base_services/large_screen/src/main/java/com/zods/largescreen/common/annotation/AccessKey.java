package com.zods.largescreen.common.annotation;
import java.lang.annotation.*;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessKey {
    String key() default "id";
}
