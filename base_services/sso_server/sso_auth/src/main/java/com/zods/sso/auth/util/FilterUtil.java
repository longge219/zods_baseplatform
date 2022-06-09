package com.zods.sso.auth.util;

import com.google.gson.Gson;

import com.zods.auth.common.LoginUserHolder;
import com.zods.auth.common.wrapper.HttpAuthRequestWrapper;
import com.zods.auth.constant.Oauth2Constant;
import com.zods.auth.model.AuthUser;
import com.zods.auth.model.JwtTokenModel;
import com.zods.auth.util.JwtTokenUtil;
import com.zods.redis.RedisUtil;
import com.zods.sso.auth.service.AuthUserService;
import com.zods.sso.auth.temp.RequestMatcherConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import zom.zods.exception.enums.HandlerExceptionEnums;
import zom.zods.exception.model.ResponseModel;
import zom.zods.exception.util.ResponseUtil;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/1/6 14:25
 */
@Slf4j
public class FilterUtil {

    /**
     * 解析登录请求,单点登录判断是否为是否已登录
     *
     * @param requestWrapper
     * @param contextPath
     * @param redisTemplate
     * @return
     */
    public static HttpAuthRequestWrapper validateLoginRequest(HttpAuthRequestWrapper requestWrapper, String contextPath, StringRedisTemplate redisTemplate, AuthUserService authUserService, ServletResponse response) {
        // SSO登录过滤
        String loginURI = contextPath + RequestMatcherConstants.AUTHENTICATION_URL;
        if (loginURI.equals(requestWrapper.getRequestURI())) {
            requestWrapper = new HttpAuthRequestWrapper(requestWrapper, true, requestWrapper.isSSO());
            // 1.校验当前用户是否为注册用户.
            Gson gson = new Gson();
            LoginModel loginModel = gson.fromJson(requestWrapper.getBody(), LoginModel.class);
            AuthUser user = getAuthUser(authUserService, loginModel);
            if (null == user) {
                ResponseUtil.responseJson((HttpServletResponse) response, new ResponseModel(HandlerExceptionEnums.AUTH_USER_NOT));
                requestWrapper.setFailedRequest(true);
            }
            // 2.校验电话验证码登录方式.(***优于账户密码方式登录:有验证码登录可以强制退出账户登录用户***)
//            validatePhoneType(requestWrapper, redisTemplate, loginModel, user);

            // 3.校验当前用户是否已登录.
//            boolean isAlive = redisTemplate.opsForValue().get(Oauth2Constant.REDIS_USER_ID(user.getId())) != null;
            // 4.登录检测,获取缓存IP与当前请求IP对比
            String redisIp = redisTemplate.opsForValue().get(Oauth2Constant.REDIS_USER_IP(user.getId()));
//            boolean isSameIpUser = (redisIp != null && IpUtils.getIpAddr(requestWrapper).equals(redisIp)) && isAlive;
            // 5.若相同用户使用相同IP重复登录直接返回token
//            validateSameIpUser(redisTemplate, (HttpServletResponse) response, user.getId(), isSameIpUser);

            // 6.存在相同用户重复登录,返回已登录响应
//            if (isAlive && requestWrapper.isSSO()) {
            if (requestWrapper.isSSO()) {
                ResponseUtil.responseJson((HttpServletResponse) response, new ResponseModel(HandlerExceptionEnums.AUTH_USER_REPEAT_LOGIN));
                requestWrapper.setFailedRequest(true);
            }
        }
        return requestWrapper;
    }

    /**
     * 用户信息查询
     *
     * @param authUserService
     * @param loginModel
     * @return
     */
    private static AuthUser getAuthUser(AuthUserService authUserService, LoginModel loginModel) {
        AuthUser user = null;
        if (null != loginModel.getUsername()) {
            user = authUserService.findUserByUsername(loginModel.getUsername());
        } else if (null != loginModel.getPhone()) {
            user = authUserService.findUserByPhone(loginModel.getPhone());
        }
        return user;
    }

    /**
     * 若相同用户使用相同IP重复登录直接返回token
     *
     * @param redisTemplate
     * @param response
     * @param userId
     * @param isSameIpUser
     */
    private static void validateSameIpUser(StringRedisTemplate redisTemplate, HttpServletResponse response, long userId, boolean isSameIpUser) {
        if (isSameIpUser) {
            getCacheTokenInfo(redisTemplate, response, userId);
        }
    }

    /**
     * 返回缓存中token信息
     *
     * @param redisTemplate
     * @param response
     * @param userId
     */
    private static void getCacheTokenInfo(StringRedisTemplate redisTemplate, HttpServletResponse response, long userId) {
//        String token = JwtTokenUtil.enhancedJwtEncryption(RedisUtil.getRedisValue(redisTemplate, Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(userId)));
//        String refreshToken = JwtTokenUtil.enhancedJwtEncryption(RedisUtil.getRedisValue(redisTemplate, Oauth2Constant.REDIS_OAUTH2_USER_REFRESH_TOKEN_PRE(userId)));
//        Long expires = RedisUtil.getExpire(redisTemplate, Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(userId), TimeUnit.SECONDS);
//        ResponseUtil.responseJson(response, new ResponseModel(new LoginSuccessModel(token, refreshToken, expires.intValue())));
    }

    /**
     * 校验电话验证码登录方式
     *
     * @param requestWrapper
     * @param redisTemplate
     * @param loginModel
     */
    private static void validatePhoneType(HttpAuthRequestWrapper requestWrapper, StringRedisTemplate redisTemplate, LoginModel loginModel, AuthUser user) {
        if (Oauth2Constant.PHONE.equals(loginModel.getGrant_type())) {
            // 手机号码校验
            validatePhone(loginModel, user);
            // 验证码校验
            validateCode(redisTemplate, loginModel);
            Gson gson = new Gson();
            requestWrapper.setBody(gson.toJson(loginModel));
            // 数据清理
            RedisUtil.deleteMultiValue(redisTemplate,
                    // 清空用户信息
//                    Oauth2Constant.REDIS_USER_ID(user.getId()),
                    // 清空 access token
//                    Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(user.getId()),
                    // 清空 refresh token
//                    Oauth2Constant.REDIS_OAUTH2_USER_REFRESH_TOKEN_PRE(user.getId()),
                    // 删除验证码缓存
                    Oauth2Constant.SMS_CODE_KEY(loginModel.getPhone()));
        }
    }

    /**
     * 手机号码校验
     *
     * @param loginModel
     * @param user
     */
    private static void validatePhone(LoginModel loginModel, AuthUser user) {
        // 手机号码校验
        if (StringUtils.isBlank(loginModel.getPhone())) {
            throw new UserDeniedAuthorizationException("请输入手机号！");
        }
        if (!loginModel.getPhone().equals(user.getPhone())) {
            throw new UserDeniedAuthorizationException("请输入注册用户的手机号！");
        }
    }

    /**
     * 验证码校验
     *
     * @param redisTemplate
     * @param loginModel
     */
    private static void validateCode(StringRedisTemplate redisTemplate, LoginModel loginModel) {
        // 验证码校验
        if (StringUtils.isBlank(loginModel.getCode())) {
            throw new UserDeniedAuthorizationException("请输入验证码！");
        }
        // 从Redis里读取存储的验证码信息
        String codeFromRedis;
        try {
            codeFromRedis = redisTemplate.opsForValue().get(Oauth2Constant.SMS_CODE_KEY(loginModel.getPhone()));
        } catch (Exception e) {
            throw new UserDeniedAuthorizationException("验证码不存在或已过期,请重试！");
        }
        // 校验有效期
        if (codeFromRedis == null) {
            throw new UserDeniedAuthorizationException("验证码不存在或已过期,请重试！");
        }
        // 比较输入的验证码是否正确
        if (!StringUtils.equalsIgnoreCase(loginModel.getCode(), codeFromRedis)) {
            throw new UserDeniedAuthorizationException("验证码不正确！");
        }
    }

    /**
     * 解析refresh_token请求,刷新前判断是否用户已注销
     *
     * @param requestWrapper
     * @param redisTemplate
     * @param contextPath
     * @return
     */
    public static HttpAuthRequestWrapper parseRefreshRequest(HttpAuthRequestWrapper requestWrapper, StringRedisTemplate redisTemplate, String contextPath, ServletResponse response) {
        String refreshURI = contextPath + RequestMatcherConstants.REFRESH_TOKEN_URL;
        if (refreshURI.equals(requestWrapper.getRequestURI())) {
            // 1.解密为真实token,放回请求体
            String refreshToken = requestWrapper.getHeader("Authorization");
            // 2.校验用户refresh_token是否还存在,否则抛出异常.
            requestWrapper = FilterUtil.validateRefreshToken(refreshToken, requestWrapper, redisTemplate, response);
        }
        return requestWrapper;
    }

    /**
     * 若存在token则对其有效性检查
     * 校验异常返回true,直接打回请求
     * 校验正常返回false,不打回继续执行
     *
     * @param requestWrapper
     * @param redisTemplate
     * @param response
     * @return
     */
    public static boolean validateToken(HttpAuthRequestWrapper requestWrapper, StringRedisTemplate redisTemplate, ServletResponse response) {
        try {
            JwtTokenModel tokenModel = LoginUserHolder.currentUser();
            if (null == tokenModel.getUserId()) {
                return false;
            }
            String tokenPre = Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(tokenModel.getTokenKey());
            if (!RedisUtil.cacheIsAlive(tokenPre, redisTemplate)) {
                ResponseUtil.responseJson((HttpServletResponse) response, new ResponseModel(HandlerExceptionEnums.AUTH_TOKEN_EXPIRED));
                return true;
            }
        } catch (Exception e) {
            ResponseUtil.responseJson((HttpServletResponse) response, new ResponseModel(HandlerExceptionEnums.AUTH_JWT_TOKEN_FAIL));
            return true;
        }

        return false;
    }

    /**
     * 参数refresh_token校验
     *
     * @param requestWrapper
     * @param redisTemplate
     * @param response
     * @return
     */
    public static HttpAuthRequestWrapper validateRefreshToken(String refreshToken, HttpAuthRequestWrapper requestWrapper, StringRedisTemplate redisTemplate, ServletResponse response) {
        try {
            Optional<String> refreshTokenOp = Optional.ofNullable(refreshToken);
            if (refreshTokenOp.isPresent()) {
                // 获取缓存前缀,查询是否依然存在
                String tokenPre = Oauth2Constant.REDIS_OAUTH2_USER_REFRESH_TOKEN_PRE(JwtTokenUtil.parseTokenInfo(refreshToken).getTokenKey());
                if (!RedisUtil.cacheIsAlive(tokenPre, redisTemplate)) {
                    ResponseUtil.responseJson((HttpServletResponse) response, new ResponseModel(HandlerExceptionEnums.AUTH_REFRESH_TOKEN_EXPIRED));
                    requestWrapper.setFailedRequest(true);
                }
            }
        } catch (Exception e) {
            ResponseUtil.responseJson((HttpServletResponse) response, new ResponseModel(HandlerExceptionEnums.AUTH_JWT_REFRESH_TOKEN_FAIL));
            requestWrapper.setFailedRequest(true);
        }
        return requestWrapper;
    }

    /**
     * 不能为空
     *
     * @param object
     * @param message
     */
    public static void notBlank(String object, String message) {
        if (StringUtils.isBlank(object)) {
            throw new IllegalArgumentException(message);
        }
    }

}
