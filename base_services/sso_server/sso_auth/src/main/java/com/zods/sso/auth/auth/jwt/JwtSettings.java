package com.zods.sso.auth.auth.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/10/13 11:00
 */
@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtSettings {

    /**
     * will expire after this time.
     */
    private Integer tokenExpirationTime;

    /**
     * can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpTime;

}
