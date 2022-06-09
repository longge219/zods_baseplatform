package com.zods.flink.sink.mysql.core;
import com.zods.flink.sink.mysql.config.MysqlConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
/**
 * @author jianglong
 * @description 数据批量写入mysql
 * @create 2019-10-29
 **/
public abstract class AbstratBatchSinkToMySQL<T> extends RichSinkFunction<List<T>> {

    public PreparedStatement ps;

    public BasicDataSource dataSource;

    public Connection connection;

    public static MysqlConfig absMysqlConfig;

    public String exeSql;

    public ParameterTool parameter;

    public void initConfig(MysqlConfig mysqlConfig, String exeSql, ParameterTool parameter) {
        absMysqlConfig = mysqlConfig;
        this.exeSql = exeSql;
        this.parameter = parameter;
    }

    /**
     * open() 方法中建立连接，这样不用每次 invoke 的时候都要建立连接和释放连接
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        dataSource = new BasicDataSource();
        connection = getConnection(dataSource);
        ps = this.connection.prepareStatement(exeSql);
    }

    @Override
    public void close() throws Exception {
        super.close();
        //关闭连接和释放资源
        if (connection != null) {
            connection.close();
        }
        if (ps != null) {
            ps.close();
        }
    }

    public static Connection getConnection(BasicDataSource dataSource) {
        dataSource.setDriverClassName(absMysqlConfig.getDriverClassName());
        //注意，替换成自己本地的 mysql 数据库地址和用户名、密码
        dataSource.setUrl(absMysqlConfig.getUrl());
        dataSource.setUsername(absMysqlConfig.getUserName());
        dataSource.setPassword(absMysqlConfig.getPassWord());
        //设置连接池的一些参数
        dataSource.setInitialSize(absMysqlConfig.getInitialSize());
        dataSource.setMaxTotal(absMysqlConfig.getMaxTotal());
        dataSource.setMinIdle(absMysqlConfig.getMinIdle());
        Connection con = null;
        try {
            con = dataSource.getConnection();
            System.out.println("创建连接池：" + con);
        } catch (Exception e) {
            System.out.println("-----------mysql get connection has exception , msg = " + e.getMessage());
        }
        return con;
    }
}
