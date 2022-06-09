package com.zods.auth.auth.permission.handler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import zom.zods.exception.model.ResponseModel;
import zom.zods.exception.util.ResponseUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author Wangchao
 * @version 1.0
 * @Description 权限访问限制
 * @createDate 2020/09/09 14:45
 */
@Slf4j
public class McloudExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        String authException = "没有权限访问该资源";
        log.error(authException, e);
        try {
            JSONObject exceptionJson = JSON.parseObject(JSON.toJSON(e.getCause()).toString());
            authException = exceptionJson.getString("oAuth2ErrorCode");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ResponseUtil.responseJson(httpServletRequest, httpServletResponse,
                new ResponseModel(HttpStatus.FORBIDDEN.value(), authException, null));
    }
}
