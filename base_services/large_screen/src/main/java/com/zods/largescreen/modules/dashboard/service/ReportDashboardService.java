package com.zods.largescreen.modules.dashboard.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.dashboard.controller.dto.ChartDto;
import com.zods.largescreen.modules.dashboard.controller.dto.ReportDashboardObjectDto;
import com.zods.largescreen.modules.dashboard.controller.param.ReportDashboardParam;
import com.zods.largescreen.modules.dashboard.dao.entity.ReportDashboard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @desc ReportDashboard 大屏设计服务接口
 * @author jianglong
 * @date 2022-06-22
 **/
public interface ReportDashboardService extends GaeaBaseService<ReportDashboardParam, ReportDashboard> {

    /***
     * 查询详情
     *
     * @param reportCode
     */
    ReportDashboardObjectDto getDetail(String reportCode);

    /***
     * 保存大屏设计
     *
     * @param dto
     */
    void insertDashboard(ReportDashboardObjectDto dto);


    /**
     * 获取单个图表数据
     * @param dto
     * @return
     */
    Object getChartData(ChartDto dto);


    /**
     * 导出大屏，zip文件
     * @param request
     * @param response
     * @param reportCode
     * @return
     */
    ResponseEntity<byte[]> exportDashboard(HttpServletRequest request, HttpServletResponse response, String reportCode, Integer showDataSet);

    /**
     * 导入大屏zip
     * @param file
     * @param reportCode
     * @return
     */
    void importDashboard(MultipartFile file, String reportCode);
}
