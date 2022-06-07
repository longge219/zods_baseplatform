package com.zods.mqtt.sever.protocol.config;
import com.zods.mqtt.sever.protocol.bean.InitBean;
import com.zods.mqtt.sever.protocol.client.AppMqttClient;
import com.zods.mqtt.sever.protocol.client.MqttClientProperties;
import com.zods.mqtt.sever.protocol.scan.SacnScheduled;
import com.zods.mqtt.sever.protocol.scan.ScanRunnable;
import com.zods.mqtt.sever.protocol.server.InitServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
/**
 * @author jianglong
 * @description 自动化配置初始化服务
 * @create 2019-03-01
 **/
@Configuration
@ConditionalOnClass
@EnableConfigurationProperties({InitBean.class})
public class ServerConfigure implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 启动扫描消息确认线程(qos消息重发)
     */
    @Bean
    @ConditionalOnMissingBean(name = "sacnScheduled")
    public ScanRunnable initRunable(@Autowired InitBean serverBean) {
        //消息重发周期
        long time = (serverBean == null || serverBean.getPeriod() < 5) ? 10 : serverBean.getPeriod();
        ScanRunnable sacnScheduled = new SacnScheduled(time);
        Thread scanRunnable = new Thread(sacnScheduled);
        scanRunnable.setDaemon(true);
        scanRunnable.start();
        return sacnScheduled;
    }

    /**
     * 初始化服务器通信参数对象
     */
    @Bean(initMethod = "open", destroyMethod = "close")
    @Order(1)
    public InitServer initServer(InitBean serverBean) throws Exception {
        return new InitServer(serverBean);
    }


    /**
     * mqtt服务器启动后，将控制台console以客户端身份注册到mqtt服务器
     *
     * @return
     */
    @Bean(destroyMethod = "closeConnect")
    @Order(1000)
    @DependsOn("initServer")
    @ConditionalOnMissingBean(name = "appMqttClient")
    public AppMqttClient mqttClient() {
        AppMqttClient appMqttClient = new AppMqttClient();
        MqttClientProperties bean = applicationContext.getBean(MqttClientProperties.class);
        appMqttClient.setConsoleProperties(bean);
        new Thread(() -> {
            appMqttClient.connect();
        }, "mqttClient").start();
        return appMqttClient;
    }


}
