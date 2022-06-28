package com.zods.plugins.db.annotation.valid.em;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
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
