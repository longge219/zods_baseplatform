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
 * (GaeaDict)Service
 *
 * @author lr
 * @since 2021-02-23 10:01:02
 */
public interface GaeaDictService extends GaeaBaseService<GaeaDictParam, GaeaDict> {

    /**
     * 刷新全部缓存
     * @param dictCodes
     */
    void refreshCache(List<String> dictCodes);


    /**
     * 获取指定字典code下拉
     * @param dictCode
     * @param language
     * @return
     */
    List<KeyValue> select(String dictCode, String language);

    /**
     * 获取所有字典项
     * @return
     */
    List<GaeaDictItem> findItems(List<String> dictCodes);
    /**
     * 获取所有 typecode
     * @param system
     * @param language
     * @return
     */
    Collection<KeyValue> selectTypeCode(String system, String language);


    /**
     * 获取所有数据字典
     * @param language
     * @return
     */
    Map<String, List<KeyValue>> all(String language);
}
