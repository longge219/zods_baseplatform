package com.zods.flink.sink.mysql.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.io.Serializable;
/**
 * @author jianglong
 * @description MYSQL参数配置
 * @create 2019-03-01
 **/

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class MysqlConfig implements Serializable {

    private String driverClassName;

    private String url;

    private String userName;

    private String passWord;

    private int initialSize;

    private int maxTotal;

    private int minIdle;

}
