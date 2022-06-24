package com.zods.largescreen.common.annotation;
import com.zods.largescreen.common.constant.QueryEnum;
import java.lang.annotation.*;
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

    //查询条件默认为true
    boolean where() default true;

    //列
    String column() default "";
}
