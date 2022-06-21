package com.zods.largescreen.common.annotation.valid.keyvalue;
import javax.validation.Constraint;
import javax.validation.Payload;
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
