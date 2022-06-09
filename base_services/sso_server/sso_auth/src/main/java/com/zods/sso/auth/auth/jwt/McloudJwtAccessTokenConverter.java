package com.zods.sso.auth.auth.jwt;
import com.alibaba.fastjson.JSON;
import com.zods.auth.constant.Oauth2Constant;
import com.zods.auth.util.JwtTokenUtil;
import com.zods.auth.util.SHA256withRSA;
import com.zods.sso.auth.service.AuthUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/10/9 10:00
 */
@Slf4j
public class McloudJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private JsonParser objectMapper = JsonParserFactory.create();

    @Autowired
    private AuthUserService authUserService;

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    private Signer signer;

    public SecretKey secretKey;

    /**
     * token 加密
     *
     * @param accessToken
     * @param authentication
     * @return
     */
    @SneakyThrows
    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        secretKey = new SecretKeySpec(super.getKey().get("value").getBytes(), SHA256withRSA.SIGNATURE_ALGORITHM);
        this.signer = new MacSigner(secretKey);
        String content;
        try {
            content = this.objectMapper.formatMap(this.tokenConverter.convertAccessToken(accessToken, authentication));
        } catch (Exception var5) {
            throw new IllegalStateException("Cannot convert access token to JSON", var5);
        }
        JSONObject contentJson = null;
        try {
            contentJson = new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
            log.error("json parse failure:" + e.getMessage(), e);
        }
        LocalDateTime currentTime = LocalDateTime.now();
        Claims claims = Jwts.claims();
        Long userId = authUserService.findUserByUsername(authentication.getName()).getId();
        claims.put("userId", userId);
        claims.put("purviewCodes", authUserService.findUserPurviewCodeByUserId(userId));
        claims.put("roleName", authUserService.findUserRoleNameByUserId(userId));
        claims.put("user_name", contentJson.getString("user_name"));
        claims.put("authorities", contentJson.getString("authorities"));
        claims.put("client_id", contentJson.getString("client_id"));
        claims.put("scope", contentJson.getString("scope"));
        claims.put("time", System.currentTimeMillis());
        claims.put(Oauth2Constant.JTI, contentJson.getString(Oauth2Constant.JTI));
        claims.put(AccessTokenConverter.ATI, accessToken.getAdditionalInformation().get(AccessTokenConverter.ATI));
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusSeconds(accessToken.getExpiresIn())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        return token;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public Claims decodeJwt(String token) {
        Claims claims = SHA256withRSA.parseToken(super.getKey().get("value"), JwtTokenUtil.enhancedJwtDecryption(token));
        claims.put("authorities", JSON.toJSON(JwtTokenUtil.parseAuthorities(claims.get("authorities").toString())));
        claims.put("scope", JSON.toJSON(JwtTokenUtil.parseScope(claims.get("scope").toString())));
        return claims;
    }


    public OAuth2AccessToken convertAccessToken(String tokenValue) {
        return extractAccessJwtToken(tokenValue, decodeJwt(tokenValue));
    }

    private OAuth2AccessToken extractAccessJwtToken(String value, Claims claims) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(value);
        if (claims.containsKey(Oauth2Constant.EXP)) {
            token.setExpiration(claims.getExpiration());
        }
        if (claims.containsKey(Oauth2Constant.JTI)) {
            claims.put(Oauth2Constant.JTI, claims.getId());
        }
        token.setScope(extractScope(claims));
        claims.remove(Oauth2Constant.EXP);
        claims.remove(Oauth2Constant.AUD);
        claims.remove(Oauth2Constant.CLIENT_ID);
        claims.remove(Oauth2Constant.SCOPE);
        token.setAdditionalInformation(claims);
        return token;
    }

    private Set<String> extractScope(Claims claims) {
        Set<String> scope = Collections.emptySet();
        if (claims.containsKey(Oauth2Constant.SCOPE)) {
            Object scopeObj = claims.get(Oauth2Constant.SCOPE);
            if (String.class.isInstance(scopeObj)) {
                scope = new LinkedHashSet<String>(Arrays.asList(String.class.cast(scopeObj).split(" ")));
            } else if (Collection.class.isAssignableFrom(scopeObj.getClass())) {
                @SuppressWarnings("unchecked")
                Collection<String> scopeColl = (Collection<String>) scopeObj;
                scope = new LinkedHashSet<String>(scopeColl);    // Preserve ordering
            }
        }
        return scope;
    }

}
