package com.zods.largescreen.modules.datasetparam.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description 数据集动态参数 dto
 * @createDate 2022-06-22
 */
@Data
public class DataSetParamDto extends GaeaBaseDTO implements Serializable {
    /** 数据集编码 */
     private String setCode;

    /** 参数名 */
     private String paramName;

    /** 参数描述 */
     private String paramDesc;

    /** 参数类型，字典= */
     private String paramType;

    /** 参数示例项 */
     private String sampleItem;

    /** 0--非必填 1--必填 DIC_NAME=REQUIRED_FLAG */
     private Integer requiredFlag;

    /** js校验字段值规则，满足校验返回 true */
     private String validationRules;

    /** 排序 */
     private Integer orderNum;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
     private Integer enableFlag;

    /** 0--未删除 1--已删除 DIC_NAME=DELETE_FLAG */
     private Integer deleteFlag;

}
