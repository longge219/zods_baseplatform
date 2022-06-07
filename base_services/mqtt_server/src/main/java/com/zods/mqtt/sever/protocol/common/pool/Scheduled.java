package com.zods.mqtt.sever.protocol.common.pool;
import java.util.concurrent.ScheduledFuture;

/**
 * @description 接口
 * @author jianglong
 * @create 2019-03-01
 **/
@FunctionalInterface
public interface Scheduled {

    ScheduledFuture<?> submit(Runnable runnable);
}
