package com.zods.flink.sink.hbase.pool;
import java.io.Serializable;
/**
 * @author jianglong
 * @description 连接池接口定义
 * @create 2020-03-07
 **/
public interface ConnectionPool<T> extends Serializable {

    /**
     * @Description: 获取连接
     * @return: 连接
     */
    public abstract T getConnection();

    /**
     * @Description:返回连接
     * @param: 连接
     */
    public void returnConnection(T conn);

    /**
     * @Description:废弃连接
     * @param: 连接
     */
    public void invalidateConnection(T conn);
}
