package com.zods.smart.iot.gunrfid.command.service;
/**
 * @desc GUN-RFID读卡器-服务接口
 * @author jianglong
 * @date 2022-07-20
 **/
public interface GunRfidCommandService {

    /**
     * @description 设置天线
     * @param deviceIp:读卡器IP
     * @param ants:天线号集，位表示天线是否启用(0:不启用，1启用)，共16位，最多16个天线。例:0000000000000011代表12号天线启用
     * */
    boolean setAnts(String deviceIp,String ants);

    /**
     * @description 查询天线
     * @param deviceIp:读卡器IP
     * */
    boolean selectAnts(String deviceIp);

    /**
     * @description 多次轮询接口
     * @param deviceIp:读卡器IP
     * @param cnt:轮询次数 CNT(范围0-65535，当值为65535则为无限循环)
     * */
    boolean multiplePolling(String deviceIp, int cnt);

    /**
     * @description 停止轮询接口
     * @param deviceIp:读卡器IP
     * */
    boolean stopMultiplePolling(String deviceIp);

}
