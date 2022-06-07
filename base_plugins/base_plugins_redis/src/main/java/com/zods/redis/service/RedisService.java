package com.zods.redis.service;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.core.RedisTemplate;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2020/12/29 10:32
 */
public interface RedisService {
    /**
     * 通过key删除
     *
     * @param keys
     */
    void del(String... keys);

    /**
     * 通过key的前缀删除
     *
     * @param pre
     */
    void deleteByPrex(String pre);

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void set(String key, Serializable value, long liveTime);

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param obj
     */
    void set(String key, Serializable obj);

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    <T> T get(String key);


    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    void flushDB(RedisClusterNode node);

    /**
     * 查看redis里有多少数据
     */
    long dbSize();

    /**
     * 检查是否连接成功
     *
     * @return
     */
    String ping(RedisClusterNode node);

    /**
     * 获取spring RedisTemplate
     *
     * @return
     */
    RedisTemplate getTemplate();

    /**
     * 获取所有key
     *
     * @return
     */
    <T> T keys(String keys);

    /**
     * hash存储
     *
     * @return
     */
    void hSet(String key, String field, Serializable value);

    /**
     * hash获取
     *
     * @return
     */
    <T> T hGet(String key, String field);
}
