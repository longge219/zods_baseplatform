package com.zods.largescreen.modules.report.controller;
import com.zods.largescreen.common.annotation.Permission;
import com.zods.largescreen.common.annotation.log.GaeaAuditLog;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.report.controller.dto.ReportDto;
import com.zods.largescreen.modules.report.controller.param.ReportParam;
import com.zods.largescreen.modules.report.dao.entity.Report;
import com.zods.largescreen.modules.report.service.ReportService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author chenkening
 * @date 2021/3/26 10:19
 */
@RestController
@Api(tags = "报表数据管理")
@Permission(code = "reportManage", name = "报表管理")
@RequestMapping("/report")
public class ReportController extends GaeaBaseController<ReportParam, Report, ReportDto> {

    @Autowired
    private ReportService reportService;

    @Override
    public GaeaBaseService<ReportParam, Report> getService() {
        return reportService;
    }

    @Override
    public Report getEntity() {
        return new Report();
    }

    @Override
    public ReportDto getDTO() {
        return new ReportDto();
    }

    @DeleteMapping("/delReport")
    @Permission(code = "delete", name = "删除")
    @GaeaAuditLog(pageTitle = "删除")
    public ResponseBean delReport(@RequestBody ReportDto reportDto) {
        reportService.delReport(reportDto);
        return ResponseBean.builder().build();
    }
}
