package com.zods.largescreen.modules.datasource.controller;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.curd.controller.GaeaBaseController;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.datasource.controller.dto.DataSourceDto;
import com.zods.largescreen.modules.datasource.controller.param.ConnectionParam;
import com.zods.largescreen.modules.datasource.controller.param.DataSourceParam;
import com.zods.largescreen.modules.datasource.dao.entity.DataSource;
import com.zods.largescreen.modules.datasource.service.DataSourceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
* @desc 数据源 controller
* @author jianglong
* @date 2022-06-16
**/
@RestController
@Api(tags = "数据源管理")
@RequestMapping("/dataSource")
public class DataSourceController extends GaeaBaseController<DataSourceParam, DataSource, DataSourceDto> {

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public GaeaBaseService<DataSourceParam, DataSource> getService() {
        return dataSourceService;
    }

    @Override
    public DataSource getEntity() {
        return new DataSource();
    }

    @Override
    public DataSourceDto getDTO() {
        return new DataSourceDto();
    }

    /**获取所有数据源*/
    @GetMapping("/queryAllDataSource")
    public ResponseBean queryAllDataSource() {
        return responseSuccessWithData(dataSourceService.queryAllDataSource());
    }

    /**测试连接*/
    @PostMapping("/testConnection")
    public ResponseBean testConnection(@Validated @RequestBody ConnectionParam connectionParam) {
        return responseSuccessWithData(dataSourceService.testConnection(connectionParam));
    }

}
