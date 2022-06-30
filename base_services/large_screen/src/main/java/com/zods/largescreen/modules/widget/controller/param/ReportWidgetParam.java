package com.zods.largescreen.modules.widget.controller.param;
import com.zods.largescreen.common.annotation.Query;
import com.zods.largescreen.common.constant.QueryEnum;
import com.zods.largescreen.common.curd.params.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @description 组件查询输入参数类
 * @author jianglong
 * @date 2022-06-22
 **/
@Data
public class ReportWidgetParam extends PageParam implements Serializable{

    @ApiModelProperty(value = "组件编码")
    @Query(QueryEnum.EQ)
    private String widgetCode;

    @ApiModelProperty(value = "组件名称")
    @Query(QueryEnum.LIKE)
    private String widgetName;

}
