package com.zods.smart.iot.electronic.server.start;
import com.zods.smart.iot.electronic.server.properties.ElectronicProperties;
/**
 * @description 初始化电子围栏红外震动服务
 * @author jianglong
 * @create 2022-06-11
 **/
public class InitElectronicServer {

    private ElectronicProperties electronicProperties;

    private BootstrapElectronicServer bootstrapElectronicServer;

    private Thread thread;

    public InitElectronicServer(ElectronicProperties electronicProperties) {
        this.electronicProperties = electronicProperties;
    }

    /**初始化调用方法*/
    public void open()throws Exception{
        if(electronicProperties!=null){
            bootstrapElectronicServer = new BootstrapElectronicServerImpl();
            bootstrapElectronicServer.setServerProperties(electronicProperties);
            bootstrapElectronicServer.start();
        }
    }
    /**销毁时调用方法*/
    public void close()throws Exception{
        if(bootstrapElectronicServer!=null){
            bootstrapElectronicServer.shutdown();
            if(thread!=null && thread.isDaemon()){
                thread.interrupt();
            }
        }
    }

}
