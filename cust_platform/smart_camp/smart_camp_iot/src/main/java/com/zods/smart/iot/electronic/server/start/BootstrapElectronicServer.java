package com.zods.smart.iot.electronic.server.start;
import com.zods.smart.iot.electronic.server.properties.ElectronicProperties;
/**
 * @author jianglong
 * @description 电子围栏红外震动启动类接口
 * @create 2022-06-11
 **/
public interface BootstrapElectronicServer {

    void start() throws Exception;

    void shutdown() throws Exception;

    void setServerProperties(ElectronicProperties electronicProperties) throws Exception;
}
