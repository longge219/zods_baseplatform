package com.zods.largescreen.modules.reportexcel.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @desc ReportExcel Excel报表实体类
 * @author jianglong
 * @date 2022-06-23
 **/
@TableName(value = "large_scrren_report_excel")
@Data
public class ReportExcel extends GaeaBaseEntity {

    @ApiModelProperty(value = "报表编码")
    private String reportCode;

    @ApiModelProperty(value = "数据集编码，以|分割")
    private String setCodes;

    @ApiModelProperty(value = "数据集查询参数")
    private String setParam;

    @ApiModelProperty(value = "报表json字符串")
    private String jsonStr;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;

    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;
}
