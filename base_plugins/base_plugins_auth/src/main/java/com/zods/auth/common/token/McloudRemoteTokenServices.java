package com.zods.auth.common.token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import zom.zods.exception.enums.HandlerExceptionEnums;
import zom.zods.exception.exception.category.McloudHandlerException;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 配置oauth2认证中心token认证URL
 * @createDate 2021/1/19 16:36
 */
@Slf4j
public class McloudRemoteTokenServices extends RemoteTokenServices {

    private LoadBalancerClient loadBalancerClient;

    private String serverApplicationName;

    private String checkTokenUrl;

    public McloudRemoteTokenServices(LoadBalancerClient loadBalancerClient, String serverApplicationName, String checkTokenUrl) {
        this.loadBalancerClient = loadBalancerClient;
        this.serverApplicationName = serverApplicationName;
        this.checkTokenUrl = checkTokenUrl;
    }

    /**
     * 访问认证服务器鉴权,给予5次异常重试机制
     *
     * @param accessToken
     * @return
     * @throws AuthenticationException
     * @throws InvalidTokenException
     */
    @Retryable(value = {McloudHandlerException.class}, maxAttempts = 5)
    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        try {
            super.setCheckTokenEndpointUrl(getCheckTokenUrl());
        } catch (Exception e) {
            throw new McloudHandlerException(HandlerExceptionEnums.EXCEPTION.message);
        }
        return super.loadAuthentication(accessToken);
    }

    /**
     * 获取oauth2认证中心token认证URL
     *
     * @return
     */
    private String getCheckTokenUrl() {
        ServiceInstance serviceInstance = loadBalancerClient.choose(serverApplicationName);
        if (null == serviceInstance) {
            throw new IllegalArgumentException("auth-server is not running , please check it.");
        }
        return String.format("http://%s:%s/%s", serviceInstance.getHost(), serviceInstance.getPort(), checkTokenUrl);
    }
}
