package com.zods.plugins.zods.event;


import org.springframework.context.ApplicationEvent;

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
