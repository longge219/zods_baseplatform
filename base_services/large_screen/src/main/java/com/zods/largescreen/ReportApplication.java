package com.zods.largescreen;
import com.zods.largescreen.common.annotation.enabled.EnabledGaeaConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class ReportApplication {
    public static void main( String[] args ) {
        SpringApplication.run(ReportApplication.class);
    }
}
