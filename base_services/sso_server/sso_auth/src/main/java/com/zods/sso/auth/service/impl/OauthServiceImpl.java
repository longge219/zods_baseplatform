package com.zods.sso.auth.service.impl;
import com.zods.auth.constant.Oauth2Constant;
import com.zods.auth.model.JwtTokenModel;
import com.zods.auth.util.JwtTokenUtil;
import com.zods.redis.RedisUtil;
import com.zods.sso.auth.auth.jwt.JwtSettings;
import com.zods.sso.auth.entity.OauthClientDetails;
import com.zods.sso.auth.model.LoginModel;
import com.zods.sso.auth.model.ResponseModel;
import com.zods.sso.auth.service.AuthUserService;
import com.zods.sso.auth.service.ClientService;
import com.zods.sso.auth.service.OauthService;
import com.zods.sso.auth.sms.SmsService;
import com.zods.sso.auth.temp.*;
import com.zods.sso.auth.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import zom.zods.exception.enums.HandlerExceptionEnums;
import zom.zods.exception.exception.category.McloudHandlerException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {

    private final JwtTokenStore tokenStore;

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final JwtSettings jwtSettings;

    private final StringRedisTemplate redisTemplate;

    private final AuthUserService authUserService;

    private final SmsService smsService;

    private final ClientService clientService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseModel login(LoginModel loginModel, HttpServletRequest request) {
        // 1.认证获取token消息
        LoginSuccessModel success = getToken(loginModel);
        // 2.缓存当前用户/IP
        Map<String, String> data = new HashMap<>(2);
        JwtTokenModel userInfo = JwtTokenUtil.parseTokenInfo(success.getAccessToken());
        data.put(Oauth2Constant.REDIS_USER_ID(userInfo.getTokenKey()), userInfo.getUser_name());
        data.put(Oauth2Constant.REDIS_USER_IP(userInfo.getUserId()), IpUtils.getIpAddr(request));
        RedisUtil.saveMultiValue(redisTemplate, TimeUnit.SECONDS, jwtSettings.getTokenExpirationTime(), data);
        return new ResponseModel(success);
    }

    @Override
    public ResponseModel refreshToken(LoginModel loginModel) {
        return new ResponseModel(getToken(loginModel));
    }

    /**
     * 构建登陆请求体参数
     *
     * @param loginModel
     * @return
     */
    private MultiValueMap<String, Object> buildLoginRequestParam(LoginModel loginModel) {
        // 参数校验
        validateBaseParam(loginModel);
        // 请求参数构建
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();
        // 密码认证模式
        if (Oauth2Constant.PASSWORD.equals(loginModel.getGrant_type())) {
            FilterUtil.notBlank(loginModel.getUsername(), "Username must not be null");
            FilterUtil.notBlank(loginModel.getPassword(), "Password must not be null");
            paramsMap.set(Oauth2Constant.USER_NAME, loginModel.getUsername());
            paramsMap.set(Oauth2Constant.PASSWORD, loginModel.getPassword());
        }
        // 刷新token
        else if (Oauth2Constant.REFRESH_TOKEN.equals(loginModel.getGrant_type())) {
            FilterUtil.notBlank(loginModel.getRefresh_token(), "Refresh token must not be null");
            paramsMap.set(Oauth2Constant.REFRESH_TOKEN, loginModel.getRefresh_token());
        }
        // 授权码模式
        else if (Oauth2Constant.AUTHORIZATION_CODE.equals(loginModel.getGrant_type())) {
            FilterUtil.notBlank(loginModel.getCode(), "Code must not be null");
            paramsMap.set(Oauth2Constant.CODE, loginModel.getCode());
        }
        // 短信验证码模式
        else if (Oauth2Constant.PHONE.equals(loginModel.getGrant_type())) {
            FilterUtil.notBlank(loginModel.getPhone(), "phone must not be null");
            paramsMap.set(Oauth2Constant.PHONE, loginModel.getPhone());
            paramsMap.set(Oauth2Constant.CLIENT_ID, loginModel.getClient_id());
        }
        paramsMap.set(Oauth2Constant.GRANT_TYPE, loginModel.getGrant_type());
        return paramsMap;
    }

    @Override
    public ResponseModel logout(String accessToken) {
        JwtTokenModel tokenModel = JwtTokenUtil.parseTokenInfo(accessToken);
        Long userId = tokenModel.getUserId();
        // 数据清理
        RedisUtil.deleteMultiValue(redisTemplate,
                // 清空用户信息
                Oauth2Constant.REDIS_USER_ID(tokenModel.getTokenKey()),
                // 清空 access token
                Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(tokenModel.getTokenKey()),
                // 清空 refresh token
                Oauth2Constant.REDIS_OAUTH2_USER_REFRESH_TOKEN_PRE(tokenModel.getTokenKey()));
        return new ResponseModel(HandlerExceptionEnums.SUCCESS.code, "登出成功");
    }

    /**
     * 认证获取token消息
     *
     * @param loginModel
     * @return
     */
    private LoginSuccessModel getToken(LoginModel loginModel) {
        // 1.构建请求参数
        MultiValueMap<String, Object> paramsMap = buildLoginRequestParam(loginModel);
        // 2.发起认证请求
        OAuth2AccessToken token = RestTemplateUtils.
                postForObject(
                        loginModel.getClient_id(),
                        loginModel.getClient_secret(),
                        Oauth2Constant.LOCALHOST + port + contextPath + RequestMatcherConstants.OAUTH_TOKEN_URL,
                        paramsMap,
                        OAuth2AccessToken.class);
        // 3.返回认证信息
        LoginSuccessModel success = new LoginSuccessModel(
                JwtTokenUtil.enhancedJwtEncryption(token.getValue()),
                JwtTokenUtil.enhancedJwtEncryption(token.getRefreshToken().getValue()),
                token.getExpiresIn());
        return success;
    }

    /**
     * 登录刷新基础参数检验
     *
     * @param loginModel
     */
    private void validateBaseParam(LoginModel loginModel) {
        FilterUtil.notBlank(loginModel.getGrant_type(), "Grant type must not be null");
        FilterUtil.notBlank(loginModel.getClient_id(), "Client id must not be null");
        Assert.doesNotContain(loginModel.getClient_id(), ":", "Client id must not contain a colon");
        FilterUtil.notBlank(loginModel.getClient_secret(), "Client secret must not be null");
    }

    /**
     * 用户获取手机验证码
     *
     * @param phone
     * @return
     */
    @Override
    public ResponseModel smsSend(String phone, BaseModel baseModel) {
        // 1.对发送短信的用户基本信息校验
        validateSmsInfo(phone, baseModel);
        // 2.校验码生成发送存储
        String code = RandomStringUtils.randomNumeric(6);
        String msg = "手机尾号为" + phone.substring(7) + "的地灾巡查责任人您好，您的微信小程序登录验证码是" + code + "。";
        SmsSendResVo resVo = smsService.send(phone, msg);
        // 3.短信结果校验
        if (null != resVo && resVo.getResult() == 0) {
            redisTemplate.opsForValue().set(Oauth2Constant.SMS_CODE_KEY(phone), code, 5, TimeUnit.MINUTES);
            log.debug("向手机号" + phone + "发送验证码：" + code);
        } else if (null != resVo && resVo.getResult() != 0) {
            throw new McloudHandlerException(resVo.getDescription(), HandlerExceptionEnums.BUSSINESS_EXCETION.code);
        } else {
            throw new McloudHandlerException(HandlerExceptionEnums.EXCEPTION);
        }

        return new ResponseModel(resVo.getDescription());
    }

    /**
     * 对发送短信的用户基本信息校验
     *
     * @param phone
     * @param baseModel
     */
    private void validateSmsInfo(String phone, BaseModel baseModel) {
        // 电话信息校验
        if (!ValidatorUtil.isMobile(phone)) {
            throw new McloudHandlerException(HandlerExceptionEnums.AUTH_SMS_CODE_FORMATE_ERROR);
        }
        if (authUserService.findUserByPhone(phone) == null) {
            throw new McloudHandlerException(HandlerExceptionEnums.AUTH_USER_NOT);
        }
        // 客户端信息校验
        OauthClientDetails clientDetails = clientService.findClientDetailsByClientId(baseModel.getClient_id());
        if (null == clientDetails || !passwordEncoder.matches(baseModel.getClient_secret(), clientDetails.getClientSecret())) {
            throw new McloudHandlerException(HandlerExceptionEnums.AUTH_NOT_SUPPORTED_CLIENT);
        }
    }
}
