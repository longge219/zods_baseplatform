package com.zods.largescreen.function.dataset.dao;
import com.zods.largescreen.function.dataset.entity.DataSet;
import com.zods.plugins.zods.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* DataSet 数据集DAO
* @author jianglong
* @date 2022-06-14
**/
@Mapper
public interface DataSetMapper extends GaeaBaseMapper<DataSet> {

}
