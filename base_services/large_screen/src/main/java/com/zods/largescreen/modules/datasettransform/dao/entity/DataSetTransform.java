package com.zods.largescreen.modules.datasettransform.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author jianglong
 * @version 1.0
 * @Description 数据集数据转换 entity
 * @createDate 2022-06-22
 */
@TableName(keepGlobalPrefix=true, value="large_scrren_report_data_set_transform")
@Data
public class DataSetTransform extends GaeaBaseEntity {
    @ApiModelProperty(value = "数据集编码")
    private String setCode;

    @ApiModelProperty(value = "数据转换类型，DIC_NAME=TRANSFORM_TYPE; js，javaBean，字典转换")
    private String transformType;

    @ApiModelProperty(value = "数据转换script,处理逻辑")
    private String transformScript;

    @ApiModelProperty(value = "排序,执行数据转换顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;

    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;


}
