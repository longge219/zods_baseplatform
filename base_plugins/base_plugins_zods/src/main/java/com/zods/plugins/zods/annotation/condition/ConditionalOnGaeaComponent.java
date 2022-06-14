package com.zods.plugins.zods.annotation.condition;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Conditional({OnGaeaComponentCondition.class})
public @interface ConditionalOnGaeaComponent {
    String value();
}
