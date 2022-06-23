package com.zods.largescreen.modules.dashboard.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.modules.dashboard.controller.dto.ChartDto;
import com.zods.largescreen.modules.dashboard.service.ChartStrategyService;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * @desc 折柱图
 * @author jianglong
 * @date 2022-06-22
 **/
@Component
public class BarLineChartServiceImpl implements ChartStrategyService {

    /**图表类型*/
    @Override
    public String type() {
        return "widget-barlinechart";
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
//        Map<String, List<Object>> series = new HashMap<>();
//        data.forEach(jsonObject -> {
//            jsonObject.forEach((s, o) -> {
//                if ("xAxis".equals(s)) {
//                    xAxis.add(o);
//                } else {
//                    List<Object> objects;
//                    if (series.containsKey(s)) {
//                        objects = series.get(s);
//                    } else {
//                        objects = new ArrayList<>();
//
//                    }
//                    objects.add(o);
//                    series.put(s, objects);
//
//                }
//            });
//        });
//
//        json.put("xAxis", xAxis);
//        List<JSONObject> result = new ArrayList<>();
//        series.forEach((s, objects) -> {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("name", s);
//            if (s.endsWith("bar")) {
//                jsonObject.put("type", "bar");
//            } else  {
//                jsonObject.put("type", "line");
//            }
//            jsonObject.put("data", objects);
//            result.add(jsonObject);
//        });
//        json.put("series", result);
//        return json.toJSONString();
        return data;
    }


    /*{
        "xAxis": [
                "1月",
                "2月",
                "3月"
        ],
        "series": [
            {
                "name": "指标1", //暂时用不上
                "type": "bar",   //需要处理
                "data": [
                    2,
                    49,
                    2
                ]
            },
            {
                 "name": "指标2",
                "type": "line",
                "yAxisIndex": 1,
                "data": [
                      2,
                     32,
                      4
            ]
        }
    ]
    }*/

}
