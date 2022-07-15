package com.zods.smart.iot.gunrfid.server.start;
import com.zods.smart.iot.common.utils.SpringBeanUtils;
import com.zods.smart.iot.common.utils.ssl.SecureSocketSslContextFactory;
import com.zods.smart.iot.gunrfid.server.code.GunRfidMessageDecoder;
import com.zods.smart.iot.gunrfid.server.code.GunRfidMessageEncoder;
import com.zods.smart.iot.gunrfid.server.handler.GunRfidServerHandler;
import com.zods.smart.iot.gunrfid.server.properties.GunRifdServerProperties;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.SystemPropertyUtil;
import org.apache.commons.lang3.ObjectUtils;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.security.KeyStore;
/**
 * @description 抽象类 负责加载--编解码器---业务处理器
 * @author jianglong
 * @create 2022-07-13
 **/
public abstract class AbstractBootstrapGunRfidServer implements BootstrapGunRfidServer {
    /**
     * 设置过滤器链表
     * @param channelPipeline
     * @param gunRifdServerProperties  服务配置参数
     */
    protected  void initHandler(ChannelPipeline channelPipeline, GunRifdServerProperties gunRifdServerProperties)throws Exception{
        if(gunRifdServerProperties.isSsl()){
            if(!ObjectUtils.allNotNull(gunRifdServerProperties.getJksCertificatePassword(),gunRifdServerProperties.getJksFile(),gunRifdServerProperties.getJksStorePassword())){
                throw  new NullPointerException("SSL file and password is null");
            }
            //设置SSL--channel
            channelPipeline.addLast("ssl",initSsl(gunRifdServerProperties));
        }
        //设置协议处理器--channel
        intProtocolHandler(channelPipeline,gunRifdServerProperties);
        //设置空闲心跳空闲检测--channel
        channelPipeline.addLast(new IdleStateHandler(gunRifdServerProperties.getRead(), gunRifdServerProperties.getWrite(), gunRifdServerProperties.getReadAndWrite()));
        //设置MQTT默认处理--channel
        channelPipeline.addLast(SpringBeanUtils.getBean(GunRfidServerHandler.class));
    }

     /**设置协议---编解码*/
    private void intProtocolHandler(ChannelPipeline channelPipeline, GunRifdServerProperties gunRifdServerProperties)throws Exception{
            switch (gunRifdServerProperties.getProtocol()){
                    case "TCP232":
                        channelPipeline.addLast("encoder", new GunRfidMessageEncoder());
                        channelPipeline.addLast("decoder", new GunRfidMessageDecoder());
                        break;
                }
    }

   /**
    * 设置SSL
    * @param gunRifdServerProperties
    */
    private SslHandler initSsl(GunRifdServerProperties gunRifdServerProperties)throws Exception {
        String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        SSLContext serverContext;
        try {
            //
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(SecureSocketSslContextFactory.class.getResourceAsStream(gunRifdServerProperties.getJksFile()),gunRifdServerProperties.getJksStorePassword().toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks,gunRifdServerProperties.getJksCertificatePassword().toCharArray());
            serverContext = SSLContext.getInstance("TLS");
            serverContext.init(kmf.getKeyManagers(), null, null);
        } catch (Exception e) {
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }
        SSLEngine engine = serverContext.createSSLEngine();
        engine.setUseClientMode(false);
        return  new SslHandler(engine);
    }
}
