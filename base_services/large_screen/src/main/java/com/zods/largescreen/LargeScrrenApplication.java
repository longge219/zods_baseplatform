package com.zods.largescreen;
import com.zods.largescreen.common.annotation.enabled.EnabledGaeaConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
/**
 * @author jianglong
 * @version 1.0
 * @Description 可视化管理后台服务
 * @createDate 2022-06-20
 */
@EnabledGaeaConfiguration
@SpringBootApplication
@MapperScan(basePackages = {
        "com.zods.largescreen.modules.*.dao",
        "com.zods.largescreen.modules.*.**.dao",
        "com.zods.largescreen.*.module.*.dao"})
@EnableAsync
public class LargeScrrenApplication {
    public static void main( String[] args ) {
        SpringApplication.run(LargeScrrenApplication.class);
    }
}
