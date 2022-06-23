package com.zods.largescreen.modules.dict.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zods.largescreen.common.cache.CacheHelper;
import com.zods.largescreen.common.constant.BaseOperationEnum;
import com.zods.largescreen.common.constant.GaeaConstant;
import com.zods.largescreen.common.constant.GaeaKeyConstant;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.common.exception.BusinessException;
import com.zods.largescreen.modules.dict.dao.GaeaDictItemMapper;
import com.zods.largescreen.modules.dict.dao.entity.GaeaDictItem;
import com.zods.largescreen.modules.dict.service.GaeaDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @desc 数据字典项(GaeaDictItem)ServiceImpl
 * @author jianglong
 * @date 2022-06-23
 **/
@Service
public class GaeaDictItemServiceImpl implements GaeaDictItemService {
    @Autowired
    private GaeaDictItemMapper gaeaDictItemMapper;

    @Autowired
    private CacheHelper redisCacheHelper;

    @Override
    public GaeaBaseMapper<GaeaDictItem> getMapper() {
        return  gaeaDictItemMapper;
    }


    @Override
    public void processAfterOperation(GaeaDictItem entity, BaseOperationEnum operationEnum) throws BusinessException {
        String dictCode = entity.getDictCode();
        String locale = entity.getLocale();

        String key = GaeaKeyConstant.DICT_PREFIX + locale + GaeaConstant.REDIS_SPLIT + dictCode;
        switch (operationEnum) {
            case INSERT:
            case UPDATE:
                redisCacheHelper.hashSet(key, entity.getItemValue(), entity.getItemName());
                break;
            case DELETE:
                redisCacheHelper.hashDel(key, entity.getItemValue());
                default:
        }
    }

    @Override
    public void processBatchAfterOperation(List<GaeaDictItem> entities, BaseOperationEnum operationEnum) throws BusinessException {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        Map<String, Map<String, String>> dictItemMap = entities.stream()
                .collect(Collectors.groupingBy(item -> item.getLocale() + GaeaConstant.REDIS_SPLIT +item.getDictCode(),
                                Collectors.toMap(GaeaDictItem::getItemValue, GaeaDictItem::getItemName,(v1,v2)-> v2)));
        switch (operationEnum) {
            case DELETE_BATCH:
                //遍历并保持到Redis中
                dictItemMap.entrySet().stream().forEach(entry -> {
                    String key = GaeaKeyConstant.DICT_PREFIX  + entry.getKey();
                    Set<String> hashKeys = entry.getValue().keySet();
                    redisCacheHelper.hashBatchDel(key, hashKeys);
                });
                break;
                default:
        }
    }

    /**
     * 根据字典编码获取字典项
     * @param dictCode 字典编码
     * @return MAP<key,value> key 字典选项编码；value:字典选项值
     */
    @Override
    public Map<String, String> getItemMap(String dictCode) {
        Locale locale = LocaleContextHolder.getLocale();
        LambdaQueryWrapper<GaeaDictItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GaeaDictItem::getDictCode, dictCode);
        wrapper.eq(GaeaDictItem::getLocale, locale.getLanguage());
        List<GaeaDictItem> list = list(wrapper);
        Map<String, String> data = list.stream().collect(Collectors.toMap(GaeaDictItem::getItemValue, GaeaDictItem::getItemName, (v1, v2) -> v2));
        return data;
    }

}
