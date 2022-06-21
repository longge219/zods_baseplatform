package com.zods.largescreen.config;
import com.zods.largescreen.runner.ApplicationInitRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * business配置类
 * @author lr
 * @since 2021-04-08
 */
@Configuration
@MapperScan(basePackages = {
        "com.zods.largescreen.modules.*.dao",
        "com.zods.largescreen.modules.*.**.dao"
})
public class BusinessAutoConfiguration {

    /**
     * 系统启动完执行
     * @return
     */
    @Bean
    public ApplicationInitRunner applicationInitRunner() {
        return new ApplicationInitRunner();
    }


    @Bean
    public EhCacheCache ehCacheCache() {
        return (EhCacheCache) ehCacheCacheManager().getCache("reportCache");
    }

    /**
     * 创建ehCacheCacheManager
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {

        return new EhCacheCacheManager();
    }
}