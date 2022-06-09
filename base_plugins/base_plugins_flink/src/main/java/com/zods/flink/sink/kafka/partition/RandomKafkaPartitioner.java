package com.zods.flink.sink.kafka.partition;
import org.apache.flink.shaded.com.google.common.base.Preconditions;
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkKafkaPartitioner;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @description 自定义分区
 * @author jianglong
 * @create 2020-03-27
 */
public class RandomKafkaPartitioner<T> extends FlinkKafkaPartitioner<T> {

    private static final long serialVersionUID = -3785320239953858777L;

    private static final ThreadLocalRandom ran = ThreadLocalRandom.current();

    public RandomKafkaPartitioner() {
    }

    public int partition(T record, byte[] key, byte[] value, String targetTopic, int[] partitions) {
        Preconditions.checkArgument(partitions != null && partitions.length > 0, "Partitions of the target topic is empty.");
        return partitions[this.ran.nextInt(1000000) % partitions.length];
    }

    public boolean equals(Object o) {
        return this == o || o instanceof RandomKafkaPartitioner;
    }

    public int hashCode() {
        return RandomKafkaPartitioner.class.hashCode();
    }
}
