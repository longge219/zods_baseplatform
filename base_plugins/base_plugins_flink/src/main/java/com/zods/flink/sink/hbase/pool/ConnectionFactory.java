package com.zods.flink.sink.hbase.pool;
import org.apache.commons.pool2.PooledObjectFactory;
import java.io.Serializable;
/**
 * @author jianglong
 * @description 连接工厂
 * @create 2020-03-07
 **/
public interface ConnectionFactory<T> extends PooledObjectFactory<T>, Serializable {

    /**
     * <p>Title: createConnection</p>
     * <p>Description: 创建连接</p>
     *
     * @return 连接
     * @throws Exception
     */
    public abstract T createConnection() throws Exception;
}
