package com.zods.largescreen.modules.reportshare.controller;
import com.zods.largescreen.common.annotation.AccessKey;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.common.utils.GaeaBeanUtils;
import com.zods.largescreen.common.utils.GaeaUtils;
import com.zods.largescreen.modules.reportshare.controller.dto.ReportShareDto;
import com.zods.largescreen.modules.reportshare.controller.param.ReportShareParam;
import com.zods.largescreen.modules.reportshare.dao.entity.ReportShare;
import com.zods.largescreen.modules.reportshare.service.ReportShareService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @author jianglong
 * @desc 报表分享 controller
 * @date 2022-06-23
 **/
@RestController
@Api(tags = "报表分享管理")
@RequestMapping("/reportShare")
public class ReportShareController extends GaeaBaseController<ReportShareParam, ReportShare, ReportShareDto> {

    @Autowired
    private ReportShareService reportShareService;

    @Override
    public GaeaBaseService<ReportShareParam, ReportShare> getService() {
        return reportShareService;
    }

    @Override
    public ReportShare getEntity() {
        return new ReportShare();
    }

    @Override
    public ReportShareDto getDTO() {
        return new ReportShareDto();
    }


    @GetMapping({"/{id}"})
    @AccessKey
    @Override
    public ResponseBean detail(@PathVariable("id") Long id) {
        this.logger.info("{}根据ID查询服务开始，id为：{}", this.getClass().getSimpleName(), id);
        ReportShare result = reportShareService.getDetail(id);
        ReportShareDto dto = this.getDTO();
        GaeaBeanUtils.copyAndFormatter(result, dto);
        ResponseBean responseBean = this.responseSuccessWithData(this.resultDtoHandle(dto));
        this.logger.info("{}根据ID查询结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(responseBean));
        return responseBean;
    }

    @GetMapping({"/detailByCode"})
    public ResponseBean detailByCode(@RequestParam("shareCode") String shareCode) {
        return ResponseBean.builder().data(reportShareService.detailByCode(shareCode)).build();
    }

}
