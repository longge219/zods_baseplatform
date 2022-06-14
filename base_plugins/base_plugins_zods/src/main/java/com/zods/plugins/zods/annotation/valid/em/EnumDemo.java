package com.zods.plugins.zods.annotation.valid.em;


public enum EnumDemo implements EnumInterface<Integer> {
    RED(1),
    GREEN(2);

    private Integer value;

    private EnumDemo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public Boolean exist(Integer value) {
        return this.value.equals(value);
    }
}
