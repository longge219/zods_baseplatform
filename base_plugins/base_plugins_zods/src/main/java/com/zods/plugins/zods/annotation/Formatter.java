package com.zods.plugins.zods.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
public @interface Formatter {
    String dictCode() default "";

    String[] replace() default {};

    String targetField() default "";

    String key() default "";
}
