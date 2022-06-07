package com.zods.mqtt.sever.business.exception;


/**
 * 系统业务处理异常
 */
public class XcHandlerException extends XcException {
    private static final long serialVersionUID = -5600078259171877139L;

    public XcHandlerException(String message) {
        super(message);
    }

    public XcHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public XcHandlerException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums);
    }

    public XcHandlerException(ExceptionEnums exceptionEnums, Throwable cause) {
        super(exceptionEnums, cause);
    }

    public XcHandlerException(String message, int errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }

    public XcHandlerException(String message, int errorCode) {
        super(message, errorCode);
    }


}
