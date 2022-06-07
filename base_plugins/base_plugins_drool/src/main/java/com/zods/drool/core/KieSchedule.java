package com.zods.drool.core;
import com.zods.drool.util.ScheduledThreadPoolExecutorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import java.util.concurrent.TimeUnit;
/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/27
 * @since 1.0.0
 */
@Slf4j
public class KieSchedule implements InitializingBean {

    private KieTemplate kieTemplate;

    public KieSchedule(KieTemplate kieTemplate) {
        this.kieTemplate = kieTemplate;
    }

    public void execute() {
        Long update = kieTemplate.getUpdate();
        if (update == null || update == 0L) {
            update = 30L;
        }
        ScheduledThreadPoolExecutorUtil.RULE_SCHEDULE.scheduleAtFixedRate(new RuleCache(kieTemplate), 1, update, TimeUnit.SECONDS);
    }

    public void stop() {
        try {
            ScheduledThreadPoolExecutorUtil.RULE_SCHEDULE.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("stop rule schedule exception:" + e.getMessage(), e);
        } finally {
            ScheduledThreadPoolExecutorUtil.RULE_SCHEDULE.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() {
    }


}
