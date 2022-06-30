package com.zods.smart.iot.electronic.service.impl;
import com.zods.kafka.producer.KafkaProducerService;
import com.zods.plugins.db.utils.DateUtils;
import com.zods.smart.iot.common.constant.TopicConst;
import com.zods.smart.iot.common.topic.ElecData;
import com.zods.smart.iot.common.topic.InfReadData;
import com.zods.smart.iot.common.topic.VibRationData;
import com.zods.smart.iot.electronic.server.protocal.*;
import com.zods.smart.iot.electronic.service.ElectronicServerService;
import com.zods.smart.iot.modules.device.entity.Device;
import com.zods.smart.iot.modules.device.service.DeviceService;
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
public class ElectronicServerServiceImpl implements ElectronicServerService {

    @Resource
    private KafkaProducerService kafkaProducerService;

    @Resource
    private DeviceService deviceService;

    /**
     * @method:消息正确接收业务处理
     * @param:channel 通道
     * @param:packetHead 解译后数据包
     * @result: boolean
     */
    @Override
    public boolean successBusiness(ChannelHandlerContext ctx, ElectronicPacketHead packetHead) throws Exception {
        /**在线信息*/
        if(packetHead instanceof ElectronicOnline){
            ElectronicOnline online = (ElectronicOnline) packetHead;
            /**应答在线信息*/
            ElectronicOnlineReturn onlineReturn = new ElectronicOnlineReturn();
            onlineReturn.setPakcetLen(8);
            onlineReturn.setHostAddress(online.getHostAddress());
            onlineReturn.setEquipAddress(online.getEquipAddress());
            onlineReturn.setUserGroupH(online.getUserGroupH());
            onlineReturn.setUserGroupL(online.getUserGroupL());
            /**返回客户端在线-应答信息*/
            onlineReturn.setRemoteAddress(online.getRemoteAddress());
            ctx.channel().writeAndFlush(onlineReturn);
            return true;
        } else if(packetHead instanceof ElectronicEquipStatus){
            /**设备状态*/
            ElectronicEquipStatus equipStatus = (ElectronicEquipStatus) packetHead;
            /**设备状态应答*/
            ElectronicEquipStatusReturn equipStatusReturn = new ElectronicEquipStatusReturn();
            equipStatusReturn.setPakcetLen(8);
            equipStatusReturn.setHostAddress(equipStatus.getHostAddress());
            equipStatusReturn.setEquipAddress(equipStatus.getEquipAddress());
            equipStatusReturn.setUserGroupH(equipStatus.getUserGroupH());
            equipStatusReturn.setUserGroupL(equipStatus.getUserGroupL());
            /**返回客户端状态-应答信息*/
            equipStatusReturn.setRemoteAddress(equipStatus.getRemoteAddress());
            ctx.channel().writeAndFlush(equipStatusReturn);
            /**设备状态业务处理*/
            try{
                if(equipStatus.getEquipAddress() <9){
                    /**电子围栏处理*/
                    doFenceStatus(equipStatus);
                }else if(8 < equipStatus.getEquipAddress()  && equipStatus.getEquipAddress() < 17){
                    /**红外震动处理*/
                    for(int i=1;i<9;i++){
                        // equipStatus：设备状态 ； i:防区号
                        infraredVibration(equipStatus,i);
                    }
                } else{
                    log.info("暂不处理其他设备地址的消息："+ equipStatus.getEquipAddress());
                }
            }catch(Exception e){
                log.error("处理电子围栏-设备状态业务处理出错");
                e.printStackTrace();
            }
        }else{
            log.info("电子围栏服务端暂不处理其他数据包协议");
        }
        return true;
    }

    /**电子围栏报警处理*/
    private void doFenceStatus(ElectronicEquipStatus equipStatus) throws  Exception{
        String deviceCode = "fence"+ String.valueOf(equipStatus.getHostAddress())+ String.valueOf(equipStatus.getEquipAddress());
        Device device = deviceService.selectElecDevice(deviceCode);
        if(device != null){
            /**更新设备状态*/
            deviceService.updateDeviceOnlineStatusByDeviceCode(device.getDeviceCode(),1);
            /**判断设备报警-恢复逻辑*/
            //防区报警---布防(bit位(0-7) 0:正常 1:报警)
            String zoneAlarm = Integer.toBinaryString((equipStatus.getZoneAlarm() & 0xFF) + 0x100).substring(1);
            log.info("电子围栏编号："+deviceCode+"发送了防区报警信息："+ zoneAlarm);
            //防区撤布防(bit位(0-7) 0:布防 1:撤防)
            String deploymentStatus = Integer.toBinaryString((equipStatus.getDeploymentStatus() & 0xFF) + 0x100).substring(1);
            log.info("电子围栏编号："+deviceCode+"发送了防区布防信息："+deploymentStatus);
            ElecData elecData = new ElecData();
            elecData.setDeviceCode(deviceCode);
            elecData.setDateStr(DateUtils.getCurrentDate());
            /**电子围栏是第一个防区且用设备地址区分（11、12、13、14、15、16、17、18）*/
            if(zoneAlarm.charAt(7)== '1'){
                elecData.setStatus(1);//0:报警修复；1:报警
            }else{
                elecData.setStatus(0);//0:报警修复；1:报警
            }
            kafkaProducerService.sendMessage(TopicConst.ELECTRIC_FENCE_TOPIC,elecData);
        }else{
            log.info("电子围栏不处理未添加设备的报警逻辑" + "设备编号：" + deviceCode);
        }
    }


    /**红外震动报警处理
     * equipStatus  设备状态数据
     * int  防区号
     * */
    private void infraredVibration(ElectronicEquipStatus equipStatus, int i) throws  Exception{
        String deviceCode = "hwzd" + String.valueOf(equipStatus.getHostAddress())+ String.valueOf(equipStatus.getEquipAddress()) + String.valueOf(i);
        Device device = deviceService.selectElecDevice(deviceCode);
        if(device != null){
            /**更新设备状态*/
            /**更新设备状态*/
            device.setIsOnline(1);
            deviceService.update(device);
            /**判断设备报警-恢复逻辑*/
            //防区报警---布防(bit位(0-7) 0:正常 1:报警)
            String zoneAlarm = Integer.toBinaryString((equipStatus.getZoneAlarm() & 0xFF) + 0x100).substring(1);
            log.info("红外震动编号："+deviceCode+"发送了防区报警信息："+ zoneAlarm);
            //防区撤布防(bit位(0-7) 0:布防1:撤防)
            //String deploymentStatus = Integer.toBinaryString((equipStatus.getDeploymentStatus() & 0xFF) + 0x100).substring(1);
            //防区实时状态--未布防(bit位(0-7) 0:正常 1:触发)
            //String timingStatus = Integer.toBinaryString((equipStatus.getTimingStatus() & 0xFF) + 0x100).substring(1);
            /**18-----红外报警*/
            if(device.getDeviceType().equals("18")){
                //判定Bit0是否报警(0:正常 1:报警; 0:正常 1:触发)
                InfReadData infReadData = new InfReadData();
                infReadData.setDeviceCode(deviceCode);
                infReadData.setDateStr(DateUtils.getCurrentDate());
                if(zoneAlarm.charAt(8-i)== '1'){
                    infReadData.setStatus(1);//0:报警修复；1:报警
                }else{
                    infReadData.setStatus(0);//0:报警修复；1:报警
                }
                kafkaProducerService.sendMessage(TopicConst.ROOM_INFRARED_TOPIC,infReadData);

            }
            /**19--震动报警*/
            else if(device.getDeviceType().equals("19")){
                //判定Bit0是否报警(0:正常 1:报警; 0:正常 1:触发)
                VibRationData vibRationData = new VibRationData();
                vibRationData.setDeviceCode(deviceCode);
                vibRationData.setDateStr(DateUtils.getCurrentDate());
                if(zoneAlarm.charAt(8-i)== '1'){
                    vibRationData.setStatus(1);//0:报警修复；1:报警
                }else{
                    vibRationData.setStatus(0);//0:报警修复；1:报警
                }
                kafkaProducerService.sendMessage(TopicConst.WALL_VIBRATION_TOPIC,vibRationData);
            }

        }else{
            log.info("报警主机不处理未添加设备的报警逻辑" + "设备编号：" + deviceCode);
        }
    }
}