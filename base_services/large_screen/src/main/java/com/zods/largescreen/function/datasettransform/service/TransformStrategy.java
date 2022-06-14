package com.zods.largescreen.function.datasettransform.service;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.function.datasettransform.dto.DataSetTransformDto;
import java.util.List;
/**
 * @desc DataSetTransform 数据集数据转换
 * @author jianglong
 * @date 2022-06-14
 **/
public interface TransformStrategy {
    /**
     * 数据清洗转换 类型
     * @return
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
