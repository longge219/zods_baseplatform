package com.zods.mqtt.sever.business.exception;
import java.util.Collection;
/**
 * @description: 断言抛出业务异常
 */
public class BusinessExceptionUtils {
    /**
     * 断言判断是否为空，如果为空，则抛出异常
     *
     * @param obj 对象
     * @param msg 异常消息
     */
    public static void isNull(Object obj, String msg) {
        if (obj == null) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 断言判断字符串是否为空或者空串，如果为空，则抛出异常
     *
     * @param txt 需要校验的字符串
     * @param msg 异常消息
     */
    public static void isBlank(String txt, String msg) {
        if ("".equals(txt)) {
            throw new RuntimeException(msg);
        }
    }

    public static void isNullOrBlank(Object obj, String msg) {
        if (obj == null || "".equals(obj)) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 断言判断集合是否为空，如果为空则抛出异常
     *
     * @param collection 集合
     * @param msg        异常消息
     */
    public static void isEmpty(Collection collection, String msg) {
        if (collection == null || collection.isEmpty()) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 断言判断参数是否为true，如果为false则抛出异常
     *
     * @param isTrue 是否为真
     * @param msg    提示
     */
    public static void isTrue(Boolean isTrue, String msg) {
        if (!isTrue) {
            throw new RuntimeException(msg);
        }
    }

}
