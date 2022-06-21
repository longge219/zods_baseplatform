package com.zods.largescreen.common.annotation.condition;
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
@Conditional({OnGaeaComponentCondition.class})
public @interface ConditionalOnGaeaComponent {
    String value();
}
