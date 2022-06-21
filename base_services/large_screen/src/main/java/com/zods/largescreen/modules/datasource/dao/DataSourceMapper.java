package com.zods.largescreen.modules.datasource.dao;

import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.datasource.dao.entity.DataSource;
import org.apache.ibatis.annotations.Mapper;

/**
* DataSource Mapper
* @author Raod
* @date 2021-03-18 12:09:57.728203200
**/
@Mapper
public interface DataSourceMapper extends GaeaBaseMapper<DataSource> {

}
