package com.zods.auth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author lawhen
 * @Date 2021/3/10
 */
public class TokenRelayInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        //请求头token传递
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)) {
            requestTemplate.header("Authorization", token);
        } else {
            //请求体token传递
            token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
            if (StringUtils.isNotBlank(token)) {
                requestTemplate.query(OAuth2AccessToken.ACCESS_TOKEN, token);
            }
        }
    }
}
