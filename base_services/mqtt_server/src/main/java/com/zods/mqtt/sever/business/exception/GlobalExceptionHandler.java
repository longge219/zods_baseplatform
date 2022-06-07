package com.zods.mqtt.sever.business.exception;
import com.zods.mqtt.sever.business.response.HandlerExceptionEnums;
import com.zods.mqtt.sever.business.response.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * 全局异常处理.
 */
@ControllerAdvice(basePackages = "com.zods")
@ResponseBody
public class GlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 自定义业务异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(XcHandlerException.class)
    public ResponseModel XcHandlerExceptionResponse(XcHandlerException exception) {
        log.error("定义业务异常:{}", exception.getMessage(), exception);
        return new ResponseModel(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(XcException.class)
    public ResponseModel XcHandlerExceptionResponse(XcException exception, HttpServletResponse response, HttpServletRequest request) {
        log.error("全局异常:{}", exception.getMessage(), exception);
        return new ResponseModel(exception.getErrorCode(), exception.getMessage());
    }

    /**
     * 400 全局参数校验异常处理器
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public ResponseModel handleValidationException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            List<ObjectError> objectError = validException.getBindingResult().getAllErrors();
            return validateParameterError(objectError);
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> objectError = bindException.getBindingResult().getAllErrors();
            return validateParameterError(objectError);
        }
        return new ResponseModel(HandlerExceptionEnums.PARAMETER_ERROR);
    }

    private ResponseModel validateParameterError(List<ObjectError> objectError) {
        String errors = "";
        for (int i = 0; i < objectError.size(); i++) {
            errors += objectError.get(i).getDefaultMessage() + ",";
        }
        return new ResponseModel(HandlerExceptionEnums.PARAMETER_ERROR.code, errors);
    }


    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseModel handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        return new ResponseModel(HandlerExceptionEnums.PARAMETER_ERROR.code, "参数解析失败");
    }

    /**
     * 405 - 不支持当前请求方法
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseModel handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return new ResponseModel(HandlerExceptionEnums.METHOD_NOT_ALLOWED.code, "不支持当前请求方法");
    }

    /**
     * 415 - 不支持当前媒体类型
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseModel handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("不支持当前媒体类型", e);
        return new ResponseModel(HandlerExceptionEnums.UNSUPPORTED_MEDIA_TYPE.code, "不支持当前媒体类型");
    }

    /**
     * 500 - sql异常
     */
    @ExceptionHandler({SQLException.class})
    public ResponseModel handleSQLException(Exception e) {
        log.error("服务运行SQLException异常", e);
        return new ResponseModel(HandlerExceptionEnums.ERROR.code, "执行数据库语句时出错");
    }

    /**
     * 500 - 网络服务异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseModel handleException(Exception e) {
        log.error("服务运行异常", e);
        String message = e.getMessage();
        message = message.toLowerCase();
        /*检测是否为操作数据库异常，若是则不能把语句内容提示出来。*/
        if (message.indexOf("update") != -1 || message.indexOf("insert") != -1 || message.indexOf("delete") != -1 || message.indexOf("select") != -1) {
            message = "可能是执行数据库语句时出错";
        }
        return new ResponseModel(HandlerExceptionEnums.ERROR.code, message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseModel XcHandlerExceptionResponse(RuntimeException exception) {
        if (exception.getCause() instanceof XcHandlerException) {
            log.error("定义业务异常:{}", exception.getMessage(), exception);
            XcHandlerException e = (XcHandlerException) exception.getCause();
            return new ResponseModel(e.getErrorCode(), exception.getMessage());
        }
        log.error("运行异常", exception.getMessage(), exception);
        return new ResponseModel(HandlerExceptionEnums.ERROR.code, "后台服务异常");
    }

}
