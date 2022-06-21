package com.zods.largescreen.modules.datasource.service;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.dataset.controller.dto.DataSetDto;
import com.zods.largescreen.modules.datasource.controller.dto.DataSourceDto;
import com.zods.largescreen.modules.datasource.controller.param.ConnectionParam;
import com.zods.largescreen.modules.datasource.controller.param.DataSourceParam;
import com.zods.largescreen.modules.datasource.dao.entity.DataSource;
import java.util.List;
/**
 * @desc DataSource 数据集服务接口
 * @author jianglong
 * @date 2022-06-16
 **/
public interface DataSourceService extends GaeaBaseService<DataSourceParam, DataSource> {

    /**获取所有数据源*/
    List<DataSource> queryAllDataSource();

    /**测试 连接*/
    Boolean testConnection(ConnectionParam connectionParam);

    /**执行sql*/
    List<JSONObject> execute(DataSourceDto dto);

    /**执行sql,统计数据total*/
    long total(DataSourceDto dataSourceDto, DataSetDto dto);
}
