package com.zods.flink.properties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
/**
 * @author jianglong
 * @version 1.0
 * @description: FLink参数
 * @date 2022年03月10日 10:10:00
 */
@Data
@Accessors(fluent = true)
public class FlinkProperties {

    private String deserializerKey;

    private String deserializerValue;

    private String kafkaBrokers;

    private String kafkaZookeeperConnect;

    private String kafkaGroupId;

    private String autoOffsetReset;

    private Long consumerFromTime;

    private Integer streamParallelism;

    private Long streamCheckPointInterval;

    private Boolean streamCheckPointEnable;

    private String ruleUrl;

    private Long lateInterval;

    private String minioOss;

    private Long ruleUpdateInterval;

    private String ruleExecuteMode;

    private String ruleMode;

    private String ruleNames;

    private String rulePath;

    public boolean getBoolValue(Boolean value, boolean defaultValue) {
        if (null == value) {
            value = defaultValue;
        }
        return value;
    }

    public String getStrValue(String value, String defaultValue) {
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value;
    }

    public int getIntValue(Integer value, int defaultValue) {
        if (null == value) {
            value = defaultValue;
        }
        return value;
    }

    public Long getLongValue(Long value, long defaultValue) {
        if (null == value) {
            value = defaultValue;
        }
        return value;
    }

}
