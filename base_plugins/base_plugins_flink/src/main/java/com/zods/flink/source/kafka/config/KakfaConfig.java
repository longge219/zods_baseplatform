package com.zods.flink.source.kafka.config;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @description Kafka参数配置
 * @create 2022-06-09
 **/
@Data
@Builder
public class KakfaConfig implements Serializable {

    private String bootstrapServers;

    private Long consumerFromTime;



}
