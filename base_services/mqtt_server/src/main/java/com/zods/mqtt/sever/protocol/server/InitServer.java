package com.zods.mqtt.sever.protocol.server;
import com.zods.mqtt.sever.protocol.bean.InitBean;
/**
 * @description 初始化服务
 * @author jianglong
 * @create 2019-03-01
 **/
public class InitServer {

    private InitBean serverBean;
    
    private BootstrapServer bootstrapServer;

    private Thread thread;

    public InitServer(InitBean serverBean) {
        this.serverBean = serverBean;
    }
    /**
     * 初始化调用方法
     * */
    public void open()throws Exception{
        if(serverBean!=null){
            bootstrapServer = new NettyBootstrapServer();
            bootstrapServer.setServerBean(serverBean);
            bootstrapServer.start();
        }
    }
    /**
     * 销毁时调用方法
     * */
    public void close()throws Exception{
        if(bootstrapServer!=null){
            bootstrapServer.shutdown();
            if(thread!=null && thread.isDaemon()){
                thread.interrupt();
            }
        }
    }

}
