package com.zods.flink.source.mysql.config;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @description MYSQL参数配置
 * @create 2019-03-01
 **/
@Data
@Builder
public class JdbcConfig implements Serializable {

    private String driverClassName;

    private String url;

    private String userName;

    private String passWord;

    private int initialSize;

    private int maxTotal;

    private int minIdle;

}
