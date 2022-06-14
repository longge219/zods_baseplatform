package com.zods.plugins.zods.event;


import org.springframework.context.ApplicationEvent;

public class NoticeEvent<T> extends ApplicationEvent {
    private T t;

    public NoticeEvent(T t) {
        super(t);
        this.t = t;
    }
}
