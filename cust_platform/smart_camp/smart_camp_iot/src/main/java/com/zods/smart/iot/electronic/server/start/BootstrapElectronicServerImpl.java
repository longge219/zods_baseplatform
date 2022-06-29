package com.zods.smart.iot.electronic.server.start;
import com.zods.smart.iot.common.utils.IpUtils;
import com.zods.smart.iot.electronic.server.properties.ElectronicProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @description 抽象类 负责加载--编解码器---业务处理器
 * @author jianglong
 * @create 2022-06-11
 **/
@Slf4j
public class BootstrapElectronicServerImpl extends AbstractBootstrapElectronicServer {

    private ElectronicProperties electronicProperties; //启动参数

    private Bootstrap bootstrap; //UDP服务端启动类

    private NioEventLoopGroup workGroup; //处理与各个客户端连接的 IO 操作线程池


    /**启动服务*/
    @Override
    public void start() throws Exception {
        initEventPool();//初始化EnentPool
        /*由于我们用的是UDP协议，所以要用NioDatagramChannel来创建*/
        bootstrap.group(workGroup)
                /**bossGroup线程池设置*/
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.SO_REUSEADDR, electronicProperties.isReuseaddr()) //地址复用
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_RCVBUF, electronicProperties.getRevbuf()) //数据接收缓冲区大小
                .option(ChannelOption.SO_SNDBUF, electronicProperties.getSndbuf()) // 设置UDP写缓冲区大小
                .handler(new LoggingHandler(LogLevel.WARN)) //设置日志级别
                /**handler配置*/
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                        initHandler(nioDatagramChannel.pipeline(), electronicProperties);
                    }
                });
                try{
                    //没有接受客户端连接的过程，监听本地端口即可
                    bootstrap.bind(Integer.valueOf(electronicProperties.getPort())).addListener((ChannelFutureListener) channelFuture -> {
                        if (channelFuture.isSuccess()) {
                            log.info("电子围栏红外震动服务端启动成功【" + IpUtils.getHost() + ":" + electronicProperties.getPort() + "】");
                        } else {
                            log.error("电子围栏红外震动服务端启动失败【" + IpUtils.getHost() + ":" + electronicProperties.getPort() + "】,堆栈信息如下：");
                            channelFuture.cause().printStackTrace();
                        }
                    }).sync().channel().closeFuture().sync().await(); //让线程进入wait状态，也就是main线程暂时不会执行到finally里面，nettyserver也持续运行，如果监听到关闭事件，可以优雅的关闭通道和nettyserver
                }catch (InterruptedException e){
                    log.error("电子围栏红外震动服务端启动失败");
                    e.printStackTrace();
                }finally {
                    workGroup.shutdownGracefully();
                }
    }

    /**关闭服务*/
    @Override
    public void shutdown() throws Exception {
        if (workGroup != null) {
            try {
                // 优雅关闭
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.error("服务端关闭资源失败【{}:{}】", IpUtils.getHost(), electronicProperties.getPort());
            }
        }
    }

    @Override
    public void setServerProperties(ElectronicProperties electronicProperties) throws  Exception{
        this.electronicProperties =electronicProperties;
    }

    /**初始化EnentPool*/
    private void initEventPool() throws Exception {
        /*和tcp的不同，udp没有接受连接的说法，所以即使是接收端，也使用Bootstrap*/
        bootstrap = new Bootstrap();
        workGroup = new NioEventLoopGroup(electronicProperties.getWorkThread(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "WORK_" + index.incrementAndGet());
            }
        });
    }
}

