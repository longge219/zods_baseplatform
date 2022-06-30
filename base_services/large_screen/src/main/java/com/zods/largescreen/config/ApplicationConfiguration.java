package com.zods.largescreen.config;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.zods.largescreen.common.cache.CacheHelper;
import com.zods.largescreen.common.cache.RedisCacheHelper;
import com.zods.largescreen.common.curd.mapper.handler.MybatisPlusMetaObjectHandler;
import com.zods.largescreen.common.curd.mapper.injected.CustomSqlInjector;
import com.zods.largescreen.common.event.listener.ExceptionApplicationListener;
import com.zods.largescreen.common.utils.ApplicationContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jianglong
 * @version 1.0
 * @Description 启动配置加载
 * @createDate 2022-06-20
 */
@Configuration
@MapperScan(basePackages = {"com.zods.largescreen.modules.*.dao", "com.zods.largescreen.modules.*.**.dao"})
public class ApplicationConfiguration {

    @Bean
    public EhCacheCache ehCacheCache() {
        return (EhCacheCache) ehCacheCacheManager().getCache("reportCache");
    }

    /**创建ehCacheCacheManager*/
    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {

        return new EhCacheCacheManager();
    }

    @Bean
    public ApplicationContextUtils applicationContextUtils() {
        return new ApplicationContextUtils();
    }


    @Bean(name = "redisCacheHelper")
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
}
