package com.zods.largescreen.function.datasettransform.controller;
import com.zods.largescreen.function.datasettransform.dto.DataSetTransformDto;
import com.zods.largescreen.function.datasettransform.entity.DataSetTransform;
import com.zods.largescreen.function.datasettransform.param.DataSetTransformParam;
import com.zods.largescreen.function.datasettransform.service.DataSetTransformService;
import com.zods.plugins.zods.curd.controller.GaeaBaseController;
import com.zods.plugins.zods.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @desc 数据集数据转换 controller
* @author Raod
* @date 2021-03-18 12:13:15.591309400
**/
@RestController
@Api(tags = "数据集数据转换管理")
@RequestMapping("/dataSetTransform")
public class DataSetTransformController extends GaeaBaseController<DataSetTransformParam, DataSetTransform, DataSetTransformDto> {

    @Autowired
    private DataSetTransformService dataSetTransformService;

    @Override
    public GaeaBaseService<DataSetTransformParam, DataSetTransform> getService() {
        return dataSetTransformService;
    }

    @Override
    public DataSetTransform getEntity() {
        return new DataSetTransform();
    }

    @Override
    public DataSetTransformDto getDTO() {
        return new DataSetTransformDto();
    }

}
