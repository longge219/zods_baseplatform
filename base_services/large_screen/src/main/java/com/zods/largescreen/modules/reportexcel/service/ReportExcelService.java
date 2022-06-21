package com.zods.largescreen.modules.reportexcel.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.reportexcel.controller.dto.ReportExcelDto;
import com.zods.largescreen.modules.reportexcel.controller.param.ReportExcelParam;
import com.zods.largescreen.modules.reportexcel.dao.entity.ReportExcel;
/**
 * TODO
 *
 * @author chenkening
 * @date 2021/4/13 15:14
 */
public interface ReportExcelService extends GaeaBaseService<ReportExcelParam, ReportExcel> {

    /**
     * 根据报表编码查询详情
     *
     * @param reportCode
     * @return
     */
    ReportExcelDto detailByReportCode(String reportCode);

    /**
     * 报表预览
     *
     * @param reportExcelDto
     * @return
     */
    ReportExcelDto preview(ReportExcelDto reportExcelDto);


    /**
     * 导出为excel
     *
     * @param reportExcelDto
     * @return
     */
    Boolean exportExcel(ReportExcelDto reportExcelDto);

//    Boolean exportPdf(ReportExcelDto reportExcelDto);
}
