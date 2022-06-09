package com.zods.auth.common;

import com.zods.auth.model.AuthUser;
import com.zods.auth.model.JwtTokenModel;
import com.zods.auth.util.JwtTokenUtil;
import lombok.SneakyThrows;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 获取登录用户信息
 * @createDate 2020/12/20 14:34
 */
public class LoginUserHolder {

    private static final BearerTokenExtractor bearerTokenExtractor = new BearerTokenExtractor();

    /**
     * 从access_token中获取用户信息
     *
     * @return
     */
    @SneakyThrows
    public static JwtTokenModel currentUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == servletRequestAttributes) {
            return new JwtTokenModel();
        }
        // 先从request中获取token,若token已解析则OK,未解析则在catch中解析后再获取用户信息
        HttpServletRequest request = servletRequestAttributes.getRequest();
        //利用oauth从请求头或者请求体中获取token
        String token = getToken(request);
        JwtTokenModel tokenModel = getJwtTokenModel(token);

        return tokenModel;
    }

    /**
     * 根据token获取对象
     *
     * @param token
     * @return
     */
    public static JwtTokenModel getJwtTokenModel(String token) {
        JwtTokenModel tokenModel = new JwtTokenModel();
        if (StringUtils.isNotBlank(token)) {
            try {
                tokenModel = JwtTokenUtil.parseTokenInfo(token);
            } catch (Exception e) {
                token = JwtTokenUtil.enhancedJwtDecryption(token);
                tokenModel = JwtTokenUtil.parseTokenInfo(token);
            }
        }
        return tokenModel;
    }

    /**
     * 利用oauth从请求头或者请求体中获取token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = null;
        try {
            token = (String) bearerTokenExtractor.extract(request).getPrincipal();
            if (StringUtils.isBlank(token)) {
                token = request.getHeader("Authorization");
            }
        } catch (Exception e) {
            if (StringUtils.isBlank(token)) {
                token = request.getHeader("Authorization");
            }
        }
        return token;
    }

    /**
     * 返回当前登录用户
     * 暂时不用
     *
     * @return LoginUserDTO
     */
    @Deprecated
    public static AuthUser getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AuthUser)) {
                return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        }
        return null;
    }
}
