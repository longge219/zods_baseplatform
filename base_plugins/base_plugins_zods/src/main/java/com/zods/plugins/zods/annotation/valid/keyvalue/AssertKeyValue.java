package com.zods.plugins.zods.annotation.valid.keyvalue;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
@Constraint(
        validatedBy = {AssertKeyValueValidator.class}
)
public @interface AssertKeyValue {
    String message() default "key.validation.not.match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dictCode() default "";

    String key() default "";
}
