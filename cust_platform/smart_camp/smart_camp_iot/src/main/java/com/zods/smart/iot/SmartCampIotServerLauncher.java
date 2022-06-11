package com.zods.smart.iot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @description 智慧营区智能设备(电子围栏红外震动,动环设备，RFID设备)厂家协议接入服务
 * @author jianglong
 * @create 2022-06-11
 **/
@SpringBootApplication(scanBasePackages = {"com.zods.smart.iot.electronic.server"})
public class SmartCampIotServerLauncher {
    public static void main(String[] args) {
        SpringApplication.run(SmartCampIotServerLauncher.class, args);
    }
}
