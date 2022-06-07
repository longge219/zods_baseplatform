package com.zods.mqtt.sever.business.response;


import java.io.Serializable;


public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = 3644322851500370651L;

    private Integer result = HandlerExceptionEnums.SUCCESS.code;

    private String message = HandlerExceptionEnums.SUCCESS.message;

    private T data;

    public ResponseModel(HandlerExceptionEnums exceptionEnums) {
        this.result = exceptionEnums.getCode();
        this.message = exceptionEnums.getMessage();
    }

    public ResponseModel(HandlerExceptionEnums exceptionEnums, T data) {
        this.result = exceptionEnums.getCode();
        this.message = exceptionEnums.getMessage();
        this.data = data;
    }

    public ResponseModel(Integer result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public ResponseModel(Integer result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseModel(T data) {
        this.data = data;
    }

    public ResponseModel() {
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
