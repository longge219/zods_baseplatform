
package com.zods.largescreen.modules.datasettransform.service;

import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.datasettransform.controller.dto.DataSetTransformDto;
import com.zods.largescreen.modules.datasettransform.controller.param.DataSetTransformParam;
import com.zods.largescreen.modules.datasettransform.dao.entity.DataSetTransform;

import java.util.List;

/**
* @desc DataSetTransform 数据集数据转换服务接口
* @author Raod
* @date 2021-03-18 12:13:15.591309400
**/
public interface DataSetTransformService extends GaeaBaseService<DataSetTransformParam, DataSetTransform> {

    List<JSONObject> transform(List<DataSetTransformDto> dataSetTransformDtoList, List<JSONObject> data);

}
