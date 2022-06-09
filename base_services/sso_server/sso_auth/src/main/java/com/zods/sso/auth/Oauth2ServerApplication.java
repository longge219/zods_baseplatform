package com.zods.sso.auth;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import zom.zods.exception.annotation.EnableGlobalDispose;
/**
 * @author Wangchao
 * @version 1.0
 * @Description 授权服务器
 * @createDate 2020/09/09 14:45
 */
@EnableGlobalDispose
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.zods.sso.auth.dao"})
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }
}
