package com.zods.largescreen.config;
import com.zods.largescreen.common.cache.CacheHelper;
import com.zods.largescreen.runner.ApplicationInitRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author jianglong
 * @version 1.0
 * @Description business配置类
 * @createDate 2022-06-20
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


    /**
     * 创建ehCacheCacheManager
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {

        return new EhCacheCacheManager();
    }
}
