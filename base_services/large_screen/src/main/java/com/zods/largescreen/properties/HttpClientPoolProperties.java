package com.zods.largescreen.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;
/**
 * @author jianglong
 * @version 1.0
 * @Description HttpClientPool配置参数(java配置的优先级低于yml配置；如果yml配置不存在，会采用java配置)
 * @createDate 2022-06-20
 */
@Component
@ConfigurationProperties(prefix = "http.client.pool")
@Data
public class HttpClientPoolProperties {

    /**连接池的最大连接数*/
    private int maxTotalConnect;

    /**同路由的并发数*/
    private int maxConnectPerRoute;

    /**客户端和服务器建立连接超时，默认2s*/
    private int connectTimeout;

    /**指客户端从服务器读取数据包的间隔超时时间,不是总读取时间，默认30s*/
    private int readTimeout;

    private String charset;

    /**重试次数,默认2次*/
    private int retryTimes;

    /**从连接池获取连接的超时时间,不宜过长,单位ms*/
    private int connectionRequestTimout;

    /**针对不同的地址,特别设置不同的长连接保持时间,单位 s*/
    private int keepAliveTime;

    /**针对不同的地址,特别设置不同的长连接保持时间*/
    private Map<String,Integer> keepAliveTargetHost;


}
