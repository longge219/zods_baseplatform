package com.zods.largescreen.modules.widget.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zods.largescreen.common.annotation.Unique;
import com.zods.largescreen.common.code.ResponseCode;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
* @description 组件 entity
* @author jianglong
* @date 2022-06-22
**/
@TableName(keepGlobalPrefix=true, value="large_scrren_report_widget")
@Data
public class ReportWidget extends GaeaBaseEntity {

    @ApiModelProperty(value = "组件编码")
    @Unique(code = ResponseCode.WIDGET_CODE_ISEXIST)
    private String widgetCode;

    @ApiModelProperty(value = "组件名称")
    private String widgetName;

    @ApiModelProperty(value = "组件类型参考字典DASHBOARD_PANEL_TYPE")
    private String type;

    @ApiModelProperty(value = "组件的渲染属性json")
    private String setup;

    @ApiModelProperty(value = "组件的数据属性json")
    private String data;

    @ApiModelProperty(value = "组件的配置属性json")
    private String collapse;

    @ApiModelProperty(value = "组件的大小位置属性json")
    private String position;

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
