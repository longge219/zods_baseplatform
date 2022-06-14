package com.zods.largescreen.function.datasource.controller;
import com.zods.largescreen.function.datasource.dto.DataSourceDto;
import com.zods.largescreen.function.datasource.entity.DataSource;
import com.zods.largescreen.function.datasource.param.ConnectionParam;
import com.zods.largescreen.function.datasource.param.DataSourceParam;
import com.zods.largescreen.function.datasource.service.DataSourceService;
import com.zods.plugins.zods.annotation.Permission;
import com.zods.plugins.zods.bean.ResponseBean;
import com.zods.plugins.zods.curd.controller.GaeaBaseController;
import com.zods.plugins.zods.curd.service.GaeaBaseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * @description 数据源 controller
 * @author jianglong
 * @date 2022-06-14
 **/
@RestController
@Api(tags = "数据源管理")
@Permission(code = "datasourceManage", name = "数据源管理")
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

    /**测试 连接*/
    @Permission( code = "query", name = "测试数据源")
    @PostMapping("/testConnection")
    public ResponseBean testConnection(@Validated @RequestBody ConnectionParam connectionParam) {
        return responseSuccessWithData(dataSourceService.testConnection(connectionParam));
    }

}
