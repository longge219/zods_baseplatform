package com.zods.largescreen.modules.datasource.dao;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.datasource.dao.entity.DataSource;
import org.apache.ibatis.annotations.Mapper;
/**
 * @desc DataSource 数据集Dao
 * @author jianglong
 * @date 2022-06-16
 **/
@Mapper
public interface DataSourceMapper extends GaeaBaseMapper<DataSource> {

}
