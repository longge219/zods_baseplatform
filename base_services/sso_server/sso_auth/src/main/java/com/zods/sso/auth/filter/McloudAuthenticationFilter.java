package com.zods.sso.auth.filter;
import com.zods.auth.common.wrapper.HttpAuthRequestWrapper;
import com.zods.sso.auth.service.AuthUserService;
import com.zods.sso.auth.util.FilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import zom.zods.exception.enums.HandlerExceptionEnums;
import zom.zods.exception.model.ResponseModel;
import zom.zods.exception.util.ExceptionUtil;
import zom.zods.exception.util.ResponseUtil;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/10/12 19:43
 */
@Slf4j
@Component
@Order(value = Integer.MIN_VALUE)
public class McloudAuthenticationFilter implements Filter {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${isSSO}")
    private boolean isSSO;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AuthUserService authUserService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            // 1.request 包装,处理token
            HttpAuthRequestWrapper requestWrapper = new HttpAuthRequestWrapper((HttpServletRequest) request, false);
            requestWrapper.setSSO(isSSO);
            // 2.若存在token则对其有效性检查
            if (FilterUtil.validateToken(requestWrapper, redisTemplate, response)) {
                return;
            }
            // 3.登录前判断用户的有效性
            requestWrapper = FilterUtil.validateLoginRequest(requestWrapper, contextPath, redisTemplate, authUserService, response);
            if (requestWrapper.isFailedRequest()) {
                return;
            }
            // 4.解析refresh_token请求,刷新前判断是否用户已注销
            requestWrapper = FilterUtil.parseRefreshRequest(requestWrapper, redisTemplate, contextPath, response);
            if (requestWrapper.isFailedRequest()) {
                return;
            }

            chain.doFilter(requestWrapper, response);
        } catch (Throwable e) {
            log.error(applicationName + "throwable exception :" + e.getMessage(), e);
            ResponseUtil.responseJson((HttpServletRequest) request, (HttpServletResponse) response,
                    new ResponseModel(HandlerExceptionEnums.AUTH_ERROR.getCode(),
                            HandlerExceptionEnums.AUTH_ERROR.message + ExceptionUtil.getRealMessage(e)));
        }
    }

}
