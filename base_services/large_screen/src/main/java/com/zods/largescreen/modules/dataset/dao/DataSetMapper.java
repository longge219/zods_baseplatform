package com.zods.largescreen.modules.dataset.dao;

import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.dataset.dao.entity.DataSet;
import org.apache.ibatis.annotations.Mapper;

/**
* DataSet Mapper
* @author Raod
* @date 2021-03-18 12:11:31.150755900
**/
@Mapper
public interface DataSetMapper extends GaeaBaseMapper<DataSet> {

}
