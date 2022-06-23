package com.zods.largescreen.modules.dashboard.service;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.modules.dashboard.controller.dto.ChartDto;
import java.util.List;
/**
 * @desc  图表服务接口
 * @author jianglong
 * @date 2022-06-22
 **/
public interface ChartStrategyService {

    /**图表类型*/
    String type();

    /**
     * 针对每种图表类型做单独的数据转换解析
     * @param dto 图表dto
     * @param data 图表数集结果
     * @return object
     */
    Object transform(ChartDto dto, List<JSONObject> data);
}
