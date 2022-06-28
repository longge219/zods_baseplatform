package com.zods.plugins.db.curd.controller;
import com.zods.plugins.db.bean.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author jianglong
 * @version 1.0
 * @Description 基础Controller--提供基础的请求放回封装方法
 * @createDate 2022-06-20
 */
public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseController() {
    }

    public ResponseBean responseSuccess() {
        return ResponseBean.builder().build();
    }

    public ResponseBean responseSuccess(String code, Object... args) {
        return ResponseBean.builder().code(code).args(args).build();
    }

    public ResponseBean responseSuccessWithData(Object data) {
        return ResponseBean.builder().data(data).build();
    }

    public ResponseBean responseSuccessWithData(String code, Object data, Object... args) {
        return ResponseBean.builder().code(code).data(data).args(args).build();
    }

    public ResponseBean failure() {
        return ResponseBean.builder().code("500").build();
    }

    public ResponseBean failure(String code, Object... args) {
        return ResponseBean.builder().code(code).args(args).build();
    }

    public ResponseBean failureWithData(String code, Object data, Object... args) {
        return ResponseBean.builder().code(code).args(args).data(data).build();
    }
}
