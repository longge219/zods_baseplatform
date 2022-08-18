package com.zods.rule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
/**
 * @author jianglong
 * @version 1.0
 * @Description 规则引擎后台服务
 * @createDate 2022-07-28
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zods.rule.modules.*.**.dao"})
@EnableAsync
public class RuleEngineApplication {
    public static void main( String[] args ) {
        SpringApplication.run(RuleEngineApplication.class);
    }
}
