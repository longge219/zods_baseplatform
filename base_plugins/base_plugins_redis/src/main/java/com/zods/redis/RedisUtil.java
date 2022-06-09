package com.zods.redis;

import io.lettuce.core.KeyScanCursor;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScanCursor;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Wangchao
 * @version 1.0
 * @Description Redis工具类
 * @createDate 2020/12/29 10:22
 */
public class RedisUtil {

    /**
     * 获取 指定格式的所有key
     * 迭代执行 SCAN 0 MATCH {pattern} COUNT 10000
     *
     * @param pattern 匹配规则
     * @return 指定格式的所有key
     */
    public static List<String> scanKeys(RedisTemplate redisTemplate, String pattern) {
        //SCAN 0 MATCH {pattern} COUNT 10000
        return (List<String>) redisTemplate.execute(connection -> {
            //scan 迭代遍历键，返回的结果可能会有重复，需要客户端去重复
            Set<String> redisKeys = new HashSet<>();
            //lettuce 原生api
            RedisAsyncCommands conn = (RedisAsyncCommands) connection.getNativeConnection();
            //游标
            ScanCursor curs = ScanCursor.INITIAL;
            try {
                //采用 SCAN 命令，迭代遍历所有key
                while (!curs.isFinished()) {
                    long count = 10000L;
                    ScanArgs args = ScanArgs.Builder.matches("*" + pattern + "*").limit(count);
                    RedisFuture<KeyScanCursor<byte[]>> future = conn.scan(curs, args);
                    KeyScanCursor<byte[]> keyCurs = future.get();
                    List<byte[]> ks = keyCurs.getKeys();
                    Set<String> set = ks.stream().map(bytes -> new String(bytes, StandardCharsets.UTF_8)).collect(Collectors.toSet());
                    redisKeys.addAll(set);
                    curs = keyCurs;
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            return new ArrayList<>(redisKeys);
        }, true);
    }

    /**
     * Redis中数据是否还存活
     *
     * @param cacheKey
     * @param redisTemplate
     * @return
     */
    public static boolean cacheIsAlive(String cacheKey, StringRedisTemplate redisTemplate) {
        List<String> keyList = RedisUtil.scanKeys(redisTemplate, cacheKey);
        if (keyList.size() >= 1) {
            return true;
        }
        return false;
    }

    /**
     * 根据key查询redis中value
     *
     * @param redisTemplate
     * @param key
     * @return
     */
    public static String getRedisValue(RedisTemplate redisTemplate, String key) {
        return (String) redisTemplate.opsForValue().get(scanKeys(redisTemplate, key).get(0));
    }

    /**
     * 返回 key 的剩余的过期时间（指定单位）
     *
     * @param redisTemplate
     * @param key           key
     * @param unit          时间单位
     * @return 过期时间
     */
    public static Long getExpire(RedisTemplate redisTemplate, String key, TimeUnit unit) {
        return redisTemplate.getExpire(scanKeys(redisTemplate, key).get(0), unit);

    }

    /**
     * 删除Redis中包含key的所有缓存
     *
     * @param key
     * @param redisTemplate
     */
    public static void blurDeleteByKey(String key, StringRedisTemplate redisTemplate) {
        List<String> keyList = scanKeys(redisTemplate, key);
        redisTemplate.delete(keyList);
    }

    /**
     * 批量按照相关时间规格缓存数据
     *
     * @param redisTemplate
     * @param unit
     * @param timeout
     * @param data
     */
    public static void saveMultiValue(StringRedisTemplate redisTemplate, TimeUnit unit, long timeout, Map<String, String> data) {
        data.forEach((k, v) -> {
            redisTemplate.opsForValue().set(k, v, timeout, unit);
        });
    }

    /**
     * 批量模糊删除对应key在redis中缓存数据
     *
     * @param redisTemplate
     * @param keys
     */
    public static void deleteMultiValue(StringRedisTemplate redisTemplate, String... keys) {
        if (keys != null && keys.length > 0) {
            Arrays.stream(keys).forEach(k -> blurDeleteByKey(k, redisTemplate));
        }
    }

    /**
     * 批量模糊删除对应key在redis中缓存数据
     *
     * @param redisTemplate
     * @param keys
     */
    public static void deleteMultiValue(StringRedisTemplate redisTemplate, List<String> keys) {
        if (!CollectionUtils.isEmpty(keys)) {
            keys.forEach(k -> blurDeleteByKey(k, redisTemplate));
        }
    }

}
