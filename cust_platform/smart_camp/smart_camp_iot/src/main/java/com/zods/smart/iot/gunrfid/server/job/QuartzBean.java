package com.zods.smart.iot.gunrfid.server.job;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description 系统启动通信配置参数
 * @author jianglong
 * @create 2021-08-23
 **/
@Component
@ConfigurationProperties(prefix ="quartz.param")
@Data
public class QuartzBean {

    /**海康设备-配置参数*/
    private int gunRfidEpcScan;//枪支RFID扫描上传Kafka

}
