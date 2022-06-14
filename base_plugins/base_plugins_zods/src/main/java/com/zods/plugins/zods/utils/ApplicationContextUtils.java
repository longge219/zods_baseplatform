package com.zods.plugins.zods.utils;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

public class ApplicationContextUtils implements ApplicationContextAware {
    public static ApplicationContext applicationContext;
    public static final String GAEA_ASYN_APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "gaeaAsynApplicationEventMulticaster";

    public ApplicationContextUtils() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
        if (applicationContext instanceof AbstractApplicationContext) {
            AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext)applicationContext;
            SimpleApplicationEventMulticaster asynApplicationEventMulticaster = new SimpleApplicationEventMulticaster(abstractApplicationContext.getBeanFactory());
            abstractApplicationContext.getBeanFactory().registerSingleton("gaeaAsynApplicationEventMulticaster", asynApplicationEventMulticaster);
            int processors = Runtime.getRuntime().availableProcessors();
            asynApplicationEventMulticaster.setTaskExecutor(new ThreadPoolExecutor(processors, processors, 10L, TimeUnit.MINUTES, new ArrayBlockingQueue(16 * processors, true)));
        }

    }

    public static <T> T getBean(String name, Class<T> requireType) {
        return applicationContext.getBean(name, requireType);
    }

    public static <T> T getBean(Class<T> requireType) {
        return applicationContext.getBean(requireType);
    }

    public static void publishEvent(ApplicationEvent applicationEvent) {
        SimpleApplicationEventMulticaster applicationEventMulticaster = (SimpleApplicationEventMulticaster)applicationContext.getBean("gaeaAsynApplicationEventMulticaster", SimpleApplicationEventMulticaster.class);
        applicationEventMulticaster.multicastEvent(applicationEvent);
    }

    public static void publishSynEvent(ApplicationEvent applicationEvent) {
        applicationContext.publishEvent(applicationEvent);
    }
}
