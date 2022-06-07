package com.zods.mqtt.sever.business.service;


import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * 下发命令记录信息表 服务类
 * </p>
 *
 * @author liu sir
 */
public interface DeviceCmdInfoService {

    /**下发命令*/
    void sendCmd(JSONObject jsonObject) throws Exception;

    /**平台下发升级固件主题*/
    void firmWareUpgrade(JSONObject jsonObject) throws Exception;

    /**设备发送待升级固件大小主题*/
    void firmWareSupportSize(JSONObject jsonObject) throws Exception;

    /**分片升级固件*/
    void publishFirmware(String deviceId) throws Exception;

    /**统计下次升级参数*/
    void countNextUpgrade(String deviceId);

    /**删除指定设备历史升级参数*/
    void removePastUpgradeData(String deviceId);

    /**获取指定设备当前正在升级的数据参数*/
    JSONObject getUpgradeData(String deviceId);

}
