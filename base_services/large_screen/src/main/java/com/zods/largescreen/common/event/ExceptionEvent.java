package com.zods.largescreen.common.event;
import org.springframework.context.ApplicationEvent;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class ExceptionEvent extends ApplicationEvent {
    private Throwable throwable;

    public ExceptionEvent(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
