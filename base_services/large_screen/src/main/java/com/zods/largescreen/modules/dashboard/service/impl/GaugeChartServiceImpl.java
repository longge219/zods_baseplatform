package com.zods.largescreen.modules.dashboard.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.modules.dashboard.controller.dto.ChartDto;
import com.zods.largescreen.modules.dashboard.service.ChartStrategyService;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * @desc 饼图或者空心饼图或者漏斗图
 * @author jianglong
 * @date 2022-06-22
 **/
@Component
public class GaugeChartServiceImpl implements ChartStrategyService {

    /**图表类型*/
    @Override
    public String type() {
        return "widget-gauge";
    }

    /**
     * 针对每种图表类型做单独的数据转换解析
     * @param dto 图表dto
     * @param data 图表数集结果
     * @return object
     */
    @Override
    public Object transform(ChartDto dto, List<JSONObject> data) {

//        return "{\"value\": 50, \"name\": \"名称1\", \"unit\": \"%\"}";
        return data;
    }




}
