
package com.zods.largescreen.modules.dashboardwidget.service;

import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.dashboardwidget.controller.param.ReportDashboardWidgetParam;
import com.zods.largescreen.modules.dashboardwidget.dao.entity.ReportDashboardWidget;

/**
* @desc ReportDashboardWidget 大屏看板数据渲染服务接口
* @author Raod
* @date 2021-04-12 15:12:43.724
**/
public interface ReportDashboardWidgetService extends GaeaBaseService<ReportDashboardWidgetParam, ReportDashboardWidget> {

    /***
     * 查询详情
     *
     * @param id
     */
    ReportDashboardWidget getDetail(Long id);
}
