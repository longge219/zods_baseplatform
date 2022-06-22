package com.zods.largescreen.modules.datasettransform.service;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.datasettransform.controller.dto.DataSetTransformDto;
import com.zods.largescreen.modules.datasettransform.controller.param.DataSetTransformParam;
import com.zods.largescreen.modules.datasettransform.dao.entity.DataSetTransform;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description DataSetTransform 数据集数据转换服务接口
 * @createDate 2022-06-22
 */
public interface DataSetTransformService extends GaeaBaseService<DataSetTransformParam, DataSetTransform> {

    /**数据转换*/
    List<JSONObject> transform(List<DataSetTransformDto> dataSetTransformDtoList, List<JSONObject> data);

}
