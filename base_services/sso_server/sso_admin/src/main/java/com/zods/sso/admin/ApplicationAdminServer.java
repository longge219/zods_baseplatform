package com.zods.sso.admin;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/1/4 10:11
 */
@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationAdminServer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationAdminServer.class, args);
    }

}
