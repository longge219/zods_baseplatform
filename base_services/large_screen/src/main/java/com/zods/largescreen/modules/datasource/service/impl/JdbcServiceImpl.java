package com.zods.largescreen.modules.datasource.service.impl;
import com.alibaba.druid.pool.DruidDataSource;
import com.zods.largescreen.modules.datasource.controller.dto.DataSourceDto;
import com.zods.largescreen.modules.datasource.service.JdbcService;
import com.zods.largescreen.properties.DruidProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @desc JDBC连接服务接口实现
 * @author jianglong
 * @date 2022-06-16
 **/
@Service
@Slf4j
public class JdbcServiceImpl implements JdbcService {

    @Autowired
    private DruidProperties druidProperties;

    /**所有数据源的连接池存在map里
     * key 为dataSourceId, value为该数据源的连接池
     * */
    static Map<Long, DruidDataSource> map = new ConcurrentHashMap<>();


    /**获取连接池*/
    public DruidDataSource getJdbcConnectionPool(DataSourceDto dataSource) {
        if (map.containsKey(dataSource.getId())) {
            return map.get(dataSource.getId());
        } else {
            try {
                if (!map.containsKey(dataSource.getId())) {
                    DruidDataSource pool = druidProperties.dataSource(dataSource.getJdbcUrl(),
                            dataSource.getUsername(), dataSource.getPassword(), dataSource.getDriverName());
                    map.put(dataSource.getId(), pool);
                    log.info("创建连接池成功：{}", dataSource.getJdbcUrl());
                }
                return map.get(dataSource.getId());
            } finally {
            }
        }
    }


    /**删除数据库连接池*/
    @Override
    public void removeJdbcConnectionPool(Long id) {
        try {
            DruidDataSource pool = map.get(id);
            if (pool != null) {
                log.info("remove pool success, datasourceId:{}", id);
                map.remove(id);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
        }
    }

    /**获取连接*/
    @Override
    public Connection getPooledConnection(DataSourceDto dataSource) throws SQLException{
        DruidDataSource pool = getJdbcConnectionPool(dataSource);
        return pool.getConnection();
    }

    /**测试数据库连接  获取一个连接 不放入数据源连接池map*/
    @Override
    public Connection getUnPooledConnection(DataSourceDto dataSource) throws SQLException {
        DruidDataSource druidDataSource = druidProperties.dataSource(dataSource.getJdbcUrl(),
                dataSource.getUsername(), dataSource.getPassword(), dataSource.getDriverName());
        return druidDataSource.getConnection();
    }

}
