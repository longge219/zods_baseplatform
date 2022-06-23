package com.zods.largescreen.modules.dashboardwidget.controller;
import com.zods.largescreen.common.annotation.Permission;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description ReportDashboardWidget 组件controller
 * @author jianglong
 * @date 2022-06-22
 **/
@RestController
@Api(tags = "组件管理")
@Permission(code = "dashboardWidgetManage", name = "组件管理")
@RequestMapping("/reportDashboardWidget")
public class ReportDashboardWidgetController {
}
