package com.zods.largescreen.modules.widget.controller;
import com.zods.largescreen.common.annotation.log.GaeaAuditLog;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.common.utils.GaeaUtils;
import com.zods.largescreen.modules.widget.controller.dto.ReportWidgetDto;
import com.zods.largescreen.modules.widget.controller.param.ReportWidgetParam;
import com.zods.largescreen.modules.widget.dao.entity.ReportWidget;
import com.zods.largescreen.modules.widget.service.ReportWidgetService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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


    /**
     * 查询组件详情
     * @param widgetCode 组件编码
     * @return ResponseBean 返回封装对象
     */
    @GetMapping({"/detailByWidgetCode/{widgetCode}"})
    public ResponseBean getWidgetDetail(@PathVariable("widgetCode") String widgetCode) {
        return ResponseBean.builder().data(reportWidgetService.getWidgetDetail(widgetCode)).build();
    }


    @PutMapping
    @GaeaAuditLog(pageTitle = "修改")
    @Override
    public ResponseBean update(@RequestBody ReportWidgetDto dto) {
        ResponseBean responseBean = this.responseSuccess();
        reportWidgetService.updateReportWidget(dto);
        this.logger.info("{}更新组件结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(responseBean));
        return this.responseSuccess();
    }

}
