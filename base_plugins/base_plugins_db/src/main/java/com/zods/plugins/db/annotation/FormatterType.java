package com.zods.plugins.db.annotation;
import com.zods.plugins.db.annotation.resolver.format.FormatterEnum;
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
    /**默认为object对象*/
    FormatterEnum type() default FormatterEnum.OBJECT;
}
