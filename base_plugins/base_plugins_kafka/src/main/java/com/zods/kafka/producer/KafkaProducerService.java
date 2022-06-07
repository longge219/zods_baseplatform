package com.zods.kafka.producer;

/**
 * @author: jianglong
 * @description: kafka发送消息接口
 * @date: 2019-09-24
 */
public interface KafkaProducerService {

    /**
     * 发送消息
     *
     * @param topic
     * @param msgData
     */
    void sendMessage(String topic, Object msgData);


    /**
     * 发送
     *
     * @param topic
     * @param msgData
     * @param partition
     */
    void sendMessage(String topic, Object msgData, Integer partition);
}
