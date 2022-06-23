package com.zods.largescreen.modules.reportshare.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.reportshare.controller.dto.ReportShareDto;
import com.zods.largescreen.modules.reportshare.controller.param.ReportShareParam;
import com.zods.largescreen.modules.reportshare.dao.entity.ReportShare;
/**
* @desc ReportShare 报表分享服务接口
* @author jianglong
* @date 2022-06-23
**/
public interface ReportShareService extends GaeaBaseService<ReportShareParam, ReportShare> {

    /***
     * 查询详情
     * @param id 主键ID
     * @return ReportShare
     */
    ReportShare getDetail(Long id);

    /***
     * 查询详情
     * @param shareCode 分项编号
     * @return ReportShare
     */
    ReportShare detailByCode(String shareCode);

    /***
     * 添加分项
     * @param dto 报表分享 dto
     * @return ReportShareDto
     */
    ReportShareDto insertShare(ReportShareDto dto);


}
