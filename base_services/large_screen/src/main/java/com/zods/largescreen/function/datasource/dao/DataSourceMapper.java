package com.zods.largescreen.function.datasource.dao;
import com.zods.largescreen.function.datasource.entity.DataSource;
import com.zods.plugins.zods.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * @description 数据源实体类DAO层
 * @author jianglong
 * @date 2022-06-14
 **/
@Mapper
public interface DataSourceMapper extends GaeaBaseMapper<DataSource> {

}
