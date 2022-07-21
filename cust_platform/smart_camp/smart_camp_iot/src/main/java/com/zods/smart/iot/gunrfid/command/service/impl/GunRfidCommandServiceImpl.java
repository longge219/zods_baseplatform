package com.zods.smart.iot.gunrfid.command.service.impl;
import com.zods.smart.iot.gunrfid.command.service.GunRfidCommandService;
import com.zods.smart.iot.gunrfid.server.channel.GunRfidChannelManager;
import com.zods.smart.iot.gunrfid.server.protocal.command.GunRfidGetAntCommand;
import com.zods.smart.iot.gunrfid.server.protocal.command.GunRfidMultiplePollingCommand;
import com.zods.smart.iot.gunrfid.server.protocal.command.GunRfidMultiplePollingStopCommand;
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
    public boolean setAnts(String deviceIp,String ants){
        if(GunRfidChannelManager.getInstance().hasChannel(deviceIp)){
            GunRfidSetAntCommand gunRfidSetAntCommand  = new GunRfidSetAntCommand();
            int a = Integer.parseInt(ants, 2);
            gunRfidSetAntCommand.setAnts((short)Integer.parseInt(ants, 2));
            GunRfidChannelManager.getInstance().getChannel(deviceIp).writeAndFlush(gunRfidSetAntCommand);
        }else{
            log.info("发送设置天线指令-设备IP: "+ deviceIp + "未连接");
            return false;
        }
        return true;
    }

    /**
     * @description 查询天线
     * @param deviceIp:读卡器IP
     * */
    public boolean selectAnts(String deviceIp){
        if(GunRfidChannelManager.getInstance().hasChannel(deviceIp)){
            GunRfidGetAntCommand gunRfidGetAntCommand = new GunRfidGetAntCommand();
            GunRfidChannelManager.getInstance().getChannel(deviceIp).writeAndFlush(gunRfidGetAntCommand);
        }else{
            log.info("发送查询天线指令-设备IP: "+ deviceIp + "未连接");
            return false;
        }
        return true;
    }

    /**
     * @description 多次轮询接口
     * @param deviceIp:读卡器IP
     * @param cnt:轮询次数 CNT(范围0-65535，当值为65535则为无限循环)
     * */
    public boolean multiplePolling(String deviceIp, int cnt){
        if(GunRfidChannelManager.getInstance().hasChannel(deviceIp)){
            //发送多次轮询指令
            GunRfidMultiplePollingCommand mpCommand = new GunRfidMultiplePollingCommand();
            mpCommand.setCnt((short)cnt);
            GunRfidChannelManager.getInstance().getChannel(deviceIp).writeAndFlush(mpCommand);
        }else{
            log.info("发送轮询指令-设备IP: "+ deviceIp + "未连接");
            return false;
        }
        return true;
    }

    /**
     * @description 停止轮询接口
     * @param deviceIp:读卡器IP
     * */
    public boolean stopMultiplePolling(String deviceIp){
        if(GunRfidChannelManager.getInstance().hasChannel(deviceIp)){
            GunRfidMultiplePollingStopCommand gunRfidMultiplePollingStopCommand = new GunRfidMultiplePollingStopCommand();
            GunRfidChannelManager.getInstance().getChannel(deviceIp).writeAndFlush(gunRfidMultiplePollingStopCommand);
        }else{
            log.info("发送停止轮询指令-设备IP: "+ deviceIp + "未连接");
            return false;
        }
        return true;
    }
}
