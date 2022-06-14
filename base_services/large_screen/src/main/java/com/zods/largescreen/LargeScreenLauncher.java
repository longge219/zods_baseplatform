package com.zods.largescreen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @description 大屏服务
 * @author jianglong
 * @create 2022-06-11
 **/
@SpringBootApplication(scanBasePackages = {"com.zods.largescrren"})
public class LargeScreenLauncher {
    public static void main(String[] args) {
        SpringApplication.run(LargeScreenLauncher.class, args);
    }
}
