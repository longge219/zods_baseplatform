package com.zods.largescreen.modules.dashboard.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.modules.dashboard.controller.dto.ChartDto;
import com.zods.largescreen.modules.dashboard.service.ChartStrategyService;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * @desc 柱状体或者折线图
 * @author jianglong
 * @date 2022-06-22
 **/
@Component
public class BarChartServiceImpl implements ChartStrategyService {

    /**图表类型*/
    @Override
    public String type() {
        return "widget-barchart|widget-linechart";
    }

    /**
     * 针对每种图表类型做单独的数据转换解析
     * @param dto 图表dto
     * @param data 图表数集结果
     * @return object
     */
    @Override
    public Object transform(ChartDto dto, List<JSONObject> data) {
//        JSONObject json = new JSONObject();
//        List<Object> xAxis = new ArrayList<>();
//        List<Object> series = new ArrayList<>();
//        data.forEach(jsonObject -> {
//            jsonObject.forEach((s, o) -> {
//                if ("xAxis".equals(s)) {
//                    xAxis.add(o);
//                } else {
//                    series.add(o);
//                }
//            });
//        });
//
//        json.put("xAxis", xAxis);
//        JSONArray objects = new JSONArray();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("data", series);
//        objects.add(jsonObject);
//        json.put("series", objects);
//        return json.toJSONString();
        return data;
    }


/*    {
        "xAxis": [
                "哈哈",
                "洗洗",
                "来了",
                "问问",
                "天天"
    ],
        "series": [
            {
                "data": [
                    1,
                    2,
                    3,
                    4,
                    5
            ]
        }
    ]
    }*/
}
