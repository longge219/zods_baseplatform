package com.zods.auth.auth.permission;
import com.zods.auth.common.token.McloudRemoteTokenServices;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Wangchao
 * @version 1.0
 * @Description 自定义的权限控制管理器
 * @createDate 2020/10/10 10:05
 */
@Slf4j
public class CustomAccessDecisionManager implements AccessDecisionManager {

    private McloudRemoteTokenServices tokenServices;

    public CustomAccessDecisionManager(McloudRemoteTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    @Override
    public void decide(Authentication auth, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        /**
         * 如果请求的资源不需要权限，则直接放行
         */
        if (configAttributes == null || configAttributes.size() <= 0) {
            return;
        }
        /**
         * 判断用户所拥有的权限是否是资源所需要的权限之一，如果是则放行，否则拦截
         */
        Iterator iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            String attribute = iterator.next().toString();
            // 1.开放接口直接放过
            if (attribute.trim().contains("permitAll")) {
                return;
            }
            // 2.非开放接口需要鉴权,判断是否权限是否匹配
            try {
                auth = tokenServices.loadAuthentication(getAuthorization((FilterInvocation) object));
                Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
                for (GrantedAuthority authority : authorities) {
                    if (attribute.trim().contains(authority.getAuthority())) {
                        return;
                    }
                }
            } catch (Exception e) {
                log.warn("parse grauthAttributes warn:" + e.getMessage(), e);
            }
        }
        throw new AccessDeniedException("没有权限");
    }

    /**
     * 获取token
     *
     * @param object
     * @return
     */
    private String getAuthorization(FilterInvocation object) {
        String token;
        try {
            token = object.getRequest().getHeader("Authorization");
            if (StringUtils.isBlank(token)) {
                token = object.getRequest().getParameter("accessToken");
            }
        } catch (Exception e) {
            token = object.getRequest().getParameter("accessToken");
        }
        return token;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
