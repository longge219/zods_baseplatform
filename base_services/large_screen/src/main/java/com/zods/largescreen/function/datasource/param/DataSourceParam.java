package com.zods.largescreen.function.datasource.param;
import com.zods.plugins.zods.annotation.Query;
import com.zods.plugins.zods.constant.QueryEnum;
import com.zods.plugins.zods.curd.params.PageParam;
import lombok.Data;
import java.io.Serializable;
/**
 * @description 数据查询输入参数类
 * @author jianglong
 * @date 2022-06-14
 **/
@Data
public class DataSourceParam extends PageParam implements Serializable{

    /** 数据源名称 */
    @Query(QueryEnum.LIKE)
    private String sourceName;

    /** 数据源编码 */
    @Query(QueryEnum.LIKE)
    private String sourceCode;

    /** 数据源类型 DIC_NAME=SOURCE_TYPE; mysql，orace，sqlserver，elasticsearch，接口，javaBean，数据源类型字典中item-extend动态生成表单 */
    @Query(QueryEnum.EQ)
    private String sourceType;
}
