package com.zods.largescreen.modules.datasource.service;
import com.zods.largescreen.modules.datasource.controller.dto.DataSourceDto;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * @desc JDBC连接服务接口
 * @author jianglong
 * @date 2022-06-16
 **/
public interface JdbcService {

    /**删除数据库连接池*/
    void removeJdbcConnectionPool(Long id);


    /**获取连接*/
    Connection getPooledConnection(DataSourceDto dataSource) throws SQLException;


    /**测试数据库连接  获取一个连接*/
    Connection getUnPooledConnection(DataSourceDto dataSource) throws SQLException;
}
