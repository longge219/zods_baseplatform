package com.zods.largescreen.modules.report.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.report.controller.dto.ReportDto;
import com.zods.largescreen.modules.report.controller.param.ReportParam;
import com.zods.largescreen.modules.report.dao.entity.Report;
/**
 * @desc Report 报表服务接口
 * @author jianglong
 * @date 2022-06-23
 **/
public interface ReportService extends GaeaBaseService<ReportParam, Report> {

    /**删除报表*/
    void delReport(ReportDto reportDto);

    /**
     * 下载次数+1
     * @param reportCode 报表编码
     */
    void downloadStatistics(String reportCode);
}
