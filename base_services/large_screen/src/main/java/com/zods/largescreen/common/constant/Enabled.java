package com.zods.largescreen.common.constant;
import com.zods.largescreen.common.annotation.valid.em.EnumInterface;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public enum Enabled implements EnumInterface<Integer> {
    YES(1),
    NO(0);

    private Integer value;

    private Enabled(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public Boolean exist(Integer value) {
        return this.getValue().equals(value);
    }
}
