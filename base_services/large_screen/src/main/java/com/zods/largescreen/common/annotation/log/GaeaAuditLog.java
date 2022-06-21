package com.zods.largescreen.common.annotation.log;
import java.lang.annotation.*;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GaeaAuditLog {

    String pageTitle() default "";

    boolean isSaveRequestData() default true;

    boolean isSaveResponseData() default false;
}
