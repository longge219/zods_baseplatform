package com.zods.plugins.db.exception;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
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
