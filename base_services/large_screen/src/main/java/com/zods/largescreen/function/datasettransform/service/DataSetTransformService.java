package com.zods.largescreen.function.datasettransform.service;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.function.datasettransform.dto.DataSetTransformDto;
import com.zods.largescreen.function.datasettransform.entity.DataSetTransform;
import com.zods.largescreen.function.datasettransform.param.DataSetTransformParam;
import com.zods.plugins.zods.curd.service.GaeaBaseService;
import java.util.List;
/**
* @desc DataSetTransform 数据集数据转换服务接口
* @author jianglong
* @date 2022-06-14
**/
public interface DataSetTransformService extends GaeaBaseService<DataSetTransformParam, DataSetTransform> {

    List<JSONObject> transform(List<DataSetTransformDto> dataSetTransformDtoList, List<JSONObject> data);

}
