package com.zods.largescreen.modules.layer.dao;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.layer.dao.entity.Layer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @desc DataSource 图层Dao
 * @author jianglong
 * @date 2022-06-24
 **/
@Mapper
public interface LayerMapper extends GaeaBaseMapper<Layer> {

}
