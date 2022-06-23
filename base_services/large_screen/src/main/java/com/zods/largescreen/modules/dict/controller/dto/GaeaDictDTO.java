package com.zods.largescreen.modules.dict.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @desc 数据字典 DTO
 * @author jianglong
 * @date 2022-06-23
 **/
@ApiModel(value = "")
@Data
public class GaeaDictDTO extends GaeaBaseDTO implements Serializable {
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
    /**
     * 字典编号
     */
    @ApiModelProperty(value = "字典编号")
    private String dictCode;
    /**
     * 字典描述
     */
    @ApiModelProperty(value = "字典描述")
    private String remark;


}
