package com.zods.kafka.producer.impl;
import com.zods.kafka.fastjson.FastJsonUtils;
import com.zods.kafka.producer.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.Executor;
/**
 * @author: jianglong
 * @description: kafka发送消息接口实现
 * @date: 2019-09-24
 */
@Slf4j
@Service("kafkaProducerService")
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Resource
    private Executor executor;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送消息
     *
     * @param topic
     * @param msgData
     */
    @Override
    public void sendMessage(String topic, Object msgData) {
        executor.execute(() -> {
            try {
                ProducerRecord record = new ProducerRecord<String, String>(topic, null, null, FastJsonUtils.objectTojson(msgData));
                kafkaTemplate.send(record);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void sendMessage(String topic, Object msgData, Integer partition) {
        executor.execute(() -> {
            try {
                ProducerRecord record = new ProducerRecord<String, String>(topic, partition, null, FastJsonUtils.objectTojson(msgData));
                kafkaTemplate.send(record);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
