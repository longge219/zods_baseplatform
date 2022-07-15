package com.zods.smart.iot.gunrfid.server.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @description 服务端链接参数配置
 * @author jianglong
 * @create 2022-07-13
 **/
@ConfigurationProperties(prefix ="com.zods.gunrfid.server")
@Data
public class GunRifdServerProperties {

    private String protocol;

    private String port ;

    private String serverName ;

    private boolean keepalive ;

    private boolean reuseaddr ;


    private boolean tcpNodelay ;

    private int backlog ;

    private  int  sndbuf ;

    private int revbuf ;

    private int read ;

    private int write ;

    private int readAndWrite ;

    private boolean ssl ;

    private String jksFile;

    private String jksStorePassword;

    private String jksCertificatePassword;

    private int bossThread;

    private int workThread;

}
