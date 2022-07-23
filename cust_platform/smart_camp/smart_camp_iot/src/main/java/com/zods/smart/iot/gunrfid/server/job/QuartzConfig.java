package com.zods.smart.iot.gunrfid.server.job;
import lombok.Data;
import org.quartz.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties({QuartzBean.class})
public class QuartzConfig {




    /**RFID设备定时检查状态任务*/
    @Bean
    public JobDetail gunRfidDeviceJobDetail(){
        JobDetail jobDetail = JobBuilder.newJob(GunRfidDeviceJob.class)
                .withIdentity("gunRfidDeviceJob","gunRfidDeviceJobGroup1")
                //JobDataMap可以给任务execute传递参数
                .usingJobData("gunrfidjob_param","gunrfidjob_param1")
                .storeDurably()
                .build();
        return jobDetail;
    }
    @Bean
    public Trigger rfidDeviceStatusRefreshJobTrigger(QuartzBean quartzBean){
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(gunRfidDeviceJobDetail())
                .withIdentity("gunRfidDeviceJobTrigger","gunRfidDeviceJobTriggerGroup1")
                .usingJobData("gunrfidjob_trigger_param","gunrfidjob_trigger_param1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(quartzBean.getGunRfidEpcScan()).repeatForever())
                .build();
        return trigger;
    }
}
