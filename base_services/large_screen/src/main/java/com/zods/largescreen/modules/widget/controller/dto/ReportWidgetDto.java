package com.zods.largescreen.modules.widget.controller.dto;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.common.curd.dto.GaeaBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @description 组件 dto
 * @author jianglong
 * @date 2022-06-22
 **/
@Data
public class ReportWidgetDto extends GaeaBaseDTO implements Serializable {

    @ApiModelProperty(value = "组件编码")
    private String widgetCode;

    @ApiModelProperty(value = "组件名称")
    private String widgetName;

    @ApiModelProperty(value = "组件类型参考字典DASHBOARD_PANEL_TYPE")
    private String type;

    @ApiModelProperty(value = "组件的渲染属性json")
    private JSONObject setup;

    @ApiModelProperty(value = "组件的数据属性json")
    private JSONObject data;

    @ApiModelProperty(value = "组件的配置属性json")
    private JSONObject collapse;

    @ApiModelProperty(value = "组件的大小位置属性json")
    private JSONObject position;

    @ApiModelProperty(value = "options配置项")
    private String options;

    @ApiModelProperty(value = "自动刷新间隔秒")
    private Integer refreshSeconds;

    @ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
    private Integer enableFlag;

    @ApiModelProperty(value = " 0--未删除 1--已删除 DIC_NAME=DEL_FLAG")
    private Integer deleteFlag;

    @ApiModelProperty(value = "排序，图层的概念")
    private Long sort;

}
