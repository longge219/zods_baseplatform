package com.zods.largescreen.common.annotation.enabled;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({GaeaConfigurationImportSelector.class})
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public @interface EnabledGaeaConfiguration {
}
