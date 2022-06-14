package com.zods.plugins.zods.exception.advice;
import com.zods.plugins.zods.bean.ResponseBean;
import com.zods.plugins.zods.exception.BusinessException;
import com.zods.plugins.zods.i18.MessageSourceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    @Autowired
    private MessageSourceHolder messageSourceHolder;

    public ExceptionHandlerAdvice() {
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseBean handleBusinessException(BusinessException businessException) {
        return ResponseBean.builder().code(businessException.getCode()).args(businessException.getArgs()).build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseBean methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        String code = methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage();

        String message;
        try {
            message = this.messageSourceHolder.getMessage(code, (Object[])null);
        } catch (NoSuchMessageException var5) {
            message = code;
        }

        return ResponseBean.builder().code("500").message(message).build();
    }

    @ExceptionHandler({Exception.class})
    public ResponseBean exception(Exception exception) {
        this.logger.error("系统异常", exception);
        ResponseBean.Builder builder = ResponseBean.builder();
        builder.code("500");
        builder.message(this.messageSourceHolder.getMessage("500", (Object[])null));
        builder.args(new String[]{exception.getMessage()});
        return builder.build();
    }
}
