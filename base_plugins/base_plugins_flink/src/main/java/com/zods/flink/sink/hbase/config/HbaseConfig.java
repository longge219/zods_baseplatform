package com.zods.flink.sink.hbase.config;
/**
 * @author jianglong
 * @description HbaseConfig参数配置
 * @date 2020-03-07
 **/
public interface HbaseConfig {

    /**地址*/
    public static final String DEFAULT_HOST = "localhost";

    /**端口*/
    public static final String DEFAULT_PORT = "2181";

    /**hbase主机*/
    public static final String DEFAULT_MASTER = null;

    /**hdfs目录*/
    public static final String DEFAULT_ROOTDIR = null;

    /**zookeeper地址*/
    public static final String ZOOKEEPER_QUORUM_PROPERTY = "hbase.zookeeper.quorum";

    /**zookeeper端口*/
    public static final String ZOOKEEPER_CLIENTPORT_PROPERTY = "hbase.zookeeper.property.clientPort";

    /**hbase主机*/
    public static final String MASTER_PROPERTY = "hbase.master";

    /**hdfs数据目录*/
    public static final String ROOTDIR_PROPERTY = "hbase.rootdir";
}
