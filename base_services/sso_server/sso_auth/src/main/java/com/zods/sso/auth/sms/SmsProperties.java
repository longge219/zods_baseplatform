package com.zods.sso.auth.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    private String baseUrl;

    private String loginName;

    private String password;

    private String spCode;

    private Long codeTime;

}
