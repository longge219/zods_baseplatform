package com.zods.mqtt.sever.consumer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.business.service.DeviceCmdInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CmdDownwardsConsumer {

    @Autowired
    private DeviceCmdInfoService deviceCmdInfoService;

    @KafkaListener(topics = "${topic.cmdDownWardsTopic}",groupId = "IOM_CMD_DOWNWARDS_GROUP")
    public void monitor(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("下行指令kafka消费参数----->topic:{},key:{},offset:{},partition:{}",
                record.topic(), record.key(), record.offset(), record.partition());
        String value = record.value();
        if (JSON.isValid(value)) {
            JSONObject object = JSON.parseObject(value);
            String body = object.getString("body");
            String deviceId = object.getString("deviceId");
            try {
                log.info("接收到下行消息，发送命令{}到设备{}", body, deviceId);
                deviceCmdInfoService.sendCmd(object);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("下行命令" + body + "发送到设备deviceId: " + deviceId + "失败", e);
            } finally {
                ack.acknowledge();
            }
        }
    }

    @KafkaListener(topics = "${topic.firmwareUpgradeTopic}",groupId = "IOM_FIRMWARE_UPGRADE_GROUP")
    public void monitorUpgrade(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("平台下发升级固件指令kafka消费参数----->topic:{},key:{},offset:{},partition:{}",
                record.topic(), record.key(), record.offset(), record.partition());
        String value = record.value();
        if (JSON.isValid(value)) {
            JSONObject object = JSON.parseObject(value);
            String body = object.getString("body");
            String deviceId = object.getString("deviceId");
            try {
                log.info("平台下发到固件指令:{}", body);
                deviceCmdInfoService.firmWareUpgrade(object);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("平台下发到固件指令" + body + "发送到设备deviceId: " + deviceId + "失败", e);
            } finally {
                ack.acknowledge();
            }
        }
    }

    @KafkaListener(topics = "${topic.firmwareSupportSizeTopic}",groupId = "IOM_FIRMWARE_SUPPORT_GROUP")
    public void monitorFirmware(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("设备上报待升级固件大小kafka消费参数----->topic:{},key:{},offset:{},partition:{}",
                record.topic(), record.key(), record.offset(), record.partition());
        String value = record.value();
        if (JSON.isValid(value)) {
            JSONObject object = JSON.parseObject(value);
            String body = object.getString("body");
            String deviceId = object.getString("deviceId");
            try {
                log.info("接收到固件文件大小响应:{}", body);
                deviceCmdInfoService.firmWareSupportSize(object);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("接收到固件文件大小响应" + body + "到设备deviceId: " + deviceId + "失败", e);
            } finally {
                ack.acknowledge();
            }
        }
    }
}
