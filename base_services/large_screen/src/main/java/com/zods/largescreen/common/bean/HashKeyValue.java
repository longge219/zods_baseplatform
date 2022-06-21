package com.zods.largescreen.common.bean;


import com.zods.largescreen.common.annotation.HashKey;

import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class HashKeyValue implements Serializable {
    private String key;
    private String value;
    private HashKey hashKey;

    public HashKeyValue() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HashKey getHashKey() {
        return this.hashKey;
    }

    public void setHashKey(HashKey hashKey) {
        this.hashKey = hashKey;
    }
}
