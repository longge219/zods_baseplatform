package com.zods.largescreen.function.datasettransform.dao;
import com.zods.largescreen.function.datasettransform.entity.DataSetTransform;
import com.zods.plugins.zods.curd.mapper.GaeaBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
*  @description 数据集数据转换DAO
* @author jianglong
* @date 2022-06-14
**/
@Mapper
public interface DataSetTransformMapper extends GaeaBaseMapper<DataSetTransform> {

}
