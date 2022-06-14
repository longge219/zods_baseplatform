package com.zods.plugins.zods.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.List;
import java.util.Map;

import com.zods.plugins.zods.GaeaProperties;
import org.springframework.util.CollectionUtils;

public class JwtBean {
    private GaeaProperties gaeaProperties;

    public JwtBean() {
    }

    public JwtBean(GaeaProperties gaeaProperties) {
        this.gaeaProperties = gaeaProperties;
    }

    public GaeaProperties getGaeaProperties() {
        return this.gaeaProperties;
    }

    public String createToken(String username, String uuid) {
        String token = JWT.create().withClaim("username", username).withClaim("uuid", uuid).sign(Algorithm.HMAC256(this.gaeaProperties.getSecurity().getJwtSecret()));
        return token;
    }

    public String createToken(String username, String uuid, Integer type, String tenantCode) {
        String token = JWT.create().withClaim("username", username).withClaim("uuid", uuid).withClaim("type", type).withClaim("tenant", tenantCode).sign(Algorithm.HMAC256(this.gaeaProperties.getSecurity().getJwtSecret()));
        return token;
    }

    public String createToken(String username, List<String> roles, List<String> authorities, List<String> menus) {
        Builder builder = JWT.create();
        if (!CollectionUtils.isEmpty(roles)) {
            //builder.withClaim("role", roles);
        }

        if (!CollectionUtils.isEmpty(authorities)) {
            //builder.withClaim("authorities", authorities);
        }

        if (!CollectionUtils.isEmpty(authorities)) {
            //builder.withClaim("menus", menus);
        }

        String token = builder.withClaim("username", username).sign(Algorithm.HMAC256(this.gaeaProperties.getSecurity().getJwtSecret()));
        return token;
    }

    public Map<String, Claim> getClaim(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(this.gaeaProperties.getSecurity().getJwtSecret())).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaims();
    }

    public String getUsername(String token) {
        Claim claim = (Claim)this.getClaim(token).get("username");
        return claim == null ? null : claim.asString();
    }

    public String getTenant(String token) {
        Claim claim = (Claim)this.getClaim(token).get("tenant");
        return claim == null ? null : claim.asString();
    }

    public Integer getUserType(String token) {
        Claim claim = (Claim)this.getClaim(token).get("type");
        return claim == null ? null : claim.asInt();
    }

    public String getUUID(String token) {
        Claim claim = (Claim)this.getClaim(token).get("uuid");
        return claim == null ? null : claim.asString();
    }
}
