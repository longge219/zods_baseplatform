package com.zods.largescreen.common.intercept;
import com.zods.largescreen.common.annotation.AccessKey;
import com.zods.largescreen.common.exception.BusinessExceptionBuilder;
import com.zods.largescreen.common.utils.GaeaUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class AccessKeyInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(AccessKeyInterceptor.class);

    public AccessKeyInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        } else {
            AccessKey accessKey = (AccessKey)((HandlerMethod)handler).getMethodAnnotation(AccessKey.class);
            if (accessKey == null) {
                return true;
            } else {
                String id = request.getParameter(accessKey.key());
                if (StringUtils.isBlank(id)) {
                    Map<String, Object> pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                    if (!CollectionUtils.isEmpty(pathVariables)) {
                        id = pathVariables.get(accessKey.key()) + "";
                    }
                }

                if (StringUtils.isBlank(id)) {
                    throw BusinessExceptionBuilder.build("500", new Object[]{"accessKey校验失败，缺少参数ID"});
                } else {
                    String passKey = request.getParameter("accessKey");
                    if (StringUtils.isBlank(passKey)) {
                        throw BusinessExceptionBuilder.build("500", new Object[]{"accessKey校验失败，缺少参数accessKey"});
                    } else {
                        String realPassKey = GaeaUtils.getPassKey(Long.parseLong(id));
                        if (!StringUtils.equals(passKey, realPassKey)) {
                            throw BusinessExceptionBuilder.build("500", new Object[]{"accessKey校验失败，传入的accessKey参数值有误"});
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
    }
}
