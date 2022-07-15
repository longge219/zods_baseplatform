package com.zods.smart.iot.gunrfid.server.start;
import com.zods.smart.iot.gunrfid.server.properties.GunRifdServerProperties;
/**
 * @description QZ-RFID初始化TCP通信服务
 * @author jianglong
 * @create 2022-07-13
 **/
public class InitGunRfidServer {

    private GunRifdServerProperties gnRifdServerProperties;

    private BootstrapGunRfidServer bootstrapGunRfidServer;

    private Thread thread;

    public InitGunRfidServer(GunRifdServerProperties gnRifdServerProperties) {
        this.gnRifdServerProperties = gnRifdServerProperties;
    }
    /**
     * 初始化调用方法
     * */
    public void open()throws Exception{
        if(gnRifdServerProperties!=null){
            bootstrapGunRfidServer = new NettyBootstrapGunRfidServer();
            bootstrapGunRfidServer.setServerProperties(gnRifdServerProperties);
            bootstrapGunRfidServer.start();
        }
    }
    /**
     * 销毁时调用方法
     * */
    public void close()throws Exception{
        if(bootstrapGunRfidServer!=null){
            bootstrapGunRfidServer.shutdown();
            if(thread!=null && thread.isDaemon()){
                thread.interrupt();
            }
        }
    }

}
