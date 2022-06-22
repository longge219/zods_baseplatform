package com.zods.largescreen.modules.dashboardwidget.service.impl;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.dashboardwidget.dao.ReportDashboardWidgetMapper;
import com.zods.largescreen.modules.dashboardwidget.dao.entity.ReportDashboardWidget;
import com.zods.largescreen.modules.dashboardwidget.service.ReportDashboardWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @description ReportDashboardWidget 组件服务实现
 * @author jianglong
 * @date 2022-06-22
 **/
@Service
//@RequiredArgsConstructor
public class ReportDashboardWidgetServiceImpl implements ReportDashboardWidgetService {

    @Autowired
    private ReportDashboardWidgetMapper reportDashboardWidgetMapper;

    @Override
    public GaeaBaseMapper<ReportDashboardWidget> getMapper() {
      return reportDashboardWidgetMapper;
    }

    @Override
    public ReportDashboardWidget getDetail(Long id) {
        ReportDashboardWidget reportDashboardWidget = this.selectOne(id);
        return reportDashboardWidget;
    }
}
