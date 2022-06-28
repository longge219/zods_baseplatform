package com.zods.plugins.db.annotation.valid.em;
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
@Constraint(validatedBy = {AssertEnumValidator.class})
public @interface AssertEnum {
    String message() default "enum.validation.not.match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class value() default Object.class;
}