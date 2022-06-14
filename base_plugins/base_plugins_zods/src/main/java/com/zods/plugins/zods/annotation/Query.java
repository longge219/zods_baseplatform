package com.zods.plugins.zods.annotation;
import com.zods.plugins.zods.constant.QueryEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @description 查询注解
 * @author jianglong
 * @date 2022-06-14
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Query {

    //查询判断条件
    QueryEnum value() default QueryEnum.EQ;

    //查询条件
    boolean where() default true;

    //列
    String column() default "";
}
