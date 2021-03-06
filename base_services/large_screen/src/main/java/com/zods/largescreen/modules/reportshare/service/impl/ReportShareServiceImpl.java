package com.zods.largescreen.modules.reportshare.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zods.largescreen.common.code.ResponseCode;
import com.zods.largescreen.common.constant.BaseOperationEnum;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.common.exception.BusinessException;
import com.zods.largescreen.common.exception.BusinessExceptionBuilder;
import com.zods.largescreen.enums.EnableFlagEnum;
import com.zods.largescreen.modules.reportshare.controller.dto.ReportShareDto;
import com.zods.largescreen.modules.reportshare.dao.ReportShareMapper;
import com.zods.largescreen.modules.reportshare.dao.entity.ReportShare;
import com.zods.largescreen.modules.reportshare.service.ReportShareService;
import com.zods.largescreen.util.DateUtil;
import com.zods.largescreen.util.JwtUtil;
import com.zods.largescreen.util.MD5Util;
import com.zods.largescreen.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* @desc ReportShare 报表分享服务实现
* @author jianglong
* @date 2022-06-23
**/
@Service
public class ReportShareServiceImpl implements ReportShareService {

    /**默认跳转路由为aj的页面*/
    private static final String SHARE_FLAG = "#/aj/";

    private static final String SHARE_URL = "#";

    @Autowired
    private ReportShareMapper reportShareMapper;

    @Override
    public GaeaBaseMapper<ReportShare> getMapper() {
      return reportShareMapper;
    }

    @Override
    public void processBeforeOperation(ReportShare entity, BaseOperationEnum operationEnum) throws BusinessException {
        switch (operationEnum) {
            case INSERT:
                init(entity);
                break;
            default:

                break;
        }
    }

    /***
     * 查询详情
     * @param id 主键ID
     * @return ReportShare
     */
    @Override
    public ReportShare getDetail(Long id) {
        ReportShare reportShare = this.selectOne(id);
        return reportShare;
    }

    /***
     * 查询详情
     * @param shareCode 分项编号
     * @return ReportShare
     */
    @Override
    public ReportShare detailByCode(String shareCode) {
        LambdaQueryWrapper<ReportShare> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ReportShare::getShareCode, shareCode);
        wrapper.eq(ReportShare::getEnableFlag, EnableFlagEnum.ENABLE.getCodeValue());
        ReportShare reportShare = selectOne(wrapper);
        if (null == reportShare) {
            throw BusinessExceptionBuilder.build(ResponseCode.REPORT_SHARE_LINK_INVALID);
        }
        //解析jwt token，获取密码
        String password = JwtUtil.getPassword(reportShare.getShareToken());
        if (StringUtils.isNotBlank(password)) {
            //md5加密返回
            reportShare.setSharePassword(MD5Util.encrypt(password));
        }
        return reportShare;
    }

    /***
     * 添加分项
     * @param dto 报表分享 dto
     * @return ReportShareDto
     */
    @Override
    public ReportShareDto insertShare(ReportShareDto dto) {
        //设置分享码
        if (dto.isSharePasswordFlag()) {
            dto.setSharePassword(UuidUtil.getRandomPwd(4));
        }

        ReportShareDto reportShareDto = new ReportShareDto();
        ReportShare entity = new ReportShare();
        BeanUtils.copyProperties(dto, entity);
        insert(entity);
        //将分享链接返回
        reportShareDto.setShareUrl(entity.getShareUrl());
        reportShareDto.setSharePassword(dto.getSharePassword());
        return reportShareDto;
    }


    /**新增初始化*/
    private void init(ReportShare entity) {
        //前端地址  window.location.href https://report.anji-plus.com/index.html#/report/bigscreen
        //截取#之前的内容
        //http://localhost:9528/#/bigscreen/viewer?reportCode=bigScreen2
        //http://127.0.0.1:9095/reportDashboard/getData
        String shareCode = UuidUtil.generateShortUuid();
        entity.setShareCode(shareCode);
        if (entity.getShareUrl().contains(SHARE_URL)) {
            String prefix = entity.getShareUrl().substring(0, entity.getShareUrl().indexOf("#"));
            entity.setShareUrl(prefix + SHARE_FLAG + shareCode);
        } else {
            entity.setShareUrl(entity.getShareUrl() + SHARE_FLAG + shareCode);
        }
        entity.setShareValidTime(DateUtil.getFutureDateTmdHms(entity.getShareValidType()));
        entity.setShareToken(JwtUtil.createToken(entity.getReportCode(), shareCode, entity.getSharePassword(), entity.getShareValidTime()));
    }
}
