package com.zods.flink.source.mysql.properties;
import lombok.experimental.UtilityClass;
/**
 * @author jianglong
 * @version 1.0
 * @Description JDBC连接常量表
 * @createDate 2021/3/31 16:23
 */
@UtilityClass
public class JdbcConst {
    public final String JDBC_PREFIX = "/jdbc-";
    public final String JDBC_DRIVER_CLASS_NAME = "driverClassName";
    public final String JDBC_URL = "url";
    public final String JDBC_USERNAME = "username";
    public final String JDBC_PASSWORD = "password";
    public final String JDBC_INITIAL_SIZE = "initialSize";
    public final String JDBC_MAX_TOTAL = "maxTotal";
    public final String JDBC_MIN_IDLE = "minIdle";
}
