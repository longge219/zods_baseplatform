package com.zods.largescreen.modules.widget.controller;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.widget.controller.dto.ReportWidgetDto;
import com.zods.largescreen.modules.widget.controller.param.ReportWidgetParam;
import com.zods.largescreen.modules.widget.dao.entity.ReportWidget;
import com.zods.largescreen.modules.widget.service.ReportWidgetService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @description 组件controller
 * @author jianglong
 * @date 2022-06-22
 **/
@RestController
@Api(tags = "组件管理")
@RequestMapping("/reportWidget")
public class ReportWidgetController  extends GaeaBaseController<ReportWidgetParam, ReportWidget, ReportWidgetDto> {

    @Autowired
    private ReportWidgetService reportWidgetService;

    @Override
    public GaeaBaseService<ReportWidgetParam, ReportWidget> getService() {
        return reportWidgetService;
    }

    @Override
    public ReportWidget getEntity() {
        return new ReportWidget();
    }

    @Override
    public ReportWidgetDto getDTO() {
        return new ReportWidgetDto();
    }

}
