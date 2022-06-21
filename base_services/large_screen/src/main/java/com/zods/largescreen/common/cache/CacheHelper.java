package com.zods.largescreen.common.cache;
import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public interface CacheHelper {
    default String stringGet(String key) {
        return null;
    }

    default Boolean setIfAbsent(String key, String value) {
        return false;
    }

    default Long increment(String key) {
        return 0L;
    }

    default void expire(String key, TimeUnit timeUnit, Long timeout) {
    }

    default Long increment(String key, Long step) {
        return 0L;
    }

    default boolean exist(String key) {
        return false;
    }

    default Set<String> keys(String pattern) {
        return new HashSet();
    }

    default void stringSet(String key, String value) {
    }

    default void stringSetExpire(String key, String value, long time, TimeUnit timeUnit) {
    }

    default String regKey(String key) {
        return key.trim();
    }

    default void stringSetExpire(String key, String value, long seconds) {
    }

    default Map<String, String> hashGet(String key) {
        return new HashMap();
    }

    default String hashGetString(String key, String hashKey) {
        return null;
    }

    default void hashDel(String key, String hashKey) {
    }

    default void hashBatchDel(String key, Set<String> hashKeys) {
    }

    default boolean hashExist(String key, String hashKey) {
        return false;
    }

    default boolean hashAnyExist(String key, String[] hashKeys) {
        return false;
    }

    default void hashSet(String key, String hashKey, String hashValue) {
    }

    default void hashSet(String key, Map<String, String> hash) {
    }

    default boolean delete(String key) {
        return false;
    }

    default boolean delete(List<String> keys) {
        return false;
    }

    default Long setAdd(String key, String[] values) {
        return 0L;
    }

    default Long setAdd(String key, String[] values, boolean clear) {
        return 0L;
    }

    default Set<String> setMembers(String key) {
        return new HashSet();
    }

    default Boolean setExist(String key, String value) {
        return false;
    }
}
