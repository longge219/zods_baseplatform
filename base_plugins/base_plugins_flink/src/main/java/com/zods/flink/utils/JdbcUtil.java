package com.zods.flink.utils;
import com.zods.flink.factory.ExecutionEnvFactory;
import com.zods.flink.source.mysql.config.JdbcConfig;
import com.zods.flink.source.mysql.properties.JdbcConst;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.utils.ParameterTool;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 * @author jianglong
 * @version 1.0
 * @Description JDBC工具类
 * @createDate 2021/4/9 16:09
 */
@Slf4j
@UtilityClass
public class JdbcUtil {

    public final JdbcConfig getDbConfig() {
        ParameterTool parameterTool = ExecutionEnvFactory.createParameterTool(JdbcConst.JDBC_PREFIX);
        JdbcConfig jdbcConfig = JdbcConfig.builder().driverClassName(parameterTool.getRequired(JdbcConst.JDBC_DRIVER_CLASS_NAME))
                .url(parameterTool.getRequired(JdbcConst.JDBC_URL)).userName(parameterTool.getRequired(JdbcConst.JDBC_USERNAME))
                .passWord(parameterTool.getRequired(JdbcConst.JDBC_PASSWORD))
                .initialSize(parameterTool.getInt(JdbcConst.JDBC_INITIAL_SIZE)).maxTotal(parameterTool.getInt(JdbcConst.JDBC_MAX_TOTAL))
                .minIdle(parameterTool.getInt(JdbcConst.JDBC_MIN_IDLE)).build();
        return jdbcConfig;
    }

    public final JdbcConfig getDbConfig(ParameterTool parameterTool) {
        JdbcConfig jdbcConfig = JdbcConfig.builder().driverClassName(parameterTool.getRequired(JdbcConst.JDBC_DRIVER_CLASS_NAME))
                .url(parameterTool.getRequired(JdbcConst.JDBC_URL)).userName(parameterTool.getRequired(JdbcConst.JDBC_USERNAME))
                .passWord(parameterTool.getRequired(JdbcConst.JDBC_PASSWORD))
                .initialSize(parameterTool.getInt(JdbcConst.JDBC_INITIAL_SIZE)).maxTotal(parameterTool.getInt(JdbcConst.JDBC_MAX_TOTAL))
                .minIdle(parameterTool.getInt(JdbcConst.JDBC_MIN_IDLE)).build();
        return jdbcConfig;
    }

    /**
     * 创建数据库链接
     * @param driver   驱动
     * @param url      数据库路径
     * @param user     用户名
     * @param password 密码
     * @return
     */
    public final Connection getConnection(String driver, String url, String user, String password) {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            log.error("-----------jdbc get connection has exception , msg = " + e.getMessage(), e);
        }
        return con;
    }

}
