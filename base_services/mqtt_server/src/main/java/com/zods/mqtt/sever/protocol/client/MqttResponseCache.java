package com.zods.mqtt.sever.protocol.client;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.business.constant.EquipCmdConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.Arrays;
/**
 * @author jianglong
 * @description 下行相应消息缓存类
 * @create 2019-03-01
 **/
@Component
@Slf4j
public class MqttResponseCache {

    // 筛选有用信息
    private void filterMessage(String message, String deviceId){
        log.info(".....................................下行响应:{}.........................................", message);
        if(!StringUtils.isEmpty(message)){
        }
        String response = message.substring(0, message.lastIndexOf("&msgid")).substring(message.substring(0, message.lastIndexOf("&msgid")).lastIndexOf("=") + 1);
        String value = message.substring(0, message.lastIndexOf("&msgid")).substring(message.substring(0, message.lastIndexOf("&msgid")).indexOf("&") + 1);

    }



    private void parseCmdResponseA(JSONObject jsonObject, String message, String response,String deviceId) {
        if (message.startsWith(EquipCmdConstant.REQ_TIME)) {
            // 获取终端时间 $cmd=reqtime&time=2019-05-01 13:00:00
            // 真实获取终端时间:$cmd=reqtime&time=2021-07-14 10:18:03&msgid=a5afbb14827a4d63afbb14827a5d630b
            jsonObject.put(EquipCmdConstant.REQ_TIME, response);
        }
        if (message.startsWith(EquipCmdConstant.SET_TIME)) {
            // 设置终端时间 $cmd=settime&result=succ
            jsonObject.put(EquipCmdConstant.SET_TIME, response);
        }
    }

    private void parseCmdResponseB(JSONObject jsonObject, String message, String response,String deviceId) {
        if (message.startsWith(EquipCmdConstant.SET_WORK_MODE)) {
            // 设置工作模式 $cmd=setworkmode&result=succ
            jsonObject.put(EquipCmdConstant.SET_WORK_MODE, response);
        }
        if (message.startsWith(EquipCmdConstant.GET_WORK_MODE)) {
            // 获取工作模式 $cmd=getworkmode&mode=value
            jsonObject.put(EquipCmdConstant.GET_WORK_MODE, response);
        }
    }

    private void parseCmdResponseC(JSONObject jsonObject, String message, String response,String deviceId) {
        if (message.startsWith(EquipCmdConstant.GET_STATUS)) {
            // 获取工作状态 $cmd=getstatus&state={"ext_power_volt":24.04,"temp":42.00,"signal_4g":27.0,"sw_version":"1.0.1","4g_on":true}
            jsonObject.put(EquipCmdConstant.GET_STATUS, response);
        }
        if (message.startsWith(EquipCmdConstant.SAMPLE)) {
            // 传感器遥测 $cmd=sample&datastreams={"L2_LF_1":"34.56","L2_LF_2":"67.45","L2_LF_3":"12.2"}
            jsonObject.put(EquipCmdConstant.GET_STATUS, response);
        }
    }

    private void parseCmdResponseD(JSONObject jsonObject, String message, String response,String deviceId) {
        // 获取设备基础属性
        if (message.startsWith(EquipCmdConstant.BASIC)) {
            jsonObject.put(EquipCmdConstant.BASIC, response);
        }
        // 获取设备指令集版本
        if (message.startsWith(EquipCmdConstant.GET_CMD_VERSION)) {
            jsonObject.put(EquipCmdConstant.GET_CMD_VERSION, response);
        }

    }

    private void parseCmdResponseE(JSONObject jsonObject, String message, String response,String deviceId) {
        // 地质灾害气象预警
        if (message.startsWith(EquipCmdConstant.METEOROLOGICALEARLYWARNING)) {
            // $cmd=meteorologicalearlywarning&result=succ
            jsonObject.put(EquipCmdConstant.METEOROLOGICALEARLYWARNING, response);
        }
        if (message.startsWith(EquipCmdConstant.SET_SENSOR_TIME)) {
            // 设置传感器时间 $cmd=setsensortime&result=succ
            jsonObject.put(EquipCmdConstant.SET_SENSOR_TIME, response);
        }
    }

    private void parseCmdResponseF(JSONObject jsonObject, String message, String response,String deviceId) {
        if (message.startsWith(EquipCmdConstant.SET_SENSOR_ATTR)) {
            // 设置传感器属性 $cmd=setsensorattr&result=succ
            jsonObject.put(EquipCmdConstant.SET_SENSOR_ATTR, response);
        }
        // 设置平台参数
        if (message.startsWith(EquipCmdConstant.SET_PLATFORM_PARAM)) {
            jsonObject.put(EquipCmdConstant.SET_PLATFORM_PARAM, response);
        }
    }

    private void parseCmdResponseG(JSONObject jsonObject, String message, String response,String deviceId) {
        // 设置链路
        if (message.startsWith(EquipCmdConstant.SET_LINK)) {
            jsonObject.put(EquipCmdConstant.SET_LINK, response);
        }
        // 设置设备传感器配置
        if (message.startsWith(EquipCmdConstant.SET_SENSOR)) {
            jsonObject.put(EquipCmdConstant.SET_SENSOR, response);
        }
    }

    private void parseCmdResponseH(JSONObject jsonObject, String message, String response,String deviceId){
        // 设置设备传感器初始值
        if (message.startsWith(EquipCmdConstant.SET_INITIAL)) {
            jsonObject.put(EquipCmdConstant.SET_INITIAL, response);
        }
        // 固件升级
        if (message.startsWith(EquipCmdConstant.FIRMWARE_MQTT_UPGRADE)) {
            jsonObject.put(EquipCmdConstant.FIRMWARE_MQTT_UPGRADE, response);
        }
        if (message.startsWith(EquipCmdConstant.GET_SENSOR_TYPE)) {
            // 获取传感器类型 $cmd=getsensorID&sensor_id=value
            jsonObject.put(EquipCmdConstant.GET_SENSOR_TYPE, response);
        }
    }

    private void parseCmdResponseI(JSONObject jsonObject, String message, String value,String deviceId) {
        if (message.startsWith(EquipCmdConstant.GET_SENSOR_TIME)) {
            // 获取传感器时间 $cmd=reqsensortime&sensor_id=value&sample_intv=value&upload_intv=value&plus_intv=value
            JSONObject object = new JSONObject();
            Arrays.asList(value.split("&")).stream().forEach(param -> {
                String[] pa = param.split("=");
                object.put(pa[0], pa[1]);
            });
            jsonObject.put(EquipCmdConstant.GET_SENSOR_TIME, object.toJSONString());
        }
    }

    private void parseCmdResponseJ(JSONObject jsonObject, String message, String value,String deviceId) {
        if (message.startsWith(EquipCmdConstant.GET_SENSOR_ATTR)) {
            // 获取传感器参数 $cmd=getsensorattr&sensor_id=value&threshold=value&upper_limit=value&lower_limit=value
            JSONObject object = new JSONObject();
            Arrays.asList(value.split("&")).stream().forEach(param -> {
                String[] pa = param.split("=");
                object.put(pa[0], pa[1]);
            });
            jsonObject.put(EquipCmdConstant.GET_SENSOR_ATTR, object.toJSONString());
        }
    }

    private void parseCmdResponseK(JSONObject jsonObject, String message, String value,String deviceId) {
        if (message.indexOf(EquipCmdConstant.GET_PLATFORM_PARAM) != -1) {
            // 获取平台参数 $cmd=getplatform&device_id=hsl02&api_key=AAC6BA3B091C511A8FFEFAA6C653B1BC&project_id=367059&mqtt_addr=171.221.254.254&mqtt_port=6681&auth_info=XCBD_2020090302&http_addr=api.sheclouds.com&http_port=80&controlNo=2
            JSONObject object = new JSONObject();
            Arrays.asList(value.split("&")).stream().forEach(param -> {
                String[] pa = param.split("=");
                object.put(pa[0], pa[1]);
            });
            jsonObject.put(EquipCmdConstant.GET_PLATFORM_PARAM, object.toJSONString());
        }
    }
}
