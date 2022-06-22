package com.zods.largescreen.modules.dataset.dao;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.dataset.dao.entity.DataSet;
import org.apache.ibatis.annotations.Mapper;
/**
 * @desc 数据集 dao
 * @author jianglong
 * @date 2022-06-22
 **/
@Mapper
public interface DataSetMapper extends GaeaBaseMapper<DataSet> {

}
