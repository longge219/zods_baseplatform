package com.zods.largescreen.modules.widget.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.common.utils.GaeaBeanUtils;
import com.zods.largescreen.modules.widget.controller.dto.ReportWidgetDto;
import com.zods.largescreen.modules.widget.dao.ReportWidgetMapper;
import com.zods.largescreen.modules.widget.dao.entity.ReportWidget;
import com.zods.largescreen.modules.widget.service.ReportWidgetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @description  组件服务实现
 * @author jianglong
 * @date 2022-06-22
 **/
@Service
public class ReportWidgetServiceImpl implements ReportWidgetService {

    @Autowired
    private ReportWidgetMapper reportWidgetMapper;

    @Override
    public GaeaBaseMapper<ReportWidget> getMapper() {
      return reportWidgetMapper;
    }

    /**
     * 查询组件详情
     * @param widgetCode 组件编码
     * @return ResponseBean 返回封装对象
     */
    public ReportWidgetDto getWidgetDetail(String widgetCode){
        ReportWidgetDto reportWidgetDto = new ReportWidgetDto();
        ReportWidget reportWidget = this.selectOne("widget_code", widgetCode);
        if (null == reportWidget) {
            return reportWidgetDto;
        }
        GaeaBeanUtils.copyAndFormatter(reportWidget, reportWidgetDto);
        reportWidgetDto.setSetup(StringUtils.isNotBlank(reportWidget.getSetup()) ? JSONObject.parseObject(reportWidget.getSetup()) : new JSONObject());
        reportWidgetDto.setData(StringUtils.isNotBlank(reportWidget.getData()) ? JSONObject.parseObject(reportWidget.getData()) : new JSONObject());
        reportWidgetDto.setPosition(StringUtils.isNotBlank(reportWidget.getPosition()) ? JSONObject.parseObject(reportWidget.getPosition()) : new JSONObject());
        reportWidgetDto.setCollapse(StringUtils.isNotBlank(reportWidget.getCollapse()) ? JSONObject.parseObject(reportWidget.getCollapse()) : new JSONObject());
        return reportWidgetDto;
    }
    /**更新组件*/
    @Override
    @Transactional
    public void updateReportWidget(ReportWidgetDto dto){
        //实例化实体类
        ReportWidget reportWidget =  new ReportWidget();;
        //拷贝通用属性
        BeanUtils.copyProperties(dto, reportWidget);
        //拷贝需要转换类型的属性
        reportWidget.setSetup(dto.getSetup() != null ? JSONObject.toJSONString(dto.getSetup()) : "");
        reportWidget.setData(dto.getData() != null ? JSONObject.toJSONString(dto.getData()) : "");
        reportWidget.setPosition(dto.getPosition() != null ? JSONObject.toJSONString(dto.getPosition()) : "");
        reportWidget.setCollapse(dto.getCollapse() != null ? JSONObject.toJSONString(dto.getCollapse()) : "");
        this.update(reportWidget);
    }
}
