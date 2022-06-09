package com.zods.sso.auth.auth.jwt;
import com.zods.auth.constant.Oauth2Constant;
import com.zods.auth.util.JwtTokenUtil;
import com.zods.redis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.concurrent.TimeUnit;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 自定义JwtTokenStore完成token的存取移除
 * @createDate 2021/1/6 09:58
 */
public class McloudJwtTokenStore extends JwtTokenStore {

    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtSettings jwtSettings;

    private McloudJwtAccessTokenConverter tokenConverter;

    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
     *
     * @param tokenConverter
     */
    public McloudJwtTokenStore(McloudJwtAccessTokenConverter tokenConverter, StringRedisTemplate redisTemplate) {
        super(tokenConverter);
        this.tokenConverter = tokenConverter;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 将token存至redis
     *
     * @param token
     * @param authentication
     */
    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        //将Jwt以自定义Key存入redis中，并保持与原Jwt有一致的时效性
        if (null != token && StringUtils.isNotBlank(token.getValue())) {
            // 1.删除旧的token
            removeAccessToken(token);
            // 2.存储token
            redisTemplate.opsForValue().set(JwtTokenUtil.getTokenRedisKey(token.getValue()), token.getValue(), token.getExpiresIn(),
                    TimeUnit.SECONDS);
        }
    }

    /**
     * refresh token存储redis
     *
     * @param refreshToken
     * @param authentication
     */
    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        if (null != refreshToken && StringUtils.isNotBlank(refreshToken.getValue())) {
            // 删除旧的refresh_token
            removeRefreshToken(refreshToken);
            // 存储refresh token
            redisTemplate.opsForValue().set(JwtTokenUtil.getRefreshTokenRedisKey(refreshToken.getValue()),
                    refreshToken.getValue(), jwtSettings.getRefreshTokenExpTime(), TimeUnit.SECONDS);
        }
    }

    /**
     * 从redis中获取token
     *
     * @param tokenValue
     * @return
     */
    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        // 检查redis中token是否还存在
        if (StringUtils.isNotBlank(tokenValue)) {
            Object tokenVal = redisTemplate.opsForValue().get(JwtTokenUtil.getTokenRedisKey(tokenConverter, tokenValue));
            if (null == tokenVal) {
                return null;
            }
        }
        // 获取accessToken
        OAuth2AccessToken accessToken = tokenConverter.convertAccessToken(tokenValue);
        if (tokenConverter.isRefreshToken(accessToken)) {
            throw new InvalidTokenException("Encoded token is a refresh token");
        }
        return accessToken;
    }

    /**
     * 从redis中移除token
     * 单点登录,所有一样前缀token都删除
     *
     * @param token
     */
    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        if (null != token && StringUtils.isNotBlank(token.getValue())) {
            RedisUtil.blurDeleteByKey(Oauth2Constant.REDIS_OAUTH2_USER_TOKEN_PRE(JwtTokenUtil.getTokenInfo(token.getValue()).getTokenKey()),
                    redisTemplate);
        }
        super.removeAccessToken(token);
    }

    /**
     * 从redis中移除refresh_token
     * 单点登录,所有一样前缀refresh_token都删除
     *
     * @param token
     */
    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        super.removeRefreshToken(token);
        if (null != token && StringUtils.isNotBlank(token.getValue())) {
            RedisUtil.blurDeleteByKey(Oauth2Constant.REDIS_OAUTH2_USER_REFRESH_TOKEN_PRE(JwtTokenUtil.parseTokenInfo(token.getValue()).getTokenKey()),
                    redisTemplate);
        }
    }

    /**
     * 根据token获取Oauth2授权信息
     *
     * @param token
     * @return
     */
    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return tokenConverter.extractAuthentication(tokenConverter.decodeJwt(token));
    }

    /**
     * 获取refresh token信息
     *
     * @param tokenValue
     * @return
     */
    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        OAuth2AccessToken accessToken = tokenConverter.convertAccessToken(tokenValue);
        return createRefreshToken(accessToken);
    }

    /**
     * 创建refresh_token
     *
     * @param encodedRefreshToken
     * @return
     */
    private OAuth2RefreshToken createRefreshToken(OAuth2AccessToken encodedRefreshToken) {
        if (!tokenConverter.isRefreshToken(encodedRefreshToken)) {
            throw new InvalidTokenException("Encoded token is not a refresh token");
        }
        if (encodedRefreshToken.getExpiration() != null) {
            return new DefaultExpiringOAuth2RefreshToken(encodedRefreshToken.getValue(),
                    encodedRefreshToken.getExpiration());
        }
        return new DefaultOAuth2RefreshToken(encodedRefreshToken.getValue());
    }

}
