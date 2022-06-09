package com.zods.sso.auth.config;
import com.zods.sso.auth.auth.factory.McloudOauth2RequestFactory;
import com.zods.sso.auth.auth.granter.SmsCodeTokenGranter;
import com.zods.sso.auth.auth.jwt.JwtSettings;
import com.zods.sso.auth.auth.jwt.McloudJwtAccessTokenConverter;
import com.zods.sso.auth.auth.jwt.McloudJwtTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Wangchao
 * @version 1.0
 * @Description OAuth2.0授权服务器
 * @createDate 2020/09/09 14:45
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtSettings jwtSettings;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ClientDetailsService jdbcClientDetails() {
        // 基于 JDBC 实现，需要事先在数据库配置客户端信息
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 对Jwt签名时，增加一个密钥,使用非对称加密算法来对Token进行签名
     * JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
     */
    @Bean
    public McloudJwtAccessTokenConverter accessTokenConverter() {
        McloudJwtAccessTokenConverter converter = new McloudJwtAccessTokenConverter();
        ClassPathXmlApplicationContext cx = new ClassPathXmlApplicationContext();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(cx.getResource("classpath:oauth2.jks"), "oauth2".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2"));
        return converter;
    }

    /**
     * 设置token 由Jwt产生，不使用默认的透明令牌
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new McloudJwtTokenStore(accessTokenConverter(), redisTemplate);
    }

    /**
     * 构建token设置令牌基础配置
     *
     * @return
     */
    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(jwtTokenStore());
        // access token有效期12个小时
        services.setAccessTokenValiditySeconds(jwtSettings.getTokenExpirationTime());
        // refresh token有效期12天
        services.setRefreshTokenValiditySeconds(jwtSettings.getRefreshTokenExpTime());
        // 支持使用refresh token刷新access token
        services.setSupportRefreshToken(true);
        services.setReuseRefreshToken(false);
        services.setTokenEnhancer(accessTokenConverter());

        return services;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 读取客户端配置
        clients.withClientDetails(jdbcClientDetails());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 设置令牌
        endpoints
                .tokenServices(defaultTokenServices())
                .tokenStore(jwtTokenStore())
                .requestFactory(new McloudOauth2RequestFactory(jdbcClientDetails()))
                .authenticationManager(authenticationManager)
                .tokenGranter(tokenGranter(endpoints))
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    /**
     * 重点
     * 先获取已经有的五种授权，然后添加我们自己的进去
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @return TokenGranter
     */
    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new SmsCodeTokenGranter(authenticationManager, defaultTokenServices(), jdbcClientDetails(),
                new McloudOauth2RequestFactory(jdbcClientDetails()), redisTemplate));
        return new CompositeTokenGranter(granters);
    }

}
