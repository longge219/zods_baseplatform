package com.zods.smart.iot.gunrfid.command.service.impl;
import com.zods.smart.iot.gunrfid.command.service.GunRfidCommandService;
import com.zods.smart.iot.gunrfid.server.channel.GunRfidChannelManager;
import com.zods.smart.iot.gunrfid.server.protocal.command.GunRfidSetAntCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @desc GUN-RFID读卡器-服务接口实现
 * @author jianglong
 * @date 2022-07-20
 **/
@Service
@Slf4j
public class GunRfidCommandServiceImpl implements GunRfidCommandService {
    /**
     * @description 设置天线
     * @param deviceIp:读卡器IP
     * @param ants:天线号集，位表示天线是否启用(0:不启用，1启用)，共16位，最多16个天线。例:0000000000000011代表12号天线启用
     * */
    public String setAnts(String deviceIp,String ants){
        if(GunRfidChannelManager.getInstance().hasChannel(deviceIp)){
            GunRfidSetAntCommand gunRfidSetAntCommand  = new GunRfidSetAntCommand();
            int a = Integer.parseInt(ants, 2);
            gunRfidSetAntCommand.setAnts((short)Integer.parseInt(ants, 2));
            GunRfidChannelManager.getInstance().getChannel(deviceIp).writeAndFlush(gunRfidSetAntCommand);
        }else{
            return "设备未连接";
        }
        return "success";
    }
}
