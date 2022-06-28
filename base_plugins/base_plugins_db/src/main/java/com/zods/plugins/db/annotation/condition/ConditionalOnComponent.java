package com.zods.plugins.db.annotation.condition;
import org.springframework.context.annotation.Conditional;
import java.lang.annotation.*;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Conditional({OnComponentCondition.class})
public @interface ConditionalOnComponent {

    String value();
}
