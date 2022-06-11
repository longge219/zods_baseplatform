package com.zods.smart.iot.electronic.service;

import com.zods.smart.iot.electronic.server.protocal.PacketHead;

/**
 * @description 服务端报文业务处理接口
 * @author jianglong
 * @create 2018-03-20
 **/
public interface ElectronicServerService {

    /**
     * method:消息正确接收业务处理 param:PacketHead packetHead result: boolean
     */
    public boolean successBusiness(PacketHead packetHead) throws Exception;


}
