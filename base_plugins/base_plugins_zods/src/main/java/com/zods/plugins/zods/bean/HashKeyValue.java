package com.zods.plugins.zods.bean;

import com.zods.plugins.zods.annotation.HashKey;

import java.io.Serializable;

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
