package com.zods.smart.iot.gunrfid.service.impl;
import com.zods.kafka.producer.KafkaProducerService;
import com.zods.smart.iot.common.utils.Bits;
import com.zods.smart.iot.common.utils.UnsignedNumber;
import com.zods.smart.iot.gunrfid.server.channel.GunRfidChannelManager;
import com.zods.smart.iot.gunrfid.server.protocal.*;
import com.zods.smart.iot.gunrfid.server.protocal.base.GunRfidLogin;
import com.zods.smart.iot.gunrfid.server.protocal.base.GunRfidOnline;
import com.zods.smart.iot.gunrfid.server.protocal.command.GunRfidMultiplePollingCommand;
import com.zods.smart.iot.gunrfid.server.protocal.command.GunRfidMultiplePollingStopCommand;
import com.zods.smart.iot.gunrfid.server.protocal.notice.GunRfidMultiplePollingNotice;
import com.zods.smart.iot.gunrfid.server.protocal.response.GunRfidGetAntResponse;
import com.zods.smart.iot.gunrfid.server.protocal.response.GunRfidMultiplePollingResponse;
import com.zods.smart.iot.gunrfid.server.protocal.response.GunRfidSetAntResponse;
import com.zods.smart.iot.gunrfid.service.GunRfidServerService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
/**
 * @author jianglong
 * @description 报文业务处理
 * @create 2018-07-10
 **/
@Service
@Slf4j
public class GunRfidServerServiceImpl implements GunRfidServerService {

    @Resource
    private KafkaProducerService kafkaProducerService;

    /**
     * @method:消息正确接收业务处理
     * @param:channel 通道
     * @param:packetHead 解译后数据包
     * @result: boolean
     */
    @Override
    public boolean successBusiness(ChannelHandlerContext ctx, GunRfidPacketHead packetHead) throws Exception {
        /**在线心跳信息*/
        if(packetHead instanceof GunRfidOnline){
            GunRfidOnline gunRfidOnline = (GunRfidOnline) packetHead;
            log.info("GUN-RFID收到心跳信息");
        } else if(packetHead instanceof GunRfidLogin){
            log.info("GUN-RFID收到登陆信息");
            /**登陆信息*/
            GunRfidLogin gunRfidLogin = (GunRfidLogin) packetHead;
            /**获取客户端IP*/
            InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
            String clientIp = ipSocket.getAddress().getHostAddress();
            if(!GunRfidChannelManager.getInstance().hasChannel(clientIp)){
                //保存连接channel
                GunRfidChannelManager.getInstance().addChannel(clientIp,ctx.channel());
                //发送多次轮询指令
                GunRfidMultiplePollingCommand mpCommand = new GunRfidMultiplePollingCommand();
                //ctx.writeAndFlush(mpCommand);
            } else {
                log.info("设备IP:" + clientIp + "已登陆..........");
            }
        }
        else if(packetHead instanceof GunRfidSetAntResponse){
            GunRfidSetAntResponse setAntResponse = (GunRfidSetAntResponse) packetHead;
            log.info("接收到GUN-RFID设置天线响应.......");
        }
        else if(packetHead instanceof GunRfidGetAntResponse){
            GunRfidGetAntResponse getAntResponse = (GunRfidGetAntResponse) packetHead;
            boolean[] aa = Bits.intToBits(UnsignedNumber.getUnsignedShort(getAntResponse.getAnts()),16);
            log.info("接收到GUN-RFID查询天线响应.......");
        }
        else if(packetHead instanceof GunRfidMultiplePollingNotice){
            log.info("接收到GUN-RFID多次轮询指令通知");
            GunRfidMultiplePollingNotice mpNotice = (GunRfidMultiplePollingNotice)packetHead;
            log.info("天线号：" + mpNotice.getAnt() + "识别到的EPC码：" + Bits.toHexString(mpNotice.getEpc(),mpNotice.getEpc().length));

        }
        else if(packetHead instanceof GunRfidMultiplePollingResponse){
            GunRfidMultiplePollingResponse mpResponse = (GunRfidMultiplePollingResponse) packetHead;
            log.info("接收到GUN-RFID多次轮询指令响应");
        }
        else if(packetHead instanceof GunRfidMultiplePollingStopCommand){
            GunRfidMultiplePollingStopCommand stopMpResponse = (GunRfidMultiplePollingStopCommand) packetHead;
            log.info("接收到GUN-RFID停止多次轮询指令响应");
        }
        else{
            log.info("GUN-RFID服务端暂不处理其他数据包协议");
        }
        return true;
    }

}