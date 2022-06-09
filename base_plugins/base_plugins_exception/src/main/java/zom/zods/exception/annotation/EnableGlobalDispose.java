package zom.zods.exception.annotation;
import org.springframework.context.annotation.Import;
import zom.zods.exception.GlobalDefaultConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author Wangchao
 * @version 1.0
 * @Description 启用全局异常处理注解
 * @createDate 2021/1/14 16:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(GlobalDefaultConfiguration.class)
public @interface EnableGlobalDispose {

}
