package com.zods.largescreen.modules.datasettransform.controller;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.datasettransform.controller.dto.DataSetTransformDto;
import com.zods.largescreen.modules.datasettransform.controller.param.DataSetTransformParam;
import com.zods.largescreen.modules.datasettransform.dao.entity.DataSetTransform;
import com.zods.largescreen.modules.datasettransform.service.DataSetTransformService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author jianglong
 * @version 1.0
 * @Description 数据集数据转换 controller
 * @createDate 2022-06-22
 */
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
