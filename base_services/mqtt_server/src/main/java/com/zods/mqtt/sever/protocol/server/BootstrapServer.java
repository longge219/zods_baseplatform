package com.zods.mqtt.sever.protocol.server;
import com.zods.mqtt.sever.protocol.bean.InitBean;
/**
 * @author jianglong
 * @description 启动类接口
 * @create 2019-03-01
 **/
public interface BootstrapServer {

    void start() throws Exception;

    void shutdown() throws Exception;

    void setServerBean(InitBean serverBean) throws Exception;
}
