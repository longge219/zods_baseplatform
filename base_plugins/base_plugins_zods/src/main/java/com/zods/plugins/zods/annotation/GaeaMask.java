package com.zods.plugins.zods.annotation;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zods.plugins.zods.annotation.resolver.mask.GaeaMaskJsonSerialize;
import com.zods.plugins.zods.annotation.resolver.mask.MaskEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
@JacksonAnnotationsInside
@JsonSerialize(
        using = GaeaMaskJsonSerialize.class
)
public @interface GaeaMask {
    MaskEnum type() default MaskEnum.COMMON;

    int left() default 0;

    int right() default 0;
}
