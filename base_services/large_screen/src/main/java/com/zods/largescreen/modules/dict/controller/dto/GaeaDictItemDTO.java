package com.zods.largescreen.modules.dict.controller.dto;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @desc 数据字典项 DTO
 * @author jianglong
 * @date 2022-06-23
 **/
@ApiModel(value = "数据字典项")
@Data
public class GaeaDictItemDTO extends GaeaBaseDTO implements Serializable {
    /**
     * 数据字典编码
     */
    @ApiModelProperty(value = "数据字典编码")
    private String dictCode;
    /**
     * 字典项名称
     */
    @ApiModelProperty(value = "字典项名称")
    private String itemName;
    /**
     * 字典项值
     */
    @ApiModelProperty(value = "字典项值")
    private String itemValue;

    /**
     * 字典项扩展
     */
    @ApiModelProperty(value = "字典项扩展")
    private String itemExtend;
    /**
     * 语言标识
     */
    @ApiModelProperty(value = "语言标识")
    private String locale;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 1：启用，0:禁用
     */
    private Integer enabled;
}
