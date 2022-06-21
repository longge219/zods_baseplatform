package com.zods.largescreen.modules.report.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.report.controller.dto.ReportDto;
import com.zods.largescreen.modules.report.controller.param.ReportParam;
import com.zods.largescreen.modules.report.dao.entity.Report;

/**
 *
 * @author chenkening
 * @date 2021/3/26 10:35
 */
public interface ReportService extends GaeaBaseService<ReportParam, Report> {

    void delReport(ReportDto reportDto);

    /**
     * 下载次数+1
     * @param reportCode
     */
    void downloadStatistics(String reportCode);
}
