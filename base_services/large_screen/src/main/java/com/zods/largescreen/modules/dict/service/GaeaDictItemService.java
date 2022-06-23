package com.zods.largescreen.modules.dict.service;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.dict.controller.param.GaeaDictItemParam;
import com.zods.largescreen.modules.dict.dao.entity.GaeaDictItem;
import java.util.Map;
/**
 * @desc 数据字典项(GaeaDictItem)Service
 * @author jianglong
 * @date 2022-06-23
 **/
public interface GaeaDictItemService extends GaeaBaseService<GaeaDictItemParam, GaeaDictItem> {

    /**
     * 根据字典编码获取字典项
     * @param dictCode 字典编码
     * @return MAP<key,value> key 字典选项编码；value:字典选项值
     */
    Map<String,String> getItemMap(String dictCode);
}
