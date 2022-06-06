package com.zods.mysql.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 在方法上打上 @Log("log的描述")
 * 在控制台看到对应数据,后面改为入库操作
 * @createDate 2021/2/4 15:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 增1 删2 改3 查4 登陆5 登出6 导入7 导出8， 默认0
     *
     * @return
     */
    int value() default 0;

    /**
     * 日志类型  所属系统：2-认证中心，3-用戶注冊中心，4-运营中心，5-物联网平台，6-监测管理平台
     *
     * @return
     */
    int type() default 0;

    /**
     * 接口的描述
     */
    String description() default "XXX方法";
}
