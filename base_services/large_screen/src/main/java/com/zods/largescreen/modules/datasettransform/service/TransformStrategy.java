package com.zods.largescreen.modules.datasettransform.service;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.modules.datasettransform.controller.dto.DataSetTransformDto;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-22
 */
public interface TransformStrategy {
    /**
     * 数据清洗转换 类型
     */
    String type();
    /***
     * 清洗转换算法接口
     * @param def
     * @param data
     * @return
     */
    List<JSONObject> transform(DataSetTransformDto def, List<JSONObject> data);
}
