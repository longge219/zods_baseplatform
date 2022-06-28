package com.zods.plugins.db.constant;
import com.zods.plugins.db.annotation.valid.em.EnumInterface;

/**
 * @author jianglong
 * @version 1.0
 * @Description 是否可用注解
 * @createDate 2022-06-20
 */
public enum Deleted implements EnumInterface<Integer> {

    YES(1),

    NO(0);

    private Integer value;

    private Deleted(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public Boolean exist(Integer value) {
        return this.getValue().equals(value);
    }
}
