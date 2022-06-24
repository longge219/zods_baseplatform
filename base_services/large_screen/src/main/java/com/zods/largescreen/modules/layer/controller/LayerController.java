package com.zods.largescreen.modules.layer.controller;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.layer.controller.dto.LayerDto;
import com.zods.largescreen.modules.layer.controller.param.LayerParam;
import com.zods.largescreen.modules.layer.dao.entity.Layer;
import com.zods.largescreen.modules.layer.service.LayerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
* @desc 图层 controller
* @author jianglong
* @date 2022-06-16
**/
@RestController
@Api(tags = "图层管理")
@RequestMapping("/layer")
public class LayerController extends GaeaBaseController<LayerParam, Layer, LayerDto> {

    @Autowired
    private LayerService layerService;

    @Override
    public GaeaBaseService<LayerParam, Layer> getService() {
        return layerService;
    }

    @Override
    public Layer getEntity() {
        return new Layer();
    }

    @Override
    public LayerDto getDTO() {
        return new LayerDto();
    }

}
