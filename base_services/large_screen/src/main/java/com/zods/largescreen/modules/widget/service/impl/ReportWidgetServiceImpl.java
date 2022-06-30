package com.zods.largescreen.modules.widget.service.impl;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.widget.dao.ReportWidgetMapper;
import com.zods.largescreen.modules.widget.dao.entity.ReportWidget;
import com.zods.largescreen.modules.widget.service.ReportWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
