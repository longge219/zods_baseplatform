package com.zods.plugins.db.annotation;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zods.plugins.db.annotation.resolver.mask.MaskEnum;
import com.zods.plugins.db.annotation.resolver.mask.MaskJsonSerialize;
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
@JsonSerialize(using = MaskJsonSerialize.class)
public @interface Mask {

    MaskEnum type() default MaskEnum.COMMON;

    int left() default 0;

    int right() default 0;
}
