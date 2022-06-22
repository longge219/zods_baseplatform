package com.zods.largescreen.modules.datasetparam.controller;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.datasetparam.controller.dto.DataSetParamDto;
import com.zods.largescreen.modules.datasetparam.controller.param.DataSetParamParam;
import com.zods.largescreen.modules.datasetparam.controller.param.DataSetParamValidationParam;
import com.zods.largescreen.modules.datasetparam.dao.entity.DataSetParam;
import com.zods.largescreen.modules.datasetparam.service.DataSetParamService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author jianglong
 * @version 1.0
 * @Description 数据集动态参数 controller
 * @createDate 2022-06-22
 */
@RestController
@Api(tags = "数据集动态参数管理")
@RequestMapping("/dataSetParam")
public class DataSetParamController extends GaeaBaseController<DataSetParamParam, DataSetParam, DataSetParamDto> {

    @Autowired
    private DataSetParamService dataSetParamService;

    @Override
    public GaeaBaseService<DataSetParamParam, DataSetParam> getService() {
        return dataSetParamService;
    }

    @Override
    public DataSetParam getEntity() {
        return new DataSetParam();
    }

    @Override
    public DataSetParamDto getDTO() {
        return new DataSetParamDto();
    }

    /**
     * 参数校验 js脚本
     */
    @PostMapping("/verification")
    public ResponseBean verification(@Validated @RequestBody DataSetParamValidationParam param) {
        DataSetParamDto dto = new DataSetParamDto();
        dto.setSampleItem(param.getSampleItem());
        dto.setValidationRules(param.getValidationRules());
        return responseSuccessWithData(dataSetParamService.verification(dto));
    }
}
