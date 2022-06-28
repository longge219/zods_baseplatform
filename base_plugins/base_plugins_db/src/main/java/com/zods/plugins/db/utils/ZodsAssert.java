package com.zods.plugins.db.utils;
import com.zods.plugins.db.exception.BusinessExceptionBuilder;
import org.apache.commons.lang3.StringUtils;
import java.util.Collection;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public abstract class ZodsAssert {
    public ZodsAssert() {
    }

    public static void notNull(Object value, String code, Object... args) {
        if (value == null) {
            throw BusinessExceptionBuilder.build(code, args);
        }
    }

    public static void notEmpty(String value, String code, Object... args) {
        if (StringUtils.isBlank(value)) {
            throw BusinessExceptionBuilder.build(code, args);
        }
    }

    public static void notEmpty(Collection value, String code, Object... args) {
        if (value == null || value.isEmpty()) {
            throw BusinessExceptionBuilder.build(code, args);
        }
    }

    public static void isTrue(boolean value, String code, Object... args) {
        if (value) {
            throw BusinessExceptionBuilder.build(code, args);
        }
    }

    public static void isFalse(boolean value, String code, Object... args) {
        if (!value) {
            throw BusinessExceptionBuilder.build(code, args);
        }
    }
}
