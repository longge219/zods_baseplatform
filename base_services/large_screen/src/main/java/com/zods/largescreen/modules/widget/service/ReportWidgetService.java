package com.zods.largescreen.modules.widget.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.widget.controller.dto.ReportWidgetDto;
import com.zods.largescreen.modules.widget.controller.param.ReportWidgetParam;
import com.zods.largescreen.modules.widget.dao.entity.ReportWidget;
/**
 * @description ReportWidget 组件服务接口
 * @author jianglong
 * @date 2022-06-30
 **/
public interface ReportWidgetService extends GaeaBaseService<ReportWidgetParam, ReportWidget> {

    /**
     * 查询组件详情
     * @param widgetCode 组件编码
     * @return ResponseBean 返回封装对象
     */
    ReportWidgetDto getWidgetDetail(String widgetCode);

    /**更新组件*/
    void updateReportWidget(ReportWidgetDto dto);

}
