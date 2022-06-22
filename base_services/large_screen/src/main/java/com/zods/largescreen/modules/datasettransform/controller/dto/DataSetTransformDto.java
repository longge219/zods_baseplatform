package com.zods.largescreen.modules.datasettransform.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import lombok.Data;
import java.io.Serializable;
/**
 * @author jianglong
 * @version 1.0
 * @Description 数据集数据转换 dto
 * @createDate 2022-06-22
 */
@Data
public class DataSetTransformDto extends GaeaBaseDTO implements Serializable {
    /** 数据集编码 */
     private String setCode;

    /** 数据转换类型，DIC_NAME=TRANSFORM_TYPE; js，javaBean，字典转换 */
     private String transformType;

    /** 数据转换script,处理逻辑 */
     private String transformScript;

    /** 排序,执行数据转换顺序 */
     private Integer orderNum;

    /** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
     private Integer enableFlag;

    /** 0--未删除 1--已删除 DIC_NAME=DELETE_FLAG */
     private Integer deleteFlag;

}
