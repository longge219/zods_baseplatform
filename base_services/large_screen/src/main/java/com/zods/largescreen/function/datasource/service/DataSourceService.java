package com.zods.largescreen.function.datasource.service;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import com.zods.largescreen.function.dataset.dto.DataSetDto;
import com.zods.largescreen.function.datasource.dto.DataSourceDto;
import com.zods.largescreen.function.datasource.entity.DataSource;
import com.zods.largescreen.function.datasource.param.ConnectionParam;
import com.zods.largescreen.function.datasource.param.DataSourceParam;
import  com.zods.plugins.zods.curd.service.GaeaBaseService;
/**
 * @description 数据集服务service层
 * @author jianglong
 * @date 2022-06-14
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
