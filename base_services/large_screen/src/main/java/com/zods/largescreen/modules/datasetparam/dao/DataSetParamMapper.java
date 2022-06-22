package com.zods.largescreen.modules.datasetparam.dao;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.datasetparam.dao.entity.DataSetParam;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author jianglong
 * @version 1.0
 * @Description 数据集动态参数 Dao
 * @createDate 2022-06-22
 */
@Mapper
public interface DataSetParamMapper extends GaeaBaseMapper<DataSetParam> {

}
