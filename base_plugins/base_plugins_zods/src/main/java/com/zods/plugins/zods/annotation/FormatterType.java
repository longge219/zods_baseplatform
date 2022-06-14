package com.zods.plugins.zods.annotation;

import com.zods.plugins.zods.annotation.resolver.format.FormatterEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormatterType {
    FormatterEnum type() default FormatterEnum.OBJECT;
}
