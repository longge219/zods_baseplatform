package com.zods.plugins.zods.event.listener;
import com.zods.plugins.zods.event.ExceptionEvent;
import org.springframework.context.ApplicationListener;

public class ExceptionApplicationListener implements ApplicationListener<ExceptionEvent> {
    public ExceptionApplicationListener() {
    }

    public void onApplicationEvent(ExceptionEvent event) {
        Throwable throwable = event.getThrowable();
    }
}
