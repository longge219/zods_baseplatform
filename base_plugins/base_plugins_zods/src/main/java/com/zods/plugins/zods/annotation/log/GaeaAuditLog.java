package com.zods.plugins.zods.annotation.log;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GaeaAuditLog {
    String pageTitle() default "";

    boolean isSaveRequestData() default true;

    boolean isSaveResponseData() default false;
}
