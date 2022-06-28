package com.zods.plugins.db.event.listener;
import com.zods.plugins.db.event.ExceptionEvent;
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
