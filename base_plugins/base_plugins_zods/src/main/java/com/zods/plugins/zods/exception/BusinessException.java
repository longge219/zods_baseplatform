package com.zods.plugins.zods.exception;

public class BusinessException extends RuntimeException {
    private String code;
    private Object[] args;

    BusinessException(String code) {
        this.code = code;
    }

    public BusinessException(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return this.args != null ? (Object[])this.args.clone() : null;
    }

    public void setArgs(Object[] args) {
        if (args != null) {
            this.args = (Object[])args.clone();
        }

    }
}
