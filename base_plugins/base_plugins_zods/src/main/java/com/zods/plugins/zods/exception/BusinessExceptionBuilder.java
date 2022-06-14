package com.zods.plugins.zods.exception;

public class BusinessExceptionBuilder {
    public BusinessExceptionBuilder() {
    }

    public static BusinessException build(String code) {
        return new BusinessException(code);
    }

    public static BusinessException build(String code, Object... args) {
        return new BusinessException(code, args);
    }
}
