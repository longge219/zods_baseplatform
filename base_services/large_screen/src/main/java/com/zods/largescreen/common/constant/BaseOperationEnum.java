package com.zods.largescreen.common.constant;
/**
 * @author jianglong
 * @version 1.0
 * @Description 基础操作枚举类型
 * @createDate 2022-06-20
 */
public enum BaseOperationEnum {
    INSERT, //添加
    UPDATE, //修改
    DELETE, //删除
    DELETE_BATCH; //批量删除
    private BaseOperationEnum() {
    }
}
