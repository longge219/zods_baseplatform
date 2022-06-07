package com.zods.redis.service.impl;
import com.zods.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
/**
 * @author jianglong
 * @version 1.0
 * @Description 常用的 Redis操作
 * @createDate 2020/12/29 10:32
 */
@Component
public class RedisServiceImpl implements RedisService {
    Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void del(String... keys) {
        ArrayList<String> keyCollection = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            keyCollection.add(keys[i]);
        }
        redisTemplate.delete(keyCollection);
    }

    @Override
    public void deleteByPrex(String prex) {
        Set<String> keys = redisTemplate.keys(prex);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void set(String key, Serializable obj, long liveTime) {
        redisTemplate.opsForValue().set(key, obj, liveTime, TimeUnit.SECONDS);
    }


    @Override
    public void set(String key, Serializable obj) {
        /*默认只保存1小时*/
        set(key, obj, 3600L);
    }


    @Override
    public <T> T get(String key) {
        try {
            T obj = (T) redisTemplate.opsForValue().get(key);
            return obj;
        } catch (Exception e) {
            del(key);
            return null;
        }
    }

    @Override
    public boolean exists(String key) {
        return get(key) != null;
    }

    @Override
    public void flushDB(RedisClusterNode node) {
        redisTemplate.opsForCluster().flushDb(node);
    }

    @Override
    public long dbSize() {
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.dbSize();
            }
        });
    }

    @Override
    public String ping(RedisClusterNode node) {
        return redisTemplate.opsForCluster().ping(node);
    }

    @Override
    public <T> T keys(String keys) {
        return (T) redisTemplate.opsForHash().keys(keys);

    }

    @Override
    public void hSet(String key, String field, Serializable obj) {
        redisTemplate.opsForHash().put(key, field, obj);
    }

    @Override
    public <T> T hGet(String key, String field) {
        try {
            T obj = (T) redisTemplate.opsForHash().get(key, field);
            return obj;
        } catch (Exception e) {
            redisTemplate.opsForHash().delete(key, field);
            return null;
        }
    }

    @Override
    public RedisTemplate getTemplate() {
        return redisTemplate;
    }
}
