package com.zods.plugins.db.annotation.log;
import java.lang.annotation.*;
/**
 * @author jianglong
 * @version 1.0
 * @Description 审核日志注解
 * @createDate 2022-06-20
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    String pageTitle() default "";

    boolean isSaveRequestData() default true;

    boolean isSaveResponseData() default false;
}
