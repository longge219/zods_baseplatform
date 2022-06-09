package com.zods.auth.auth;//package com.orieange.oauth.data.auth;
//
//import com.orieange.oauth.data.auth.permission.CustomAccessDecisionManager;
//import com.orieange.oauth.data.auth.permission.handler.CommonAccessDeniedHandler;
//import com.orieange.oauth.data.auth.permission.handler.CommonAuthenticationEntryPoint;
//import com.orieange.oauth.data.common.cors.CustomCorsFilter;
//import com.orieange.oauth.data.common.token.XcRemoteTokenServices;
//import com.orieange.oauth.data.jwt.constant.HttpAuthEnums;
//import com.orieange.oauth.data.jwt.constant.RequestMatcherConstants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.util.FileCopyUtils;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//
///**
// * @author Wangchao
// * @version 1.0
// * @Description Oauth2.0资源服务配置
// * @createDate 2020/09/09 14:45
// */
//@Configuration
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    /**
//     * Token issuer.
//     */
//    @Value("${oauth2.client.clientId}")
//    private String clientId;
//
//    /**
//     * Key is used to sign
//     */
//    @Value("${oauth2.client.clientSecret}")
//    private String clientSecret;
//
//    /**
//     * can be refreshed during this timeframe.
//     */
//    @Value("${oauth2.client.checkTokenUrl}")
//    private String checkTokenUrl;
//
//    /**
//     * Server application name
//     */
//    @Value("${oauth2.client.serverApplicationName}")
//    private String serverApplicationName;
//
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
//
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        // 配置数据源（注意，我使用的是 HikariCP 连接池），以上注解是指定数据源，否则会有冲突
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @Primary
//    public CommonAccessDeniedHandler getCommonAccessDeniedHandler() {
//        return new CommonAccessDeniedHandler();
//    }
//
//    @Bean
//    @Primary
//    public CommonAuthenticationEntryPoint getCommonAuthenticationEntryPoint() {
//        return new CommonAuthenticationEntryPoint();
//    }
//
//    /**
//     * 自定义的权限控制管理器
//     *
//     * @return
//     */
//    @Bean
//    @Primary
//    @Qualifier("customAccessDecisionManager")
//    public CustomAccessDecisionManager getAccessDecisionManager() {
//        return new CustomAccessDecisionManager();
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling().accessDeniedHandler(getCommonAccessDeniedHandler())
//                .authenticationEntryPoint(getCommonAuthenticationEntryPoint())
//                .and().httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // 放行URL
//                .authorizeRequests().antMatchers(RequestMatcherConstants.PERMIT_URLS).permitAll()
//                // 权限配置
//                .antMatchers(HttpAuthEnums.REGISTER_USER_REGISTER.geturi(), HttpAuthEnums.REGISTER_USER_TEST.geturi()).hasAuthority(HttpAuthEnums.REGISTER_USER_REGISTER.getAuthority())
//                .anyRequest().authenticated()
//                //跨域处理filter
//                .accessDecisionManager(getAccessDecisionManager())
//                .and().addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(tokenStore());
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtTokenEnhancer());
//    }
//
//    @Primary
//    @Bean
//    @Qualifier("RemoteTokenServices")
//    public XcRemoteTokenServices remoteTokenServices() {
//        XcRemoteTokenServices tokenService = new XcRemoteTokenServices(loadBalancerClient, serverApplicationName, checkTokenUrl);
//        tokenService.setClientId(clientId);
//        tokenService.setClientSecret(clientSecret);
//        return tokenService;
//    }
//
//    @Bean
//    protected JwtAccessTokenConverter jwtTokenEnhancer() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        Resource resource = new ClassPathResource("public.txt");
//        String publicKey = null;
//        try {
//            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        converter.setVerifierKey(publicKey);
//        return converter;
//    }
//
//}
