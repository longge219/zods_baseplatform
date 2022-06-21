package com.zods.largescreen.common.annotation;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zods.largescreen.common.annotation.resolver.mask.GaeaMaskJsonSerialize;
import com.zods.largescreen.common.annotation.resolver.mask.MaskEnum;
import java.lang.annotation.*;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
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
