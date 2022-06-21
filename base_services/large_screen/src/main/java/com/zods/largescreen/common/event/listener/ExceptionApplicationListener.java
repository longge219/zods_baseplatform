package com.zods.largescreen.common.event.listener;
import com.zods.largescreen.common.event.ExceptionEvent;
import org.springframework.context.ApplicationListener;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class ExceptionApplicationListener implements ApplicationListener<ExceptionEvent> {
    public ExceptionApplicationListener() {
    }
    public void onApplicationEvent(ExceptionEvent event) {
        Throwable throwable = event.getThrowable();
    }
}
