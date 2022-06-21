package com.zods.largescreen.common.cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class RedisCacheHelper implements CacheHelper {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public RedisCacheHelper() {
    }

    public String stringGet(String key) {
        String regKey = this.regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return "";
        } else {
            BoundValueOperations<String, String> operations = this.stringRedisTemplate.boundValueOps(regKey);
            return (String)operations.get();
        }
    }

    public Boolean setIfAbsent(String key, String value) {
        String regKey = this.regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        } else {
            BoundValueOperations<String, String> operations = this.stringRedisTemplate.boundValueOps(regKey);
            return operations.setIfAbsent(value);
        }
    }

    public Long increment(String key) {
        BoundValueOperations<String, String> operations = this.stringRedisTemplate.boundValueOps(key);
        return operations.increment();
    }

    public void expire(String key, TimeUnit timeUnit, Long timeout) {
        this.stringRedisTemplate.expire(key, timeout, timeUnit);
    }

    public Long increment(String key, Long step) {
        BoundValueOperations<String, String> operations = this.stringRedisTemplate.boundValueOps(key);
        return operations.increment(step);
    }

    public boolean exist(String key) {
        return this.stringRedisTemplate.hasKey(key);
    }

    public Set<String> keys(String pattern) {
        return this.stringRedisTemplate.keys(pattern);
    }

    public void stringSet(String key, String value) {
        String regKey = this.regKey(key);
        if (!StringUtils.isBlank(regKey)) {
            BoundValueOperations<String, String> operations = this.stringRedisTemplate.boundValueOps(regKey);
            operations.set(value);
        }
    }

    public void stringSetExpire(String key, String value, long time, TimeUnit timeUnit) {
        String regKey = this.regKey(key);
        if (!StringUtils.isBlank(regKey)) {
            BoundValueOperations<String, String> operations = this.stringRedisTemplate.boundValueOps(regKey);
            operations.set(value, time, timeUnit);
        }
    }

    public void stringSetExpire(String key, String value, long seconds) {
        this.stringSetExpire(key, value, seconds, TimeUnit.SECONDS);
    }

    public Map<String, String> hashGet(String key) {
        BoundHashOperations<String, String, String> operations = this.stringRedisTemplate.boundHashOps(key);
        return operations.entries();
    }

    public String hashGetString(String key, String hashKey) {
        String regKey = this.regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return "";
        } else {
            String regHashKey = this.regKey(hashKey);
            if (StringUtils.isBlank(regHashKey)) {
                return "";
            } else {
                BoundHashOperations<String, String, String> operations = this.stringRedisTemplate.boundHashOps(regKey);
                if (hashKey.contains(",")) {
                    String[] split = hashKey.split(",");
                    String reduce = (String)Arrays.stream(split).reduce("", (all, item) -> {
                        if (StringUtils.isBlank(all)) {
                            all = (String)operations.get(item);
                        } else {
                            all = all + "," + (String)operations.get(item);
                        }

                        return all;
                    });
                    return reduce;
                } else {
                    return (String)operations.entries().get(regHashKey);
                }
            }
        }
    }

    public void hashDel(String key, String hashKey) {
        String regKey = this.regKey(key);
        if (!StringUtils.isBlank(regKey)) {
            String regHashKey = this.regKey(hashKey);
            if (!StringUtils.isBlank(regHashKey)) {
                BoundHashOperations<String, String, String> operations = this.stringRedisTemplate.boundHashOps(regKey);
                operations.delete(new Object[]{regHashKey});
            }
        }
    }

    public void hashBatchDel(String key, Set<String> hashKeys) {
        String regKey = this.regKey(key);
        if (!StringUtils.isBlank(regKey)) {
            BoundHashOperations<String, String, String> operations = this.stringRedisTemplate.boundHashOps(regKey);
            operations.delete(hashKeys.toArray(new String[0]));
        }
    }

    public boolean hashExist(String key, String hashKey) {
        String regKey = this.regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        } else {
            String regHashKey = this.regKey(hashKey);
            if (StringUtils.isBlank(regHashKey)) {
                return false;
            } else {
                BoundHashOperations<String, String, String> operations = this.stringRedisTemplate.boundHashOps(regKey);
                return operations.hasKey(regHashKey);
            }
        }
    }

    public boolean hashAnyExist(String key, String[] hashKeys) {
        String regKey = this.regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        } else {
            BoundHashOperations<String, String, String> operations = this.stringRedisTemplate.boundHashOps(regKey);
            String[] var5 = hashKeys;
            int var6 = hashKeys.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String hashKey = var5[var7];
                if (operations.hasKey(hashKey)) {
                    return true;
                }
            }

            return false;
        }
    }

    public void hashSet(String key, String hashKey, String hashValue) {
        String regKey = this.regKey(key);
        if (!StringUtils.isBlank(regKey) && !StringUtils.isBlank(hashKey)) {
            String regHashKey = this.regKey(hashKey);
            if (!StringUtils.isBlank(regHashKey)) {
                BoundHashOperations<String, String, String> operations = this.stringRedisTemplate.boundHashOps(regKey);
                operations.put(regHashKey, hashValue);
            }
        }
    }

    public void hashSet(String key, Map<String, String> hash) {
        String regKey = this.regKey(key);
        if (!StringUtils.isBlank(regKey)) {
            if (!CollectionUtils.isEmpty(hash)) {
                BoundHashOperations<String, String, String> operations = this.stringRedisTemplate.boundHashOps(regKey);
                operations.putAll(hash);
            }
        }
    }

    public boolean delete(String key) {
        String regKey = this.regKey(key);
        return StringUtils.isBlank(regKey) ? false : this.stringRedisTemplate.delete(key);
    }

    public boolean delete(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return false;
        } else {
            Long count = this.stringRedisTemplate.delete(keys);
            return count > 0L;
        }
    }

    public Long setAdd(String key, String[] values) {
        return this.setAdd(key, values, false);
    }

    public Long setAdd(String key, String[] values, boolean clear) {
        if (clear) {
            this.stringRedisTemplate.delete(key);
        }

        if (values != null && values.length == 0) {
            return 0L;
        } else {
            BoundSetOperations<String, String> setOperations = this.stringRedisTemplate.boundSetOps(key);
            return setOperations.add(values);
        }
    }

    public Set<String> setMembers(String key) {
        BoundSetOperations<String, String> setOperations = this.stringRedisTemplate.boundSetOps(key);
        return setOperations.members();
    }

    public Boolean setExist(String key, String value) {
        String regKey = this.regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        } else {
            BoundSetOperations<String, String> setOperations = this.stringRedisTemplate.boundSetOps(regKey);
            return setOperations.isMember(value);
        }
    }
}
