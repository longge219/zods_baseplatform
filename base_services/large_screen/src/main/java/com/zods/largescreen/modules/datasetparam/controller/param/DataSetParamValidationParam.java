package com.zods.largescreen.modules.datasetparam.controller.param;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description DataSetParam 数据集动态参数校验查询输入类
 * @createDate 2022-06-22
 */
@Data
public class DataSetParamValidationParam implements Serializable {

    /** 参数示例项 */
    @NotBlank(message = "sampleItem not empty")
    private String sampleItem;


    /** js校验字段值规则，满足校验返回 true */
    @NotBlank(message = "validationRules not empty")
    private String validationRules;
}
