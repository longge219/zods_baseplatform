package com.zods.plugins.zods.utils;

import java.util.Collection;

import com.zods.plugins.zods.exception.BusinessExceptionBuilder;
import org.apache.commons.lang3.StringUtils;

public abstract class GaeaAssert {
    public GaeaAssert() {
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
