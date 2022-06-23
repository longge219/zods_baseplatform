package com.zods.largescreen.modules.dict.service;
import com.zods.largescreen.common.bean.KeyValue;
import com.zods.largescreen.common.curd.service.GaeaBaseService;
import com.zods.largescreen.modules.dict.controller.param.GaeaDictParam;
import com.zods.largescreen.modules.dict.dao.entity.GaeaDict;
import com.zods.largescreen.modules.dict.dao.entity.GaeaDictItem;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * @desc 数据字典-service
 * @author jianglong
 * @date 2022-06-23
 **/
public interface GaeaDictService extends GaeaBaseService<GaeaDictParam, GaeaDict> {


    /**刷新全部缓存*/
    void refreshCache(List<String> dictCodes);


    /**
     * 获取指定字典code下拉
     * @param dictCode 字典编码
     * @param language 语言类型
     * @return
     */
    List<KeyValue> select(String dictCode, String language);

    /**
     * 获取所有字典项
     * @param dictCodes 数据字典编码集合
     */
    List<GaeaDictItem> findItems(List<String> dictCodes);
    /**
     * 获取所有 typecode
     * @param system
     * @param language 语言
     * @return
     */
    Collection<KeyValue> selectTypeCode(String system, String language);

    /**
     * 获取所有数据字典
     * @param language 语言
     * @return
     */
    Map<String, List<KeyValue>> all(String language);
}
