package com.zods.plugins.zods;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.zods.plugins.zods.bean.ResponseBean;
import com.zods.plugins.zods.cache.CacheHelper;
import com.zods.plugins.zods.cache.RedisCacheHelper;
import com.zods.plugins.zods.config.MybatisPlusMetaObjectHandler;
import com.zods.plugins.zods.curd.mapper.injected.CustomSqlInjector;
import com.zods.plugins.zods.event.listener.ExceptionApplicationListener;
import com.zods.plugins.zods.holder.UserContentHolder;
import com.zods.plugins.zods.holder.UserContext;
import com.zods.plugins.zods.i18.MessageLocaleResolver;
import com.zods.plugins.zods.i18.MessageSourceHolder;
import com.zods.plugins.zods.init.InitRequestUrlMappings;
import com.zods.plugins.zods.intercept.AccessKeyInterceptor;
import com.zods.plugins.zods.utils.ApplicationContextUtils;
import com.zods.plugins.zods.utils.JwtBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties({GaeaProperties.class})
public class GaeaAutoConfiguration {
    public GaeaAutoConfiguration() {
    }

    @Bean
    public ApplicationContextUtils applicationContextUtils() {
        return new ApplicationContextUtils();
    }

    @Bean
    public JwtBean jwtBean(GaeaProperties gaeaProperties) {
        return new JwtBean(gaeaProperties);
    }

    @Bean
    @ConditionalOnClass({RedisAutoConfiguration.class})
    @ConditionalOnMissingBean
    public CacheHelper cacheHelper() {
        return new RedisCacheHelper();
    }

    @Bean
    public ExceptionApplicationListener exceptionApplicationListener() {
        return new ExceptionApplicationListener();
    }

    @Configuration
    @ConditionalOnClass({MybatisPlusAutoConfiguration.class})
    public class GaeaMybatisPlusAutoConfiguration {
        public GaeaMybatisPlusAutoConfiguration() {
        }

        @Bean
        public OptimisticLockerInterceptor optimisticLockerInterceptor() {
            return new OptimisticLockerInterceptor();
        }

        @Bean
        public CustomSqlInjector customSqlInjector() {
            return new CustomSqlInjector();
        }

        @Bean
        public PaginationInterceptor paginationInterceptor() {
            return new PaginationInterceptor();
        }

        @Bean
        @ConditionalOnMissingBean({MetaObjectHandler.class})
        public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
            return new MybatisPlusMetaObjectHandler();
        }
    }

    @Configuration
    @ConditionalOnClass({WebMvcConfigurer.class})
    public static class GaeaWebMvcConfigurer implements WebMvcConfigurer {
        public GaeaWebMvcConfigurer() {
        }

        public void addInterceptors(InterceptorRegistry registry) {
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(new AccessKeyInterceptor());
            interceptorRegistration.addPathPatterns(new String[]{"/**"});
        }
    }

    @Configuration
    @ConditionalOnClass({WebMvcConfigurer.class})
    @ComponentScan({"com.anji.plus.gaea.controller", "com.anji.plus.gaea.exception.advice"})
    public static class WebGaeaAutoConfiguration {
        public WebGaeaAutoConfiguration() {
        }

        @Bean
        public InitRequestUrlMappings initRequestUrlMappings() {
            return new InitRequestUrlMappings();
        }

        @Bean
        public FilterRegistrationBean registrationBean(JwtBean jwtBean) {
            FilterRegistrationBean registrationBean = new FilterRegistrationBean();
            registrationBean.setFilter((request, response, chain) -> {
                if (request instanceof HttpServletRequest) {
                    HttpServletRequest httpServletRequest = (HttpServletRequest)request;
                    String authorization = httpServletRequest.getHeader("Authorization");
                    String orgCode = httpServletRequest.getHeader("orgCode");
                    String sysCode = httpServletRequest.getHeader("systemCode");
                    String locale = httpServletRequest.getHeader("locale");
                    UserContext userContext = UserContentHolder.getContext();
                    if (StringUtils.isNotBlank(locale)) {
                        userContext.setLocale(Locale.forLanguageTag(locale));
                    }

                    if (StringUtils.isNotBlank(authorization)) {
                        try {
                            String username = jwtBean.getUsername(authorization);
                            Integer userType = jwtBean.getUserType(authorization);
                            String tenant = jwtBean.getTenant(authorization);
                            userContext.setUsername(username);
                            userContext.setType(userType);
                            userContext.setTenantCode(tenant);
                            MDC.put("loginName", username);
                            if (StringUtils.isNotBlank(orgCode)) {
                                userContext.setOrgCode(orgCode);
                            }

                            if (StringUtils.isNotBlank(sysCode)) {
                                userContext.setSysCode(sysCode);
                            }

                            userContext.getParams().put("orgCode", orgCode);
                        } catch (Exception var13) {
                            ResponseBean responseBean = ResponseBean.builder().code("500").message("The Token has expired").build();
                            response.getWriter().print(JSONObject.toJSONString(responseBean));
                            return;
                        }
                    }
                }

                chain.doFilter(request, response);
                UserContentHolder.clearContext();
            });
            registrationBean.addUrlPatterns(new String[]{"/*"});
            registrationBean.setName("userOrgCodeFilter");
            registrationBean.setOrder(-2147483548);
            return registrationBean;
        }

        @Configuration
        @ConditionalOnClass({LocaleResolver.class})
        @ConditionalOnMissingBean({MessageLocaleResolver.class})
        public class MessageI18AutoConfiguration {
            public MessageI18AutoConfiguration() {
            }

            @Bean
            public MessageLocaleResolver localeResolver() {
                return new MessageLocaleResolver();
            }

            @Bean
            public MessageSourceHolder messageSourceHolder() {
                return new MessageSourceHolder();
            }
        }
    }
}
