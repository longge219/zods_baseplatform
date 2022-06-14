package com.zods.largescreen.function.datasource.param;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
/**
 * @description 连接参数
 * @author jianglong
 * @date 2022-06-14
 **/
@Data
public class ConnectionParam implements Serializable {

    /** 数据源类型 DIC_NAME=SOURCE_TYPE; mysql，orace，sqlserver，elasticsearch，接口，javaBean，数据源类型字典中item-extend动态生成表单 */
    @NotBlank(message = "sourceType not empty")
    private String sourceType;

    /** 数据源连接配置json：
     * 关系库{ jdbcUrl:'', username:'', password:'','driverName':''}
     * ES-sql{ apiUrl:'http://127.0.0.1:9092/_xpack/sql?format=json','method':'POST','body':'{"query":"select 1"}' }
     * 接口{ apiUrl:'http://ip:port/url', method:'' } javaBean{ beanNamw:'xxx' } */
    @NotBlank(message = "sourceConfig not empty")
    private String sourceConfig;
}
