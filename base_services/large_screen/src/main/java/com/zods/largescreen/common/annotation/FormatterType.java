package com.zods.largescreen.common.annotation;
import com.zods.largescreen.common.annotation.resolver.format.FormatterEnum;
import java.lang.annotation.*;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormatterType {
    FormatterEnum type() default FormatterEnum.OBJECT;
}
