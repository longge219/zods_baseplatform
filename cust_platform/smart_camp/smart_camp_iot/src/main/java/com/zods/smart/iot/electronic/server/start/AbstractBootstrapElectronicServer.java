package com.zods.smart.iot.electronic.server.start;
import com.zods.smart.iot.electronic.server.code.MyMessageDecoder;
import com.zods.smart.iot.electronic.server.code.MyMessageEncoder;
import com.zods.smart.iot.electronic.server.handler.ElectronicServerHandler;
import com.zods.smart.iot.electronic.server.properties.ElectronicProperties;
import com.zods.smart.iot.electronic.utils.SpringBeanUtils;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
/**
 * @description 抽象类 负责加载--编解码器---业务处理器
 * @author jianglong
 * @create 2022-06-11
 **/
public abstract class AbstractBootstrapElectronicServer implements BootstrapElectronicServer {
    /**
     * 设置过滤器链表
     * @param channelPipeline
     * @param electronicProperties  服务配置参数
     */
    protected  void initHandler(ChannelPipeline channelPipeline, ElectronicProperties electronicProperties)throws Exception{
        /**设置协议---编解码*/
        channelPipeline.addLast("decoder", new MyMessageDecoder());
        channelPipeline.addLast("encoder", new MyMessageEncoder());
        //设置空闲心跳空闲检测--channel
        channelPipeline.addLast(new IdleStateHandler(electronicProperties.getRead(), electronicProperties.getWrite(), electronicProperties.getReadAndWrite()));
        //设置MQTT默认处理--channel
        channelPipeline.addLast(new ElectronicServerHandler());
    }
}
