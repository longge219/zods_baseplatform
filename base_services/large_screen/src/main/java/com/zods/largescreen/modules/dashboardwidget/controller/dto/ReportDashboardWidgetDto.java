package com.zods.largescreen.modules.dashboardwidget.controller.dto;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import java.io.Serializable;
/**
 * @description 大屏组件 dto
 * @author jianglong
 * @date 2022-06-22
 **/
@Data
public class ReportDashboardWidgetDto implements Serializable {

    /**
     * 组件类型参考字典DASHBOARD_PANEL_TYPE
     */
    private String type;

    /**
     * value
     */
    private ReportDashboardWidgetValueDto value;

    /**
     * options
     */
    private JSONObject options;

}
