package com.zods.flink.sink.hbase.pool;
import com.zods.flink.sink.hbase.config.HbaseConfig;
import com.zods.flink.sink.hbase.exception.ConnectionException;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import java.util.Map.Entry;
import java.util.Properties;
/**
 * @author jianglong
 * @description HbaseConfig链接工厂
 * @create 2020-03-07
 **/
class HbaseConnectionFactory implements ConnectionFactory<Connection> {

    private static final long serialVersionUID = 4024923894283696465L;


    private final Configuration hadoopConfiguration;

    /**
     * @param hadoopConfiguration hbase配置
     */
    public HbaseConnectionFactory(final Configuration hadoopConfiguration) {
        this.hadoopConfiguration = hadoopConfiguration;
    }

    /**
     * @param host    zookeeper地址
     * @param port    zookeeper端口
     * @param master  hbase主机
     * @param rootdir hdfs数据目录
     */
    public HbaseConnectionFactory(final String host, final String port, final String master, final String rootdir) {
        this.hadoopConfiguration = new Configuration();
        if (host == null)
            throw new ConnectionException("[" + HbaseConfig.ZOOKEEPER_QUORUM_PROPERTY + "] is required !");
        this.hadoopConfiguration.set(HbaseConfig.ZOOKEEPER_QUORUM_PROPERTY, host);
        if (port == null)
            throw new ConnectionException("[" + HbaseConfig.ZOOKEEPER_CLIENTPORT_PROPERTY + "] is required !");
        this.hadoopConfiguration.set(HbaseConfig.ZOOKEEPER_CLIENTPORT_PROPERTY, port);
        if (master != null)
            this.hadoopConfiguration.set(HbaseConfig.MASTER_PROPERTY, master);
        if (rootdir != null)
            this.hadoopConfiguration.set(HbaseConfig.ROOTDIR_PROPERTY, rootdir);
    }

    /**
     * @param properties 参数配置
     */
    public HbaseConnectionFactory(final Properties properties) {
        this.hadoopConfiguration = new Configuration();
        for (Entry<Object, Object> entry : properties.entrySet()) {
            this.hadoopConfiguration.set((String) entry.getKey(), (String) entry.getValue());
        }
    }

    /**
     * 获取HBASE链接
     * */
    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        Connection connection = this.createConnection();
        return new DefaultPooledObject<Connection>(connection);
    }

    /**
     * 销毁Hbase链接
     * */
    @Override
    public void destroyObject(PooledObject<Connection> p) throws Exception {
        Connection connection = p.getObject();
        if (connection != null)
            connection.close();
    }

    /**
     * 判断Hbase链接是否可用
     * */
    @Override
    public boolean validateObject(PooledObject<Connection> p) {
        Connection connection = p.getObject();
        if (connection != null)
            return ((!connection.isAborted()) && (!connection.isClosed()));
        return false;
    }

    /**
     * 创建Hbase链接
     * */
    @Override
    public Connection createConnection() throws Exception {
        Connection connection = org.apache.hadoop.hbase.client.ConnectionFactory
                .createConnection(hadoopConfiguration);
        return connection;
    }

    @Override
    public void activateObject(PooledObject<Connection> p) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void passivateObject(PooledObject<Connection> p) throws Exception {
        // TODO Auto-generated method stub
    }
}