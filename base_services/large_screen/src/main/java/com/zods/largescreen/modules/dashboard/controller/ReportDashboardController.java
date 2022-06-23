package com.zods.largescreen.modules.dashboard.controller;
import com.zods.largescreen.common.annotation.log.GaeaAuditLog;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.modules.dashboard.controller.dto.ChartDto;
import com.zods.largescreen.modules.dashboard.controller.dto.ReportDashboardObjectDto;
import com.zods.largescreen.modules.dashboard.service.ReportDashboardService;
import com.zods.largescreen.modules.reportshare.controller.dto.ReportShareDto;
import com.zods.largescreen.modules.reportshare.service.ReportShareService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @desc ReportDashboard 大屏设计controller
 * @author jianglong
 * @date 2022-06-22
 **/
@RestController
@Api(tags = "大屏设计管理")
@RequestMapping("/reportDashboard")
public class ReportDashboardController {

    @Autowired
    private ReportDashboardService reportDashboardService;

    @Autowired
    private ReportShareService reportShareService;

    /**
     * 预览、查询大屏详情
     * @param reportCode 报表编码
     * @return ResponseBean 返回封装对象
     */
    @GetMapping({"/{reportCode}"})
    public ResponseBean detail(@PathVariable("reportCode") String reportCode) {
        return ResponseBean.builder().data(reportDashboardService.getDetail(reportCode)).build();
    }

    /**
     * 保存大屏设计
     * @param dto 大屏设计dto
     * @return ResponseBean 返回封装对象
     */
    @PostMapping
    @GaeaAuditLog(pageTitle = "新增")
    public ResponseBean insert(@RequestBody ReportDashboardObjectDto dto) {
        reportDashboardService.insertDashboard(dto);
        return ResponseBean.builder().build();
    }


    /**
     * 获取去单个图层数据
     * @param dto 图表dto
     * @return ResponseBean 返回封装对象
     */
    @PostMapping("/getData")
    public ResponseBean getData(@RequestBody ChartDto dto) {
        return ResponseBean.builder().data(reportDashboardService.getChartData(dto)).build();
    }


    /**
     * 导出大屏
     * @param reportCode 大屏报表编号
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDashboard(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam("reportCode") String reportCode, @RequestParam(value = "showDataSet",required = false, defaultValue = "1") Integer showDataSet) {
        return reportDashboardService.exportDashboard(request, response, reportCode, showDataSet);
    }

    /**
     * 导入大屏
     * @param file  导入的zip文件
     * @param reportCode 大屏报表编号
     * @return
     */
    @PostMapping("/import/{reportCode}")
    public ResponseBean importDashboard(@RequestParam("file") MultipartFile file, @PathVariable("reportCode") String reportCode) {
        reportDashboardService.importDashboard(file, reportCode);
        return ResponseBean.builder().build();
    }

    @PostMapping("/share")
    @GaeaAuditLog(pageTitle = "分享")
    public ResponseBean share(@Validated @RequestBody ReportShareDto dto) {
        return ResponseBean.builder().data(reportShareService.insertShare(dto)).build();
    }

}
