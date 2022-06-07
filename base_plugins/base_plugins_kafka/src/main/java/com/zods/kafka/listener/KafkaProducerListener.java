package com.zods.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

import java.util.concurrent.BlockingQueue;

/**
 * @author: jianglong
 * @description: kafka生产者监听器
 * @date: 2019-09-24
 */
@Slf4j
public class KafkaProducerListener implements ProducerListener<String, String> {

    /**
     * 生产消息队列
     */
    private final BlockingQueue<String> blockingDeque;

    /**
     * 是否可见日志
     */
    private final Boolean visibleLog;

    public KafkaProducerListener(BlockingQueue<String> blockingDeque, Boolean visibleLog) {
        this.blockingDeque = blockingDeque;
        this.visibleLog = visibleLog;
    }

    /**
     * 获取消费成功队列,统一进行处理
     */
    public BlockingQueue<String> getBlockingDeque() {
        return blockingDeque;
    }

    /**
     * 生产消息成功
     */
    @Override
    public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
        //blockingDeque.add(value);
        if (visibleLog) {
            log.info("kafka生产消息成功--value:" + value);
        }
    }

    /**
     * 生产消息失败
     */
    @Override
    public void onError(String topic, Integer partition, String key, String value, Exception exception) {
        log.error("kafka生产消息失败--value:" + value + "失败");
        exception.printStackTrace();
    }

}
