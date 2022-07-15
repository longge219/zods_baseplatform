package com.zods.smart.iot.gunrfid.service.impl;
import com.zods.kafka.producer.KafkaProducerService;
import com.zods.smart.iot.gunrfid.server.protocal.GunRfidPacketHead;
import com.zods.smart.iot.gunrfid.service.GunRfidServerService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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
        return true;
    }

}