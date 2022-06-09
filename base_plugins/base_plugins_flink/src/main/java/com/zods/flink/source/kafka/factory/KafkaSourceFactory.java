package com.zods.flink.source.kafka.factory;
import com.zods.flink.source.kafka.config.KakfaConfig;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * @author jianglong
 * @version 1.0
 * @Description kafka-source创建工厂
 * @createDate 2022-06-09
 */
public class KafkaSourceFactory<T> {

    private Class<T> clazz;

    public KafkaSourceFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static DataStreamSource<String> createKafkaSource(StreamExecutionEnvironment env, KakfaConfig KakfaConfig, String topic,
                                                             String groupId, OffsetsInitializer offsets) {
        // 1.15 之后需要新方法创建kafka source
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setProperty("partition.discovery.interval.ms", "60000")
                .setBootstrapServers(KakfaConfig.getBootstrapServers())
                .setTopics(topic)
                .setGroupId(groupId)
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setStartingOffsets(offsets)
                .build();
            // 重置offset到time时刻 消费开始时间下标
//                Long time = KakfaConfig.getConsumerFromTime();
//                if (time != 0L) {
//                    Map<KafkaTopicPartition, Long> partitionOffset = buildOffsetByTime(props, topic, time);
//                    consumer.setStartFromSpecificOffsets(partitionOffset);
//             }
        return env.fromSource(source, WatermarkStrategy.noWatermarks(), topic);
    }


    /**根据消费时间设置消费下标*/
    private static Map<KafkaTopicPartition, Long> buildOffsetByTime(Properties props, String topic, Long time) {
        props.setProperty("group.id", "query_time_" + time);
        KafkaConsumer consumer = new KafkaConsumer(props);
        List<PartitionInfo> partitionsFor = consumer.partitionsFor(topic);
        Map<TopicPartition, Long> partitionInfoLongMap = new HashMap<>();
        for (PartitionInfo partitionInfo : partitionsFor) {
            partitionInfoLongMap.put(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), time);
        }
        Map<TopicPartition, OffsetAndTimestamp> offsetResult = consumer.offsetsForTimes(partitionInfoLongMap);
        Map<KafkaTopicPartition, Long> partitionOffset = new HashMap<>();
        offsetResult.forEach((key, value) -> partitionOffset.put(new KafkaTopicPartition(key.topic(), key.partition()), value.offset()));
        consumer.close();
        return partitionOffset;
    }

}