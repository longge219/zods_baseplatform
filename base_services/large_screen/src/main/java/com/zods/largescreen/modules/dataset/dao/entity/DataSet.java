package com.zods.largescreen.modules.dataset.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.annotation.Unique;
import com.zods.largescreen.common.code.ResponseCode;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @desc 数据集 entity
 * @author jianglong
 * @date 2022-06-22
 **/
@TableName(keepGlobalPrefix=true, value="large_scrren_report_data_set")
@Data
public class DataSet extends GaeaBaseEntity {
    @ApiModelProperty(value = "数据集编码")
    @Unique(code = ResponseCode.SET_CODE_ISEXIST)
    private String setCode;

    @ApiModelProperty(value = "数据集名称")
    private String setName;

    @ApiModelProperty(value = "数据集描述")
    private String setDesc;

    @ApiModelProperty(value = "数据集类型")
    private String setType;

    @ApiModelProperty(value = "数据源编码")
    private String sourceCode;

    @ApiModelProperty(value = "动态查询sql或者接口中的请求体")
    private String dynSentence;

    @ApiModelProperty(value = "结果案例")
    private String caseResult;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;

    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;


}
