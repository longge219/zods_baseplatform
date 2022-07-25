package com.zods.smart.iot.gunrfid.server.job;

import com.zods.kafka.fastjson.FastJsonUtils;
import com.zods.kafka.producer.KafkaProducerService;
import com.zods.smart.iot.common.topic.GunRfidData;
import com.zods.smart.iot.gunrfid.command.service.GunRfidCommandService;
import com.zods.smart.iot.gunrfid.server.scan.EpcManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jianglong
 * @version 1.0
 * @Description 动环设备设备状态检查
 * @createDate 2021/09/10
 */
@Slf4j
public class GunRfidDeviceJob extends QuartzJobBean {

    @Autowired
    private GunRfidCommandService gunRfidCommandService;

    @Resource
    private KafkaProducerService kafkaProducerService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info(" 开始GUN-RFID的EPC扫描");
        try {
            List<GunRfidData> gunRfidDataLList = EpcManager.getInstance().getGunRfidData();
            log.info(FastJsonUtils.objectTojson(gunRfidDataLList));
            //1分钟从新设置天线循环次数。
            gunRfidDataLList.forEach(gunRfidData->{
                gunRfidCommandService.multiplePolling(gunRfidData.getDeviceIP(),65535);
            });


        }
        catch (Exception ex){
              log.error("GUN-RFID的EPC扫描业务出错.............");
              ex.printStackTrace();
        }
        log.info("完成GUN-RFID的EPC扫描");
    }
}
