package com.zods.smart.iot.config;
import com.zods.smart.iot.electronic.server.properties.ElectronicProperties;
import com.zods.smart.iot.gunrfid.server.properties.GunRifdServerProperties;
import com.zods.smart.iot.gunrfid.server.start.InitGunRfidServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
/**
 * @author jianglong
 * @description 应用启动加载服务
 * @create 2022-06-11
 **/
@Configuration
@ConditionalOnClass
@EnableConfigurationProperties({ElectronicProperties.class, GunRifdServerProperties.class})
public class AppConfigure implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**枪支RFID服务启动加载配置*/
    @Bean(initMethod = "open", destroyMethod = "close")
    @Order(1)
    public InitGunRfidServer initGunRfidServer(GunRifdServerProperties gunRifdServerProperties) throws Exception {
        return new InitGunRfidServer(gunRifdServerProperties);
    }

}
