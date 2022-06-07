package com.zods.mqtt.sever;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @description 工程启动类
 * @author jianglong
 * @create 2019-09-09
 **/
@SpringBootApplication(scanBasePackages = {"com.zods.mqtt.sever"})
@MapperScan(basePackages = {"com.zods.mysql.dao", "com.zods.mqtt.sever.business.dao","com.zods.mqtt.sever.register.dao"})
public class MqttServerLauncher {
    public static void main(String[] args) {
        SpringApplication.run(MqttServerLauncher.class, args);
    }
}
