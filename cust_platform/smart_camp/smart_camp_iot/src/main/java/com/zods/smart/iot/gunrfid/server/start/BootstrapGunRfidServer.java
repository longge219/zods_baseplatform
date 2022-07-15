package com.zods.smart.iot.gunrfid.server.start;
import com.zods.smart.iot.gunrfid.server.properties.GunRifdServerProperties;
/**
 * @author jianglong
 * @description QZ-RFID通信服务启动接口
 * @create 2022-07-13
 **/
public interface BootstrapGunRfidServer {

    void start() throws Exception;

    void shutdown() throws Exception;

    void setServerProperties(GunRifdServerProperties gunRifdServerProperties) throws Exception;
}
