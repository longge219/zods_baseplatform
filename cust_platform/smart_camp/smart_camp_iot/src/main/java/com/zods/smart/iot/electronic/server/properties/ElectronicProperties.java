package com.zods.smart.iot.electronic.server.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @description 服务端链接参数配置
 * @author jianglong
 * @create 2022-06-11
 **/
@ConfigurationProperties(prefix = "com.zods.electronic.server")
@Data
public class ElectronicProperties {

    private String server ; //服务名

    private String port ; //监听端口

    private boolean reuseaddr ;//地址复用，默认值False。有四种情况可以使用：(1).当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你希望启动的程序的socket2要占用该地址和端口，比如重启服务且保持先前端口。(2).有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。(3).单个进程绑定相同的端口到多个socket上，但每个socket绑定的ip地址不同。(4).完全相同的地址和端口的重复绑定。但这只用于UDP的多播，不用于TCP。

    private boolean tcpNodelay ; //TCP参数，立即发送数据，默认值为True（Netty默认为True而操作系统默认为False）。该值设置Nagle算法的启用，改算法将小的碎片数据连接成更大的报文来最小化所发送的报文的数量，如果需要发送一些较小的报文，则需要禁用该算法。Netty默认禁用该算法，从而最小化报文传输延时。

    private int backlog ; //Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝

    private  int  sndbuf ; //Socket参数，TCP数据发送缓冲区大小。

    private int revbuf ; //Socket参数，TCP数据接收缓冲区大小。

    private int read ; //读超时时间

    private int write ; //写超时时间

    private int readAndWrite ; //读写超时时间

    private int workThread; //用于处理与各个客户端连接的IO操作

}
