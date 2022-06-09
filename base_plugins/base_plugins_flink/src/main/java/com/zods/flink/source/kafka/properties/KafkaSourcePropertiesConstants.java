package com.zods.flink.source.kafka.properties;
/**
 * @description KAFKA配置常量名称
 * @author jianglong
 * @create 2019-10-18
 */
public class KafkaSourcePropertiesConstants {

    public static final String SOURCE_KAFKA_BOOTSTRAP_SERVER = "source.kafka.bootstrap.servers";

    public static final String SOURCE_KAFKA_ZOOKEEPER_CONNECT = "source.kafka.zookeeper.connect";

    public static final String SOURCE_KAFKA_GROUP_ID = "source.kafka.group.id";

    public static final String SOURCE_KAFKA_KEY_DESERIALIZER = "source.kafka.key.deserializer";

    public static final String  SOURCE_KAFKA_VALUE_DESERIALIZER = "source.kafka.value.deserializer";

    public static final String SOURCE_KAFKA_AUTO_OFFSET_RESET = "source.kafka.auto.offset.reset";

    public static final String SOURCE_KAFKA_CONSUMER_FROM_TIME = "source.kafka.consumer.from.time";
}
