package com.zods.flink.factory;
import com.zods.flink.properties.FlinkProperties;
import com.zods.flink.utils.PropertiesConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @author jianglong
 * @version 1.0
 * @Description Flink环境配置
 * @createDate 2022-06-09
 */
public class ExecutionEnvFactory {

    /**加载配置*/
    public static ParameterTool createParameterTool(String prefix) {
        try {
            return ParameterTool
                    .fromPropertiesFile(ExecutionEnvFactory.class.getResourceAsStream(prefix + PropertiesConstant.PROPERTIES_FILE_NAME))
                    .mergeWith(ParameterTool.fromSystemProperties())
                    .mergeWith(ParameterTool.fromMap(getenv()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置运行环境参数
     */
    public static StreamExecutionEnvironment createStreamExecEnvByProperties(ParameterTool parameterTool) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置并行度为，默认并行度是当前机器CPU数量
        env.setParallelism(parameterTool.getInt(PropertiesConstant.STREAM_PARALLELISM, 5));
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(1, 10000));
        if (parameterTool.getBoolean(PropertiesConstant.STREAM_CHECKPOINT_ENABLE, true)) {
            env.enableCheckpointing(parameterTool.getInt(PropertiesConstant.STREAM_CHECKPOINT_INTERVAL, 1000), CheckpointingMode.EXACTLY_ONCE);
            // 设置重启策略 5次尝试，每次尝试间隔50s
            env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 5000));
            // 确保检查点之间有至少500 ms的间隔【checkpoint最小间隔】
            env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
            //同一时间只允许进行一次检查点
            env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
            // 表示一旦Flink处理程序被cancel后，会保留Checkpoint数据，以便根据实际需要恢复到指定的Checkpoint
            env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
            //设置statebackend,将检查点保存在对象存储minio，默认保存在内存中。
            String path = parameterTool.get(PropertiesConstant.MINIO_OSS);
            if (StringUtils.isNotBlank(path)) {
                env.setStateBackend(new FsStateBackend(path));
            }
        }
        //供后续连接source或者sink使用配置
        env.getConfig().setGlobalJobParameters(parameterTool);
        return env;
    }

    /**设置运行环境参数*/
    public static StreamExecutionEnvironment createStreamExecEnvByYml(FlinkProperties properties) {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置并行度为，默认并行度是当前机器CPU数量
        env.setParallelism(properties.getIntValue(properties.streamParallelism(), 5));
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(1, 10000));
        if (properties.getBoolValue(properties.streamCheckPointEnable(), true)) {
            env.enableCheckpointing(properties.getLongValue(properties.streamCheckPointInterval(),1000), CheckpointingMode.EXACTLY_ONCE);
            // 设置重启策略 5次尝试，每次尝试间隔50s
            env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 5000));
            // 确保检查点之间有至少500 ms的间隔【checkpoint最小间隔】
            env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
            //同一时间只允许进行一次检查点
            env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
            // 表示一旦Flink处理程序被cancel后，会保留Checkpoint数据，以便根据实际需要恢复到指定的Checkpoint
            env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
            //设置statebackend,将检查点保存在对象存储minio，默认保存在内存中。
            String path = properties.minioOss();
            if (StringUtils.isNotBlank(path)) {
                env.setStateBackend(new FsStateBackend(path));
            }
        }
        return env;
    }

    private static Map<String, String> getenv() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
