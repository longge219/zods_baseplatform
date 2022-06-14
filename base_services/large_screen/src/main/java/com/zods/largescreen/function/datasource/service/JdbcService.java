package com.zods.largescreen.function.datasource.service;
import com.zods.largescreen.function.datasource.dto.DataSourceDto;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * @description JDBC服务接口
 * @author jianglong
 * @date 2022-06-14
 **/
public interface JdbcService {

    /**
     * 删除数据库连接池
     *
     * @param id
     */
    void removeJdbcConnectionPool(Long id);


    /**
     * 获取连接
     *
     * @param dataSource
     * @return
     * @throws SQLException
     */
    Connection getPooledConnection(DataSourceDto dataSource) throws SQLException;

    /**
     * 测试数据库连接  获取一个连接
     *
     * @param dataSource
     * @return
     * @throws ClassNotFoundException driverName不正确
     * @throws SQLException
     */
    Connection getUnPooledConnection(DataSourceDto dataSource) throws SQLException;
}
