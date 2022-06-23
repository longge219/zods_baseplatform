package com.zods.largescreen.modules.reportexcel.controller;
import com.zods.largescreen.common.annotation.log.GaeaAuditLog;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.code.ResponseCode;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.reportexcel.controller.dto.ReportExcelDto;
import com.zods.largescreen.modules.reportexcel.controller.param.ReportExcelParam;
import com.zods.largescreen.modules.reportexcel.dao.entity.ReportExcel;
import com.zods.largescreen.modules.reportexcel.service.ReportExcelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenkening
 * @date 2021/4/13 15:12
 */
@RestController
@Api(tags = "报表表格管理")
@RequestMapping("/reportExcel")
public class ReportExcelController extends GaeaBaseController<ReportExcelParam, ReportExcel, ReportExcelDto> {

    @Autowired
    private ReportExcelService reportExcelService;

    @Override
    public GaeaBaseService<ReportExcelParam, ReportExcel> getService() {
        return reportExcelService;
    }

    @Override
    public ReportExcel getEntity() {
        return new ReportExcel();
    }

    @Override
    public ReportExcelDto getDTO() {
        return new ReportExcelDto();
    }


    @GetMapping("/detailByReportCode/{reportCode}")
    @GaeaAuditLog(pageTitle = "详情")
    public ResponseBean detailByReportCode(@PathVariable String reportCode) {
        ReportExcelDto reportExcelDto = reportExcelService.detailByReportCode(reportCode);
        return ResponseBean.builder().data(reportExcelDto).build();
    }

    @PostMapping("/preview")
    @GaeaAuditLog(pageTitle = "预览")
    public ResponseBean preview(@RequestBody ReportExcelDto reportExcelDto) {
        ReportExcelDto result = reportExcelService.preview(reportExcelDto);
        return ResponseBean.builder().data(result).build();
    }


    @PostMapping("/exportExcel")
    @GaeaAuditLog(pageTitle = "报表导出")
    public ResponseBean exportExcel(@RequestBody ReportExcelDto reportExcelDto) {
        return ResponseBean.builder().code(ResponseCode.SUCCESS_CODE)
                .data(reportExcelService.exportExcel(reportExcelDto))
                .message("导出成功，请稍后在文件管理中查看").build();
    }
}
