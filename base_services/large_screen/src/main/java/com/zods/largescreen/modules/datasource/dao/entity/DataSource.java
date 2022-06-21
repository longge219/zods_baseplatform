package com.zods.largescreen.modules.datasource.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.code.ResponseCode;
import com.zods.largescreen.common.annotation.Unique;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @desc 数据源 entity
 * @author jianglong
 * @date 2022-06-16
 **/
@TableName(keepGlobalPrefix=true, value="large_screen_data_source")
@Data
public class DataSource extends GaeaBaseEntity {
    @ApiModelProperty(value = "数据源编码")
    @Unique(code = ResponseCode.SOURCE_CODE_ISEXIST)
    private String sourceCode;

    @ApiModelProperty(value = "数据源名称")
    private String sourceName;

    @ApiModelProperty(value = "数据源描述")
    private String sourceDesc;

    @ApiModelProperty(value = "数据源类型 DIC_NAME=SOURCE_TYPE; mysql，orace，sqlserver，elasticsearch，接口，javaBean，数据源类型字典中item-extend动态生成表单")
    private String sourceType;

    @ApiModelProperty(value = "数据源连接配置json：关系库{ jdbcUrl:'', username:'', password:'','driverName':''}ES-sql{ apiUrl:'http://127.0.0.1:9092/_xpack/sql?format=json','method':'POST','body':'{\"query\":\"select 1\"}' }  接口{ apiUrl:'http://ip:port/url', method:'' } javaBean{ beanNamw:'xxx' }")
    private String sourceConfig;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;

    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;


}
