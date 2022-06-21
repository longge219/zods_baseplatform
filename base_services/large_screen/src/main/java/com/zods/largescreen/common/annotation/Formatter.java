package com.zods.largescreen.common.annotation;
import java.lang.annotation.*;
/**
 * @author jianglong
 * @version 1.0
 * @Description 格式化自定义注解
 * @createDate 2022-06-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
public @interface Formatter {

    String dictCode() default "";

    String[] replace() default {};

    String targetField() default "";

    String key() default "";
}
