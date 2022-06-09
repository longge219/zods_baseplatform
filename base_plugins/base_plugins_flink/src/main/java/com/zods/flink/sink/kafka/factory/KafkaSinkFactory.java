package com.zods.flink.sink.kafka.factory;
import com.zods.flink.sink.kafka.properties.KafkaSinkPropertiesConstants;
import org.apache.flink.api.java.utils.ParameterTool;
import java.util.Properties;
/**
 * @description KAFKA-SINK创建工厂配置信息
 * @author jianglong
 * @create 2020-03-27
 */
public class KafkaSinkFactory {

    /**设置 kafka 配置*/
    public static Properties buildKafkaProps(ParameterTool parameterTool) {
        Properties props = parameterTool.getProperties();
        props.put("bootstrap.servers", parameterTool.get(KafkaSinkPropertiesConstants.SINK_KAFKA_BOOTSTRAP_SERVERS));
        props.put("zookeeper.connect", parameterTool.get(KafkaSinkPropertiesConstants.SINK_KAFKA_ZOOKEEPER_CONNECT));
        props.put("group.id", parameterTool.get(KafkaSinkPropertiesConstants.SINK_KAFKA_GROUP_ID));
        return props;
    }
}
