package com.zods.largescreen.modules.dict.dao;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.dict.dao.entity.GaeaDict;
import org.apache.ibatis.annotations.Mapper;
/**
 * @desc 数据字典(GaeaDict)Dao
 * @author jianglong
 * @date 2022-06-23
 **/
@Mapper
public interface GaeaDictMapper extends GaeaBaseMapper<GaeaDict> {


}
