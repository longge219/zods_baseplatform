package com.zods.flink.utils;


public class PropertiesConstant {

    public static final String PROPERTIES_FILE_NAME = "application.properties";

    public static final String STREAM_PARALLELISM = "stream.parallelism";

    public static final String STREAM_SINK_PARALLELISM = "stream.sink.parallelism";

    public static final String STREAM_CHECKPOINT_ENABLE = "stream.checkpoint.enable";

    public static final String STREAM_CHECKPOINT_INTERVAL = "stream.checkpoint.interval";

    /**
     * 对象存储minio地址
     */
    public static final String MINIO_OSS = "minio-oss";


    /**
     * Drools
     */
    public static final String STREAM_RULE_URL = "stream.rule.url";
    public static final String STREAM_RULE_PATH = "spring.drools.path";
    public static final String STREAM_RULE_MODE = "spring.drools.mode";
    public static final String STREAM_RULE_LISTENER = "spring.drools.listener";
    public static final String STREAM_RULE_NAMES = "spring.drools.names";
    public static final String STREAM_RULE_EXECUT_MODE = "spring.drools.execute";

}
