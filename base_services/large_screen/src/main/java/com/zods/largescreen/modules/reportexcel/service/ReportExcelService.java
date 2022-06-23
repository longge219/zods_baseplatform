package com.zods.largescreen.modules.reportexcel.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.reportexcel.controller.dto.ReportExcelDto;
import com.zods.largescreen.modules.reportexcel.controller.param.ReportExcelParam;
import com.zods.largescreen.modules.reportexcel.dao.entity.ReportExcel;
/**
 * @desc  Excel报表-服务接口
 * @author jianglong
 * @date 2022-06-23
 **/
public interface ReportExcelService extends GaeaBaseService<ReportExcelParam, ReportExcel> {

    /**
     * 根据报表编码查询详情
     * @param reportCode
     * @return ReportExcelDto
     */
    ReportExcelDto detailByReportCode(String reportCode);

    /**
     * 报表预览
     * @param reportExcelDto
     * @return ReportExcelDto
     */
    ReportExcelDto preview(ReportExcelDto reportExcelDto);

    /**
     * 导出为excel
     * @param reportExcelDto
     * @return Boolean
     */
    Boolean exportExcel(ReportExcelDto reportExcelDto);

}
