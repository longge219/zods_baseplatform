package com.zods.largescreen.modules.dashboardwidget.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.dashboardwidget.controller.param.ReportDashboardWidgetParam;
import com.zods.largescreen.modules.dashboardwidget.dao.entity.ReportDashboardWidget;
/**
 * @description ReportDashboardWidget 组件服务接口
 * @author jianglong
 * @date 2022-06-22
 **/
public interface ReportDashboardWidgetService extends GaeaBaseService<ReportDashboardWidgetParam, ReportDashboardWidget> {

    /***
     * 查询详情
     * @param id 组件ID
     */
    ReportDashboardWidget getDetail(Long id);
}
