package com.zods.sso.auth.auth.factory;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/4/1 15:19
 */
public class McloudOauth2RequestFactory extends DefaultOAuth2RequestFactory {

    public McloudOauth2RequestFactory(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
    }

}
