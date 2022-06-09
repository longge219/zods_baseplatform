package zom.zods.exception.exception;
import com.netflix.client.ClientException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;
import zom.zods.exception.annotation.IgnoreResponseAdvice;
import zom.zods.exception.enums.ExceptionEnums;
import zom.zods.exception.enums.HandlerExceptionEnums;
import zom.zods.exception.exception.category.McloudHandlerException;
import zom.zods.exception.model.ResponseModel;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 基础全局异常处理
 * @createDate 2021/1/14 16:38
 */
@Slf4j
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    /**
     * 检测是否为操作数据库异常，若是则不能把语句内容提示出来
     */
    private final String UPDATE = "update";
    private final String INSERT = "insert";
    private final String DELETE = "delete";
    private final String SELECT = "select";

    /**
     * 自定义业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(McloudHandlerException.class)
    public ResponseModel xcHandlerExceptionResponse(McloudHandlerException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(McloudHandlerException.class, e.getExceptionEnums(), e);
        return new ResponseModel(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseModel xcBaseException(BaseException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(McloudHandlerException.class, e.getExceptionEnums(), e);
        return new ResponseModel(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseModel xcBaseException(HttpClientErrorException e) throws Throwable {
        errorDispose(e);
        if (e.getRawStatusCode() == HttpStatus.BAD_REQUEST.value()) {
            if (e.getMessage().contains("invalid_grant") && e.getMessage().contains("Bad credentials")) {
                return new ResponseModel(HandlerExceptionEnums.AUTH_PASS_NOT_INCORE);
            }
        }
         outPutErrorWarn(McloudHandlerException.class, HandlerExceptionEnums.AUTH_PASS_NOT_INCORE, e);
        return new ResponseModel(e.getMessage());
    }

    /**
     * NoHandlerFoundException 404 异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseModel handlerNoHandlerFoundException(NoHandlerFoundException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(NoHandlerFoundException.class, HandlerExceptionEnums.NOT_FOUND, e);
        return new ResponseModel(HandlerExceptionEnums.NOT_FOUND);
    }

    /**
     * HttpRequestMethodNotSupportedException 405 异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseModel handlerHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(HttpRequestMethodNotSupportedException.class, HandlerExceptionEnums.METHOD_NOT_ALLOWED, e);
        return new ResponseModel(HandlerExceptionEnums.METHOD_NOT_ALLOWED);
    }

    /**
     * HttpMediaTypeNotSupportedException 415 异常处理
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseModel handlerHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(HttpMediaTypeNotSupportedException.class, HandlerExceptionEnums.UNSUPPORTED_MEDIA_TYPE, e);
        return new ResponseModel(HandlerExceptionEnums.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Exception 类捕获 500 异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResponseModel handlerException(Exception e) throws Throwable {
        errorDispose(e);

        String message = e.getMessage();
        message = message.toLowerCase();
        /*检测是否为操作数据库异常，若是则不能把语句内容提示出来。*/
        if (message.indexOf(UPDATE) != -1 || message.indexOf(INSERT) != -1 || message.indexOf(DELETE) != -1 || message.indexOf(SELECT) != -1) {
            message = "可能是执行数据库语句时出错";
            return new ResponseModel(HandlerExceptionEnums.EXCEPTION.code, message);
        }

        return ifDepthExceptionType(e);
    }

    /**
     * 500 - sql异常
     */
    @ExceptionHandler({SQLException.class})
    public ResponseModel handleSQLException(SQLException e) throws Throwable {
        errorDispose(e);
        outPutErrorWarn(HttpMediaTypeNotSupportedException.class, HandlerExceptionEnums.SQL_ERROR, e);
        return new ResponseModel(HandlerExceptionEnums.SQL_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseModel xcRuntimeException(RuntimeException e) throws Throwable {
        errorDispose(e);
        if (e.getCause() instanceof McloudHandlerException) {
            log.error("定义业务异常:{}", e.getMessage(), e);
            McloudHandlerException xcE = (McloudHandlerException) e.getCause();
            return new ResponseModel(xcE.getErrorCode(), xcE.getMessage());
        }
        outPutError(FeignException.class, HandlerExceptionEnums.EXCEPTION, e);
        return new ResponseModel(HandlerExceptionEnums.EXCEPTION.code, e.getCause().getMessage());
    }

    /**
     * 二次深度检查错误类型
     */
    private ResponseModel ifDepthExceptionType(Throwable throwable) throws Throwable {
        Throwable cause = throwable.getCause();
        if (cause instanceof ClientException) {
            return handlerClientException((ClientException) cause);
        }
        if (cause instanceof FeignException) {
            return handlerFeignException((FeignException) cause);
        }
        outPutError(Exception.class, HandlerExceptionEnums.EXCEPTION, throwable);
        return new ResponseModel(HandlerExceptionEnums.EXCEPTION);
    }

    /**
     * FeignException 类捕获
     */
    @ExceptionHandler(value = FeignException.class)
    public ResponseModel handlerFeignException(FeignException e) throws Throwable {
        errorDispose(e);
        outPutError(FeignException.class, HandlerExceptionEnums.RPC_ERROR, e);
        return new ResponseModel(HandlerExceptionEnums.RPC_ERROR);
    }

    /**
     * ClientException 类捕获
     */
    @ExceptionHandler(value = ClientException.class)
    public ResponseModel handlerClientException(ClientException e) throws Throwable {
        errorDispose(e);
        outPutError(ClientException.class, HandlerExceptionEnums.RPC_ERROR, e);
        return new ResponseModel(HandlerExceptionEnums.RPC_ERROR);
    }

    /**
     * HttpMessageNotReadableException 参数错误异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseModel handleHttpMessageNotReadableException(HttpMessageNotReadableException e) throws Throwable {
        errorDispose(e);
        outPutError(HttpMessageNotReadableException.class, HandlerExceptionEnums.BAD_REQUEST, e);
        String msg = String.format("%s : 错误详情( %s )", HandlerExceptionEnums.BAD_REQUEST.getMessage(),
                e.getRootCause().getMessage());
        return new ResponseModel(HandlerExceptionEnums.BAD_REQUEST.getCode(), msg);
    }

    /**
     * ConstraintViolationException 参数错误异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseModel handleConstraintViolationException(ConstraintViolationException e) throws Throwable {
        errorDispose(e);
        String splatform = "";
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (log.isDebugEnabled()) {
            for (ConstraintViolation error : constraintViolations) {
                log.error("{} -> {}", error.getPropertyPath(), error.getMessageTemplate());
                splatform = error.getMessageTemplate();
            }
        }

        if (constraintViolations.isEmpty()) {
            log.error("validExceptionHandler error fieldErrors is empty");
            new ResponseModel(HandlerExceptionEnums.BUSSINESS_EXCETION.getCode(), "");
        }

        return new ResponseModel(HandlerExceptionEnums.BAD_REQUEST.getCode(), splatform);
    }

    /**
     * MethodArgumentNotValidException 参数错误异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handleMethodArgumentNotValidException(MethodArgumentNotValidException e) throws Throwable {
        errorDispose(e);
        BindingResult bindingResult = e.getBindingResult();
        return getBindResultDTO(bindingResult);
    }

    /**
     * BindException 参数错误异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseModel handleBindException(BindException e) throws Throwable {
        errorDispose(e);
        outPutError(BindException.class, HandlerExceptionEnums.BAD_REQUEST, e);
        BindingResult bindingResult = e.getBindingResult();
        return getBindResultDTO(bindingResult);
    }

    private ResponseModel getBindResultDTO(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (log.isDebugEnabled()) {
            for (FieldError error : fieldErrors) {
                log.error("{} -> {}", error.getDefaultMessage(), error.getDefaultMessage());
            }
        }

        if (fieldErrors.isEmpty()) {
            log.error("validExceptionHandler error fieldErrors is empty");
            new ResponseModel(HandlerExceptionEnums.BUSSINESS_EXCETION.getCode(), "");
        }

        return new ResponseModel(HandlerExceptionEnums.BAD_REQUEST.getCode(), fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * 校验是否进行异常处理
     *
     * @param e   异常
     * @param <T> extends Throwable
     * @throws Throwable 异常
     */
    private <T extends Throwable> void errorDispose(T e) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler");

        // 获取异常 Controller
        Class<?> beanType = handlerMethod.getBeanType();
        // 获取异常方法
        Method method = handlerMethod.getMethod();

        // 判断方法是否存在 IgnoreResponseAdvice 注解
        IgnoreResponseAdvice methodAnnotation = method.getAnnotation(IgnoreResponseAdvice.class);
        if (methodAnnotation != null) {
            // 是否使用异常处理
            if (!methodAnnotation.errorDispose()) {
                throw e;
            } else {
                return;
            }
        }
        // 判类是否存在 IgnoreResponseAdvice 注解
        IgnoreResponseAdvice classAnnotation = beanType.getAnnotation(IgnoreResponseAdvice.class);
        if (classAnnotation != null) {
            if (!classAnnotation.errorDispose()) {
                throw e;
            }
        }
    }

    public void outPutError(Class errorType, ExceptionEnums secondaryErrorType, Throwable throwable) {
        log.error("[{}] {}: {}", errorType.getSimpleName(), secondaryErrorType, throwable.getMessage(),
                throwable);
    }

    public void outPutErrorWarn(Class errorType, ExceptionEnums secondaryErrorType, Throwable throwable) {
        log.warn("[{}] {}: {}", errorType.getSimpleName(), secondaryErrorType, throwable.getMessage());
    }

}
