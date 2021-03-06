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
        // 1.????????????token??????
        LoginSuccessModel success = getToken(loginModel);
        // 2.??????????????????/IP
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
     * ???????????????????????????
     *
     * @param loginModel
     * @return
     */
    private MultiValueMap<String, Object> buildLoginRequestParam(LoginModel loginModel) {
        // ????????????
        validateBaseParam(loginModel);
        // ??????????????????
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();
        // ??????????????????
        if (Oauth2Constant.PASSWORD.equals(loginModel.getGrant_type())) {
            FilterUtil.notBlank(loginModel.getUsername(), "Username must not be null");
            FilterUtil.notBlank(loginModel.getPassword(), "Password must not be null");
            paramsMap.set(Oauth2Constant.USER_NAME, loginModel.getUsername());
            paramsMap.set(Oauth2Constant.PASSWORD, loginModel.getPassword());
        }
        // ??????token
        else if (Oauth2Constant.REFRESH_TOKEN.equals(loginModel.getGrant_type())) {
            FilterUtil.notBlank(loginModel.getRefresh_token(), "Refresh token must not be null");
            paramsMap.set(Oauth2Constant.REFRESH_TOKEN, loginModel.getRefresh_token());
        }
        // ???????????????
        else if (Oauth2Constant.AUTHORIZATION_CODE.equals(loginModel.getGrant_type())) {
            FilterUtil.notBlank(loginModel.getCode(), "Code must not be null");
            paramsMap.set(Oauth2Constant.CODE, loginModel.getCode());
        }
        // ?????????????????????
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
        // ????????????
        RedisUtil.deleteMultiValue(redisTemplate,
                // ??????????????????
                Oauth2Constant.REDIS_USER_ID(tokenModel.getTokenKey()),
                // ?????? access token
                Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(tokenModel.getTokenKey()),
                // ?????? refresh token
                Oauth2Constant.REDIS_OAUTH2_USER_REFRESH_TOKEN_PRE(tokenModel.getTokenKey()));
        return new ResponseModel(HandlerExceptionEnums.SUCCESS.code, "????????????");
    }

    /**
     * ????????????token??????
     *
     * @param loginModel
     * @return
     */
    private LoginSuccessModel getToken(LoginModel loginModel) {
        // 1.??????????????????
        MultiValueMap<String, Object> paramsMap = buildLoginRequestParam(loginModel);
        // 2.??????????????????
        OAuth2AccessToken token = RestTemplateUtils.
                postForObject(
                        loginModel.getClient_id(),
                        loginModel.getClient_secret(),
                        Oauth2Constant.LOCALHOST + port + contextPath + RequestMatcherConstants.OAUTH_TOKEN_URL,
                        paramsMap,
                        OAuth2AccessToken.class);
        // 3.??????????????????
        LoginSuccessModel success = new LoginSuccessModel(
                JwtTokenUtil.enhancedJwtEncryption(token.getValue()),
                JwtTokenUtil.enhancedJwtEncryption(token.getRefreshToken().getValue()),
                token.getExpiresIn());
        return success;
    }

    /**
     * ??????????????????????????????
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
     * ???????????????????????????
     *
     * @param phone
     * @return
     */
    @Override
    public ResponseModel smsSend(String phone, BaseModel baseModel) {
        // 1.??????????????????????????????????????????
        validateSmsInfo(phone, baseModel);
        // 2.???????????????????????????
        String code = RandomStringUtils.randomNumeric(6);
        String msg = "???????????????" + phone.substring(7) + "????????????????????????????????????????????????????????????????????????" + code + "???";
        SmsSendResVo resVo = smsService.send(phone, msg);
        // 3.??????????????????
        if (null != resVo && resVo.getResult() == 0) {
            redisTemplate.opsForValue().set(Oauth2Constant.SMS_CODE_KEY(phone), code, 5, TimeUnit.MINUTES);
            log.debug("????????????" + phone + "??????????????????" + code);
        } else if (null != resVo && resVo.getResult() != 0) {
            throw new McloudHandlerException(resVo.getDescription(), HandlerExceptionEnums.BUSSINESS_EXCETION.code);
        } else {
            throw new McloudHandlerException(HandlerExceptionEnums.EXCEPTION);
        }

        return new ResponseModel(resVo.getDescription());
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param phone
     * @param baseModel
     */
    private void validateSmsInfo(String phone, BaseModel baseModel) {
        // ??????????????????
        if (!ValidatorUtil.isMobile(phone)) {
            throw new McloudHandlerException(HandlerExceptionEnums.AUTH_SMS_CODE_FORMATE_ERROR);
        }
        if (authUserService.findUserByPhone(phone) == null) {
            throw new McloudHandlerException(HandlerExceptionEnums.AUTH_USER_NOT);
        }
        // ?????????????????????
        OauthClientDetails clientDetails = clientService.findClientDetailsByClientId(baseModel.getClient_id());
        if (null == clientDetails || !passwordEncoder.matches(baseModel.getClient_secret(), clientDetails.getClientSecret())) {
            throw new McloudHandlerException(HandlerExceptionEnums.AUTH_NOT_SUPPORTED_CLIENT);
        }
    }
}
