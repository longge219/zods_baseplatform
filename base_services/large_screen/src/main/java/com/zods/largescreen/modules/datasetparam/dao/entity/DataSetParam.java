package com.zods.largescreen.modules.datasetparam.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author jianglong
 * @version 1.0
 * @Description 数据集动态参数 entity
 * @createDate 2022-06-22
 */
@TableName(keepGlobalPrefix=true, value="large_scrren_report_data_set_param")
@Data
public class DataSetParam extends GaeaBaseEntity {
    @ApiModelProperty(value = "数据集编码")
    private String setCode;

    @ApiModelProperty(value = "参数名")
    private String paramName;

    @ApiModelProperty(value = "参数描述")
    private String paramDesc;

    @ApiModelProperty(value = "参数类型，字典=")
    private String paramType;

    @ApiModelProperty(value = "参数示例项")
    private String sampleItem;

    @ApiModelProperty(value = "0--非必填 1--必填 DIC_NAME=REQUIRED_FLAG")
    private Integer requiredFlag;

    @ApiModelProperty(value = "js校验字段值规则，满足校验返回 true")
    private String validationRules;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;

    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;


}
