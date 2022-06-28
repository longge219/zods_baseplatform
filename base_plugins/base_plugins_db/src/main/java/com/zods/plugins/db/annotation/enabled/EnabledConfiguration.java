package com.zods.plugins.db.annotation.enabled;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ConfigurationImportSelector.class})
/**
 * @author jianglong
 * @version 1.0
 * @Description 是否可用注解配置
 * @createDate 2022-06-20
 */
public @interface EnabledConfiguration {
}
