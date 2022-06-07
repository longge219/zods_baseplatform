package com.zods.drool.config;
import com.zods.drool.core.KieSchedule;
import com.zods.drool.core.KieTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Objects;
import static com.zods.drool.common.DroolsConstant.LISTENER_CLOSE;
/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2021/5/17 15:03
 */
@Configuration
@EnableConfigurationProperties(DroolsProperties.class)
public class DroolsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "kieTemplate")
    public KieTemplate kieTemplate(DroolsProperties droolsProperties) {
        KieTemplate kieTemplate = new KieTemplate();
        kieTemplate.setPath(droolsProperties.getPath());
        kieTemplate.setMode(droolsProperties.getMode());
        String autoUpdate = droolsProperties.getAutoUpdate();
        if (Objects.equals(LISTENER_CLOSE, autoUpdate)) {
            // 关闭自动更新
            kieTemplate.setUpdate(999999L);
        } else {
            // 启用自动更新
            kieTemplate.setUpdate(droolsProperties.getUpdate());
        }
        kieTemplate.setListener(droolsProperties.getListener());
        kieTemplate.setVerify(droolsProperties.getVerify());
        return kieTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(name = "kieSchedule")
    public KieSchedule kieSchedule(KieTemplate kieTemplate) {
        KieSchedule kieSchedule = new KieSchedule(kieTemplate);
        kieSchedule.execute();
        return kieSchedule;
    }

}
