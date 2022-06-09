package com.zods.auth.constant;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/1/11 10:01
 */
public class Oauth2Constant {

    public static final String AUD = "aud";

    public static final String CLIENT_ID = "client_id";

    public static final String EXP = "exp";

    public static final String JTI = "jti";

    public static final String SCOPE = OAuth2AccessToken.SCOPE;

    /**
     * 用户名
     */
    public static final String USER_NAME = "username";

    /**
     * 密码
     */
    public static final String PASSWORD = "password";

    /**
     * 刷新
     */
    public static final String REFRESH_TOKEN = "refresh_token";

    /**
     * 授权码模式
     */
    public static final String AUTHORIZATION_CODE = "authorization_code";

    /**
     * 鉴权类型
     */
    public static final String GRANT_TYPE = "grant_type";

    public static final String LOCALHOST = "http://localhost:";
    public static final String CODE = "code";
    public static final String MSG = "msg";

    /**
     * 小程序 clientId secret都默认为app
     */
    public static final String APP = "app";

    /**
     * 用户缓存前缀
     */
    public static final String USER_CACHE_KEY = "com.orieange:user:";

    /**
     * 认证缓存前缀
     */
    public static final String REDIS_DIR = "com.orieange:oauth2:";

    /**
     * 短信验证码redis key
     */
    public static final String SMS_CODE_KEY(String mobile) {
        return REDIS_DIR + "sms-code-" + mobile;
    }

    /**
     * token缓存前缀
     *
     * @param tokenKey
     * @return
     */
    public static final String REDIS_OAUTH2_USER_TOKEN_PRE(String tokenKey) {
        return REDIS_DIR + tokenKey + ":access-token-";
    }

    /**
     * refresh token 缓存前缀
     *
     * @param tokenKey
     * @return
     */
    public static final String REDIS_OAUTH2_USER_REFRESH_TOKEN_PRE(String tokenKey) {
        return REDIS_DIR + tokenKey + ":refresh-token-";
    }

    /**
     * 用户缓存key
     *
     * @param tokenKey
     * @return
     */
    public static final String REDIS_USER_ID(String tokenKey) {
        return USER_CACHE_KEY + tokenKey;
    }

    /**
     * 用户IP缓存key
     *
     * @param userId
     * @return
     */
    public static final String REDIS_USER_IP(Long userId) {
        return USER_CACHE_KEY + userId + ":ip";
    }

    /**
     * 短信验证码手机号参数的名称
     */
    public static final String PHONE = "phone";

    public static final String LOGIN_URI = "/api/auth/oauth/login";
    public static final String REFRESH_TOKEN_URI = "/api/auth/oauth/refresh";
    public static final String LOGIN_SMS_URI = "/api/auth/oauth/login/sms";


    /**
     * 增强JWT加密内容前后缀
     */
    public static final String PRE = ".Cx";
    public static final String SUF = "dB.";

    /**
     * token key
     */
    public static final String ACCESS_TOKEN = "access_token";
    public static final String TOKEN = "token";

    /*****************Oauth2独立API***********************/
    /**
     * 短信发送API(不需要认证)
     */
    public static final String OAUTH_SMS_SEND = "/oauth/sms/send";

}
