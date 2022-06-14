package com.zods.plugins.zods.curd.service;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.zods.plugins.zods.annotation.*;
import com.zods.plugins.zods.bean.HashKeyValue;
import com.zods.plugins.zods.cache.CacheHelper;
import com.zods.plugins.zods.constant.BaseOperationEnum;
import com.zods.plugins.zods.curd.entity.BaseEntity;
import com.zods.plugins.zods.curd.entity.GaeaBaseEntity;
import com.zods.plugins.zods.curd.mapper.GaeaBaseMapper;
import com.zods.plugins.zods.curd.params.PageParam;
import com.zods.plugins.zods.exception.BusinessException;
import com.zods.plugins.zods.exception.BusinessExceptionBuilder;
import com.zods.plugins.zods.utils.ApplicationContextUtils;
import com.zods.plugins.zods.utils.GaeaAssert;
import com.zods.plugins.zods.utils.GaeaUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.springframework.transaction.annotation.Transactional;
/**
 * @description 基础service层接口
 * @author jianglong
 * @date 2022-06-14
 **/
public interface GaeaBaseService<P extends PageParam, T extends BaseEntity> {

    GaeaBaseMapper<T> getMapper();

    default T wrapperEntity(T entity) {
        return entity;
    }

    default T selectOne(Long id) {
        T t = this.getMapper().selectById(id);
        GaeaAssert.notNull(t, "500-0001", new Object[0]);
        return this.wrapperEntity(t);
    }

    default T selectOne(Wrapper<T> wrapper) {
        return this.getMapper().selectOne(wrapper);
    }

    default T selectOne(String column, Object value) {
        List<T> list = this.list(column, value);
        return list.isEmpty() ? null : this.wrapperEntity(list.get(0));
    }

    default IPage<T> page(P pageParam) {
        return this.page(pageParam, (Wrapper)null);
    }

    default OrderItem build(String column, String order) {
        OrderItem item = new OrderItem();
        item.setColumn(column);
        if ("ASC".equalsIgnoreCase(order)) {
            item.setAsc(true);
        } else {
            item.setAsc(false);
        }

        return item;
    }

    default IPage<T> page(P pageParam, Wrapper<T> wrapper) {
        Page<T> page = new Page();
        page.setCurrent((long)pageParam.getPageNumber());
        page.setSize((long)pageParam.getPageSize());
        String sort = pageParam.getSort();
        String order = pageParam.getOrder();
        String[] sortSplit = new String[0];
        String[] orderSplit = new String[0];
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            if (sort.contains(",") && order.contains(",")) {
                sortSplit = sort.split(",");
                orderSplit = order.split(",");
                if (orderSplit.length == sortSplit.length) {
                    OrderItem[] orderItems = new OrderItem[orderSplit.length];

                    for(int i = 0; i < sortSplit.length; ++i) {
                        orderItems[i] = this.build(GaeaUtils.camelToUnderline(sortSplit[i]), orderSplit[i]);
                    }

                    page.addOrder(orderItems);
                } else {
                    String order0 = orderSplit[0];
                    OrderItem[] orderItems = new OrderItem[orderSplit.length];

                    for(int i = 0; i < sortSplit.length; ++i) {
                        orderItems[i] = this.build(GaeaUtils.camelToUnderline(sortSplit[i]), order0);
                    }

                    page.addOrder(orderItems);
                }
            }

            if (sort.contains(",") && !order.contains(",")) {
                List<String> orderList = (List)Arrays.stream(sort.split(",")).map(GaeaUtils::camelToUnderline).collect(Collectors.toList());
                String[] orderColumns = (String[])orderList.toArray(new String[0]);
                if ("ASC".equalsIgnoreCase(order)) {
                    page.addOrder(OrderItem.ascs(orderColumns));
                } else {
                    page.addOrder(OrderItem.descs(orderColumns));
                }
            }

            if (!sort.contains(",") && !order.contains(",")) {
                page.addOrder(new OrderItem[]{this.build(GaeaUtils.camelToUnderline(sort), order)});
            }
        }

        if (wrapper != null) {
            return this.resultHandler(this.getMapper().selectPage(page, wrapper));
        } else {
            Wrapper<T> pageWrapper = this.extensionWrapper(pageParam, this.getWrapper(pageParam));
            this.handlerPageWrapper(pageWrapper);
            return this.resultHandler(this.getMapper().selectPage(page, pageWrapper));
        }
    }

    default Wrapper<T> handlerPageWrapper(Wrapper<T> pageWrapper) {
        return pageWrapper;
    }

    default IPage<T> resultHandler(IPage<T> iPage) {
        return iPage;
    }

    default Wrapper<T> extensionWrapper(P param, QueryWrapper<T> wrapper) {
        return wrapper;
    }

    default QueryWrapper<T> getWrapper(P param) {
        QueryWrapper<T> queryWrapper = new QueryWrapper();
        Field[] fields = param.getClass().getDeclaredFields();
        Arrays.stream(fields).filter((field) -> {
            if (field.isAnnotationPresent(Query.class)) {
                Query query = (Query)field.getAnnotation(Query.class);
                return query.where();
            } else {
                return true;
            }
        }).forEach((field) -> {
            try {
                field.setAccessible(true);
                String column;
                if (field.isAnnotationPresent(Query.class) && StringUtils.isNotBlank(((Query)field.getAnnotation(Query.class)).column())) {
                    column = ((Query)field.getAnnotation(Query.class)).column();
                } else {
                    column = GaeaUtils.camelToUnderline(field.getName());
                }

                boolean flag;
                if (field.get(param) instanceof String) {
                    flag = StringUtils.isNoneBlank(new CharSequence[]{(String)field.get(param)});
                } else {
                    flag = field.get(param) != null;
                }

                if (!flag) {
                    return;
                }

                if (field.isAnnotationPresent(Query.class)) {
                    String[] split;
                    switch(((Query)field.getAnnotation(Query.class)).value()) {
                        case LIKE:
                            String likeValue = String.valueOf(field.get(param));
                            if (likeValue.contains("%")) {
                                likeValue = likeValue.replace("%", "\\%");
                            }

                            if (likeValue.contains("_")) {
                                likeValue = likeValue.replace("_", "\\_");
                            }

                            queryWrapper.like(column, likeValue);
                            break;
                        case IN:
                            Object value = field.get(param);
                            if (value instanceof List) {
                                queryWrapper.in(column, (List)value);
                            } else if (value instanceof String) {
                                split = ((String)value).split(",");
                                List<String> list = Arrays.asList(split);
                                queryWrapper.in(column, list);
                            }
                            break;
                        case GT:
                            queryWrapper.gt(column, field.get(param));
                            break;
                        case GE:
                            queryWrapper.ge(column, field.get(param));
                            break;
                        case LT:
                            queryWrapper.lt(column, field.get(param));
                            break;
                        case LE:
                            queryWrapper.le(column, field.get(param));
                            break;
                        case BWT:
                            split = field.get(param).toString().split(",");
                            if (split.length == 2) {
                                queryWrapper.between(column, split[0], split[1]);
                            } else if (split.length == 1) {
                                queryWrapper.ge(column, split[0]);
                            }
                            break;
                        default:
                            queryWrapper.eq(column, field.get(param));
                    }
                } else {
                    queryWrapper.eq(column, field.get(param));
                }
            } catch (IllegalAccessException var9) {
                var9.printStackTrace();
            }

        });
        return queryWrapper;
    }

    default void processBeforeOperation(T entity, BaseOperationEnum operationEnum) throws BusinessException {
    }

    default void processAfterOperation(T entity, BaseOperationEnum operationEnum) throws BusinessException {
    }

    default String getColumn(SFunction<T, ?> function) {
        SerializedLambda lambda = LambdaUtils.resolve(function);
        String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        Class implClass = lambda.getImplClass();

        try {
            Field field = implClass.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(TableField.class)) {
                fieldName = ((TableField)field.getAnnotation(TableField.class)).value();
                return fieldName;
            }
        } catch (NoSuchFieldException var6) {
        }

        return GaeaUtils.camelToUnderline(fieldName);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Integer insert(T entity) throws BusinessException {
        this.processBeforeOperation(entity, BaseOperationEnum.INSERT);
        this.checkUniqueField(entity, false);
        Integer result = this.getMapper().insert(entity);
        if (result != null && result >= 1) {
            this.refreshCacheFields(entity, BaseOperationEnum.INSERT);
            this.processAfterOperation(entity, BaseOperationEnum.INSERT);
            return result;
        } else {
            throw BusinessExceptionBuilder.build("Insert.failure");
        }
    }

    default void refreshCacheFields(T entity, BaseOperationEnum operationEnum) {
        Class<? extends BaseEntity> entityClass = entity.getClass();
        Field[] declaredFields = entityClass.getDeclaredFields();
        Map<String, HashKeyValue> cacheMap = new HashMap();
        Field[] var6 = declaredFields;
        int var7 = declaredFields.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Field field = var6[var8];
            field.setAccessible(true);

            Object value;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException var14) {
                continue;
            }

            if (value != null) {
                String key;
                HashKeyValue hashKeyValue;
                if (field.isAnnotationPresent(HashKey.class)) {
                    HashKey hashKey = (HashKey)field.getAnnotation(HashKey.class);
                    key = hashKey.key();
                    if (cacheMap.containsKey(key)) {
                        hashKeyValue = (HashKeyValue)cacheMap.get(key);
                        hashKeyValue.setKey(String.valueOf(value));
                        hashKeyValue.setHashKey(hashKey);
                    } else {
                        hashKeyValue = new HashKeyValue();
                        hashKeyValue.setKey(String.valueOf(value));
                        hashKeyValue.setHashKey(hashKey);
                        cacheMap.put(key, hashKeyValue);
                    }
                }

                if (field.isAnnotationPresent(HashValue.class)) {
                    HashValue hashValue = (HashValue)field.getAnnotation(HashValue.class);
                    key = hashValue.key();
                    if (cacheMap.containsKey(key)) {
                        hashKeyValue = (HashKeyValue)cacheMap.get(key);
                        hashKeyValue.setValue(String.valueOf(value));
                    } else {
                        hashKeyValue = new HashKeyValue();
                        hashKeyValue.setValue(String.valueOf(value));
                        cacheMap.put(key, hashKeyValue);
                    }
                }
            }
        }

        CacheHelper cacheHelper = (CacheHelper) ApplicationContextUtils.getBean(CacheHelper.class);
        if (BaseOperationEnum.DELETE != operationEnum && BaseOperationEnum.DELETE_BATCH != operationEnum) {
            cacheMap.entrySet().stream().filter((entry) -> {
                return ((HashKeyValue)entry.getValue()).getKey() != null && ((HashKeyValue)entry.getValue()).getValue() != null;
            }).forEach((entry) -> {
                cacheHelper.hashSet(this.formatKey((String)entry.getKey(), ((HashKeyValue)entry.getValue()).getHashKey().replace(), entity), ((HashKeyValue)entry.getValue()).getKey(), ((HashKeyValue)entry.getValue()).getValue());
            });
        } else {
            cacheMap.entrySet().stream().filter((entry) -> {
                return ((HashKeyValue)entry.getValue()).getKey() != null && ((HashKeyValue)entry.getValue()).getValue() != null;
            }).forEach((entry) -> {
                cacheHelper.hashDel(this.formatKey((String)entry.getKey(), ((HashKeyValue)entry.getValue()).getHashKey().replace(), entity), ((HashKeyValue)entry.getValue()).getKey());
            });
        }

    }

    default String formatKey(String key, String[] replaceArray, T entity) {
        if (key.contains("${")) {
            Map<String, Object> keyPatternMap = new HashMap(2);
            String[] var5 = replaceArray;
            int var6 = replaceArray.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String fieldName = var5[var7];

                try {
                    Field declaredField = entity.getClass().getDeclaredField(fieldName);
                    declaredField.setAccessible(true);
                    Object fieldValue = declaredField.get(entity);
                    keyPatternMap.put(fieldName, fieldValue);
                } catch (Exception var11) {
                }
            }

            key = GaeaUtils.replaceFormatString(key, keyPatternMap);
            if (key.contains("${")) {
                return null;
            }
        }

        return key;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Integer insertBatch(List<T> entities) throws BusinessException {
        Integer result = this.getMapper().insertBatch(entities);
        entities.stream().forEach((entity) -> {
            this.refreshCacheFields(entity, BaseOperationEnum.INSERT);
        });
        if (result != null && result >= 1) {
            return result;
        } else {
            throw BusinessExceptionBuilder.build("Insert.failure");
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Integer update(T entity) throws BusinessException {
        this.processBeforeOperation(entity, BaseOperationEnum.UPDATE);
        this.checkUniqueField(entity, true);
        if (entity instanceof GaeaBaseEntity) {
            GaeaBaseEntity gaeaBaseEntity = (GaeaBaseEntity)entity;
            T dbEntity = this.getById(gaeaBaseEntity.getId());
            this.refreshCacheFields(dbEntity, BaseOperationEnum.DELETE);
        }

        Integer result = this.getMapper().updateById(entity);
        if (result != null && result >= 1) {
            this.refreshCacheFields(entity, BaseOperationEnum.UPDATE);
            this.processAfterOperation(entity, BaseOperationEnum.UPDATE);
            return result;
        } else {
            throw BusinessExceptionBuilder.build("Update.failure");
        }
    }

    default Integer updateFieldsById(Map<String, Object> map, Long id) {
        return this.getMapper().updateFieldsById(map, id);
    }

    default Integer updateBatchFieldsById(Map<String, Object> map, List<Long> ids) {
        return this.getMapper().updateFieldsBatchById(map, ids);
    }

    default Integer updateBatchFields(Map<String, Object> map, List<T> list) {
        return this.getMapper().updateFieldsBatch(map, list);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Integer deleteById(Serializable id) {
        T t = this.getById(id);
        if (t == null) {
            throw BusinessExceptionBuilder.build("500-0001");
        } else {
            this.processBeforeOperation(t, BaseOperationEnum.DELETE);
            Integer result = this.getMapper().deleteById(id);
            if (result != null && result >= 1) {
                this.refreshCacheFields(t, BaseOperationEnum.DELETE);
                this.processAfterOperation(t, BaseOperationEnum.DELETE);
                return result;
            } else {
                throw BusinessExceptionBuilder.build("Delete.failure");
            }
        }
    }

    default void processBatchBeforeOperation(List<T> entities, BaseOperationEnum operationEnum) throws BusinessException {
    }

    default void processBatchAfterOperation(List<T> entities, BaseOperationEnum operationEnum) throws BusinessException {
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean deleteByIds(Collection<? extends Serializable> idList) {
        List<T> list = this.getMapper().selectBatchIds(idList);
        this.processBatchBeforeOperation(list, BaseOperationEnum.DELETE_BATCH);
        boolean result = SqlHelper.retBool(this.getMapper().deleteBatchIds(idList));
        if (result) {
            list.stream().forEach((entity) -> {
                this.refreshCacheFields(entity, BaseOperationEnum.DELETE_BATCH);
            });
            this.processBatchAfterOperation(list, BaseOperationEnum.DELETE_BATCH);
        }

        return result;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default void delete(LambdaQueryWrapper<T> lambdaQueryWrapper) {
        this.getMapper().delete(lambdaQueryWrapper);
    }

    default void checkUniqueField(T entity, boolean isUpdate) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Field[] superFields = entity.getClass().getSuperclass().getDeclaredFields();
        Field[] allFields = (Field[])ArrayUtils.addAll(fields, superFields);
        Optional<Field> idFiledOptional = Arrays.stream(allFields).filter((fieldx) -> {
            return fieldx.isAnnotationPresent(TableId.class);
        }).findFirst();
        if (idFiledOptional.isPresent()) {
            Field idField = (Field)idFiledOptional.get();
            idField.setAccessible(true);
            Field[] var8 = fields;
            int var9 = fields.length;

            int var10;
            for(var10 = 0; var10 < var9; ++var10) {
                Field field = var8[var10];
                if (field.isAnnotationPresent(Unique.class)) {
                    Unique unique = (Unique)field.getDeclaredAnnotation(Unique.class);
                    QueryWrapper wrapper = Wrappers.query();

                    Integer integer;
                    try {
                        Object value = this.getFieldValue(entity, field);
                        String column;
                        if (StringUtils.isBlank(unique.column())) {
                            column = GaeaUtils.camelToUnderline(field.getName());
                        } else {
                            column = unique.column();
                        }

                        wrapper.eq(column, value);
                        if (isUpdate) {
                            wrapper.ne(((TableId)idField.getAnnotation(TableId.class)).value(), idField.get(entity));
                        }

                        integer = this.getMapper().selectCount(wrapper);
                    } catch (Exception var24) {
                        continue;
                    }

                    if (integer > 0) {
                        throw BusinessExceptionBuilder.build(unique.code(), new Object[]{field.getName()});
                    }
                }
            }

            Map<String, QueryWrapper<T>> unionUniqueMap = new HashMap();
            Field[] var26 = fields;
            var10 = fields.length;

            for(int var29 = 0; var29 < var10; ++var29) {
                Field field = var26[var29];
                if (field.isAnnotationPresent(UnionUnique.class)) {
                    try {
                        UnionUnique[] unionUniques = (UnionUnique[])field.getDeclaredAnnotationsByType(UnionUnique.class);
                        UnionUnique[] var35 = unionUniques;
                        int var37 = unionUniques.length;

                        for(int var39 = 0; var39 < var37; ++var39) {
                            UnionUnique unionUnique = var35[var39];
                            String group = unionUnique.group();
                            Object value = this.getFieldValue(entity, field);
                            String column;
                            if (StringUtils.isBlank(unionUnique.column())) {
                                column = GaeaUtils.camelToUnderline(field.getName());
                            } else {
                                column = unionUnique.column();
                            }

                            QueryWrapper unionWrapper;
                            if (unionUniqueMap.containsKey(group)) {
                                unionWrapper = (QueryWrapper)unionUniqueMap.get(group);
                                unionWrapper.eq(column, value);
                            } else {
                                unionWrapper = Wrappers.query();
                                unionWrapper.eq(column, value);
                                unionUniqueMap.put(group, unionWrapper);
                            }
                        }
                    } catch (Exception var23) {
                    }
                }
            }

            Set<Entry<String, QueryWrapper<T>>> entries = unionUniqueMap.entrySet();
            Iterator var28 = entries.iterator();

            while(true) {
                Entry entry;
                Integer result;
                do {
                    if (!var28.hasNext()) {
                        return;
                    }

                    entry = (Entry)var28.next();
                    QueryWrapper<T> queryWrapper = (QueryWrapper)entry.getValue();
                    if (isUpdate) {
                        try {
                            queryWrapper.ne(((TableId)idField.getAnnotation(TableId.class)).value(), idField.get(entity));
                        } catch (Exception var22) {
                            return;
                        }
                    }

                    result = this.getMapper().selectCount(queryWrapper);
                } while(result <= 0);

                String group = (String)entry.getKey();
                Class<? extends BaseEntity> aClass = entity.getClass();
                UnionUniqueCode[] unionUniqueCodes = (UnionUniqueCode[])aClass.getAnnotationsByType(UnionUniqueCode.class);
                UnionUniqueCode[] var40 = unionUniqueCodes;
                int var42 = unionUniqueCodes.length;

                for(int var43 = 0; var43 < var42; ++var43) {
                    UnionUniqueCode unionUniqueCode = var40[var43];
                    if (StringUtils.equals(unionUniqueCode.group(), group)) {
                        throw BusinessExceptionBuilder.build(unionUniqueCode.code());
                    }
                }
            }
        }
    }

    default Object getFieldValue(T entity, Field field) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity.getClass());
        Method readMethod = propertyDescriptor.getReadMethod();
        return readMethod.invoke(entity);
    }

    default List<T> list(String column, Object value) {
        QueryWrapper<T> queryWrapper = new QueryWrapper();
        queryWrapper.eq(column, value);
        return this.getMapper().selectList(queryWrapper);
    }

    default Integer count(Wrapper<T> wrapper) {
        return this.getMapper().selectCount(wrapper);
    }

    default List<T> list(Wrapper<T> wrapper) {
        return this.getMapper().selectList(wrapper);
    }

    default T getById(Serializable id) {
        return this.getMapper().selectById(id);
    }

    default List<T> findAll() {
        return this.getMapper().selectList(Wrappers.emptyWrapper());
    }

    default Integer updateColumn(Long id, String column, Object value) {
        Map<String, Object> params = new HashMap(1);
        params.put(column, value);
        return this.getMapper().updateFieldsById(params, id);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default Integer updateFieldsBatch(Map<String, Object> map, List<T> list) {
        return this.getMapper().updateFieldsBatch(map, list);
    }
}
