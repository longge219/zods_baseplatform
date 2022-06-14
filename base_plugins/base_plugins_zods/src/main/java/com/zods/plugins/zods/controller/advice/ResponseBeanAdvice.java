package com.zods.plugins.zods.controller.advice;

import com.zods.plugins.zods.bean.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseBeanAdvice implements ResponseBodyAdvice<ResponseBean> {
    @Autowired
    private MessageSource messageSource;

    public ResponseBeanAdvice() {
    }

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType() == ResponseBean.class;
    }

    public ResponseBean beforeBodyWrite(ResponseBean responseBean, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (StringUtils.isNotBlank(responseBean.getMessage())) {
            return responseBean;
        } else {
            String code = responseBean.getCode();
            if (StringUtils.isNotBlank(code)) {
                try {
                    String message = this.messageSource.getMessage(code, responseBean.getArgs(), LocaleContextHolder.getLocale());
                    responseBean.setMessage(message);
                } catch (Exception var9) {
                    responseBean.setMessage(code);
                }
            }

            return responseBean;
        }
    }
}
