package com.zods.auth.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nimbusds.jose.JWSObject;
import com.zods.auth.constant.Oauth2Constant;
import com.zods.auth.model.JwtTokenModel;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.DigestUtils;
import zom.zods.exception.enums.HandlerExceptionEnums;
import zom.zods.exception.exception.category.McloudHandlerException;
import java.text.ParseException;
import java.util.List;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/10/12 20:07
 */
@Slf4j
public class JwtTokenUtil {

    /**
     * 获取token信息
     *
     * @param tokenConverter
     * @param token
     * @return
     */
    public static JwtTokenModel getTokenInfo(JwtAccessTokenConverter tokenConverter, String token) {
        Claims claims = SHA256withRSA.parseToken(tokenConverter.getKey().get("value"), token);
        Gson gson = new Gson();
        claims.put("authorities", JSON.parseArray(claims.get("authorities").toString()));
        claims.put("purviewCodes", JSON.parseArray(gson.toJson(claims.get("purviewCodes"))));
        claims.put("scope", JSON.parseArray(claims.get("scope").toString()));
        JwtTokenModel tokenModel = gson.fromJson(gson.toJson(claims), JwtTokenModel.class);
        return tokenModel;
    }

    /**
     * 获取token/refresh_token信息
     *
     * @param token
     * @return JwtTokenModel
     */
    public static JwtTokenModel getTokenInfo(String token) {
        log.info("parse token(refresh_token) :{}", token);
        Gson gson = new Gson();
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (ParseException e) {
            return new JwtTokenModel();
        }
        JSONObject tokenJson = JSON.parseObject(jwsObject.getPayload().toString());
        // 解析获取authorities、scope
        List<String> authorities = parseAuthorities(tokenJson.getString("authorities"));
        List<String> scope = parseScope(tokenJson.getString("scope"));
        tokenJson.remove("authorities");
        tokenJson.remove("scope");
        JwtTokenModel tokenModel = gson.fromJson(tokenJson.toJSONString(), JwtTokenModel.class);
        tokenModel.setAuthorities(authorities);
        tokenModel.setScope(scope);

        return tokenModel;
    }

    /**
     * 获取token/refresh_token信息
     *
     * @param token
     * @return JwtTokenModel
     */
    public static JwtTokenModel parseTokenInfo(String token) {
        token = enhancedJwtDecryption(token);
        log.info("parse token(refresh_token) :{}", token);
        Gson gson = new Gson();
        JWSObject jwsObject = null;
        try {
            jwsObject = JWSObject.parse(token);
        } catch (ParseException e) {
            return new JwtTokenModel();
        }
        JSONObject tokenJson = JSON.parseObject(jwsObject.getPayload().toString());
        // 解析获取authorities、scope
        List<String> authorities = parseAuthorities(tokenJson.getString("authorities"));
        List<String> scope = parseScope(tokenJson.getString("scope"));
        tokenJson.remove("authorities");
        tokenJson.remove("scope");
        JwtTokenModel tokenModel = gson.fromJson(tokenJson.toJSONString(), JwtTokenModel.class);
        tokenModel.setAuthorities(authorities);
        tokenModel.setScope(scope);

        return tokenModel;
    }

    /**
     * parse authorities
     *
     * @param value
     * @return
     */
    public static List<String> parseAuthorities(String value) {
        Gson gson = new Gson();
        String authorities = value.replaceAll("\\\\\"", "");
        return gson.fromJson(authorities, new TypeToken<List<String>>() {
        }.getType());
    }

    /**
     * parse scope
     *
     * @param value
     * @return
     */
    public static List<String> parseScope(String value) {
        Gson gson = new Gson();
        String scope = value.replaceAll("\\[\\\\\"", "").replaceAll("\\\\\"\\]", "");
        return gson.fromJson(scope, new TypeToken<List<String>>() {
        }.getType());
    }

    /**
     * 获取存储在redis的key值
     *
     * @param tokenConverter
     * @param token
     * @return
     */
    public static String getTokenRedisKey(JwtAccessTokenConverter tokenConverter, String token) {
        String tokenPre = null;
        token = enhancedJwtDecryption(token);
        try {
            tokenPre = Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(getTokenInfo(tokenConverter, token).getTokenKey());
        } catch (Exception e) {
            throw new McloudHandlerException(HandlerExceptionEnums.AUTH_TOKEN_INVALID);
        }
        return tokenPre + DigestUtils.md5DigestAsHex(token.getBytes());
    }

    /**
     * 获取存储在redis token的key值
     *
     * @param token
     * @return
     */
    public static String getTokenRedisKey(String token) {
        return Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(getTokenInfo(token).getTokenKey()) + DigestUtils.md5DigestAsHex(token.getBytes());
    }

    /**
     * 获取存储在redis refreshToken的key值
     *
     * @param refreshToken
     * @return
     */
    public static String getRefreshTokenRedisKey(String refreshToken) {
        return Oauth2Constant.REDIS_OAUTH2_USER_REFRESH_TOKEN_PRE(getTokenInfo(refreshToken).getTokenKey()) +
                DigestUtils.md5DigestAsHex(refreshToken.getBytes());
    }


    /**
     * 增强JWT加密增加前后缀
     *
     * @param token
     * @return
     */
    public static String enhancedJwtEncryption(String token) {
        String[] tokenArrs = token.split("\\.");
        return tokenArrs[0] + Oauth2Constant.PRE + tokenArrs[1] + Oauth2Constant.SUF + tokenArrs[2];
    }

    /**
     * 增强JWT解密获取真实token
     *
     * @param token
     * @return
     */
    public static String enhancedJwtDecryption(String token) {
        if (token.contains(Oauth2Constant.PRE)) {
            String[] tokenArrs = token.split("\\.");
            return tokenArrs[0] + "." + tokenArrs[1].substring(2, tokenArrs[1].length() - 2) + "." + tokenArrs[2];
        }
        return token;
    }

}
