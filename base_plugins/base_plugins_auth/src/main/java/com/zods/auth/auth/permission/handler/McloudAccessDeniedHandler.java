package com.zods.auth.auth.permission.handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import zom.zods.exception.model.ResponseModel;
import zom.zods.exception.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 授权异常处理
 * @createDate 2020/09/09 14:45
 */
@Slf4j
public class McloudAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        log.error(e.getMessage(), e);
        ResponseUtil.responseJson(httpServletRequest, httpServletResponse, new ResponseModel(HttpStatus.FORBIDDEN.value(), e.getMessage(), null));
    }
}
