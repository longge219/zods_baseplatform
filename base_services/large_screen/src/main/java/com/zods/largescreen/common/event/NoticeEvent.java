package com.zods.largescreen.common.event;
import org.springframework.context.ApplicationEvent;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class NoticeEvent<T> extends ApplicationEvent {
    private T t;

    public NoticeEvent(T t) {
        super(t);
        this.t = t;
    }
}
