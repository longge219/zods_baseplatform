package com.zods.largescreen.common.curd.service;
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
import com.zods.largescreen.common.annotation.*;
import com.zods.largescreen.common.bean.HashKeyValue;
import com.zods.largescreen.common.cache.CacheHelper;
import com.zods.largescreen.common.constant.BaseOperationEnum;
import com.zods.largescreen.common.curd.entity.BaseEntity;
import com.zods.largescreen.common.curd.entity.GaeaBaseEntity;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.common.curd.params.PageParam;
import com.zods.largescreen.common.exception.BusinessException;
import com.zods.largescreen.common.exception.BusinessExceptionBuilder;
import com.zods.largescreen.common.utils.ApplicationContextUtils;
import com.zods.largescreen.common.utils.GaeaAssert;
import com.zods.largescreen.common.utils.GaeaUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.springframework.transaction.annotation.Transactional;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
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

    /**根据ID查找对象*/
    default T selectOne(Long id) {
        T t = this.getMapper().selectById(id);
        GaeaAssert.notNull(t, "500-0001", new Object[0]);
        return this.wrapperEntity(t);
    }

    /**通过查询条件查询对象*/
    default T selectOne(Wrapper<T> wrapper) {
        return this.getMapper().selectOne(wrapper);
    }

    /**根据属性名和属性值查询集合返回集合的第一条数据
     * column 属性名称
     * value 属性值
     * */
    default T selectOne(String column, Object value) {
        List<T> list = this.list(column, value);
        return list.isEmpty() ? null : this.wrapperEntity(list.get(0));
    }

    /**根据分页查询条件分页查询*/
    default IPage<T> page(P pageParam) {
        return this.page(pageParam, (Wrapper)null);
    }

    /**根据分页查询条件分页查询
     * pageParam 分页查询条件
     * wrapper 查询条件
     * */
    default IPage<T> page(P pageParam, Wrapper<T> wrapper) {
        Page<T> page = new Page();
        page.setCurrent((long)pageParam.getPageNumber());
        page.setSize((long)pageParam.getPageSize());
        String sort = pageParam.getSort();
        String order = pageParam.getOrder();
        String[] sortSplit = new String[0];
        String[] orderSplit = new String[0];
        /**组装排序检索条件*/
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
        /**查询条件*/
        if (wrapper != null) {
            return this.resultHandler(this.getMapper().selectPage(page, wrapper));
        } else {
            /**wrapper为null时用查询条件来封装wrapper*/
            Wrapper<T> pageWrapper = this.extensionWrapper(pageParam, this.getWrapper(pageParam));
            this.handlerPageWrapper(pageWrapper);
            return this.resultHandler(this.getMapper().selectPage(page, pageWrapper));
        }
    }

    /**通过查询条件查询记录总数*/
    default Integer count(Wrapper<T> wrapper) {
        return this.getMapper().selectCount(wrapper);
    }

    /**根据查询条件查询所有记录*/
    default List<T> list(Wrapper<T> wrapper) {
        return this.getMapper().selectList(wrapper);
    }

    /**根据id查询记录*/
    default T getById(Serializable id) {
        return this.getMapper().selectById(id);
    }

    /**查询记录集合*/
    default List<T> findAll() {
        return this.getMapper().selectList(Wrappers.emptyWrapper());
    }

    /**添加*/
    @Transactional(rollbackFor = {Exception.class})
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

    /**批量添加*/
    @Transactional(rollbackFor = {Exception.class})
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

    /**更新*/
    @Transactional(rollbackFor = {Exception.class})
    default Integer update(T entity) throws BusinessException {
        //更新前业务处理
        this.processBeforeOperation(entity, BaseOperationEnum.UPDATE);
        //字段唯一性检查
        this.checkUniqueField(entity, true);
        if (entity instanceof GaeaBaseEntity) {
            GaeaBaseEntity gaeaBaseEntity = (GaeaBaseEntity)entity;
            T dbEntity = this.getById(gaeaBaseEntity.getId());
            //删除历史缓存
            this.refreshCacheFields(dbEntity, BaseOperationEnum.DELETE);
        }

        Integer result = this.getMapper().updateById(entity);
        if (result != null && result >= 1) {
            //更新缓存
            this.refreshCacheFields(entity, BaseOperationEnum.UPDATE);
            //更新后业务处理
            this.processAfterOperation(entity, BaseOperationEnum.UPDATE);
            return result;
        } else {
            throw BusinessExceptionBuilder.build("Update.failure");
        }
    }

    /**通过id输入更新属性map*/
    default Integer updateFieldsById(Map<String, Object> map, Long id) {
        return this.getMapper().updateFieldsById(map, id);
    }

    /**通过id集合输入更新属性map批量更新*/
    default Integer updateBatchFieldsById(Map<String, Object> map, List<Long> ids) {
        return this.getMapper().updateFieldsBatchById(map, ids);
    }

    /**根据对象批量更新*/
    @Transactional(rollbackFor = {Exception.class})
    default Integer updateFieldsBatch(Map<String, Object> map, List<T> list) {
        return this.getMapper().updateFieldsBatch(map, list);
    }

    /**根据id删除*/
    @Transactional(rollbackFor = {Exception.class})
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

    /**通过条件删除*/
    @Transactional(rollbackFor = {Exception.class})
    default void delete(LambdaQueryWrapper<T> lambdaQueryWrapper) {
        this.getMapper().delete(lambdaQueryWrapper);
    }

    /**通过id集合删除*/
    @Transactional(rollbackFor = {Exception.class})
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


    /**根据查询条件封装QueryWrapper*/
    default QueryWrapper<T> getWrapper(P param) {
        QueryWrapper<T> queryWrapper = new QueryWrapper();
        //遍历param查询对象的所有属性
        Field[] fields = param.getClass().getDeclaredFields();
        Arrays.stream(fields).filter((field) -> {
            //获取属性的自定义注解Query
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
                /**根据查询条件组装queryWrapper*/
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

    /**检查实体类的唯一性*/
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

    /**添加刷新缓存字段*/
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

    /**格式化key*/
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


    /**根据属性名称获取属性值*/
    default Object getFieldValue(T entity, Field field) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity.getClass());
        Method readMethod = propertyDescriptor.getReadMethod();
        return readMethod.invoke(entity);
    }

    /**根据列名和值查询集合*/
    default List<T> list(String column, Object value) {
        QueryWrapper<T> queryWrapper = new QueryWrapper();
        queryWrapper.eq(column, value);
        return this.getMapper().selectList(queryWrapper);
    }

    /**封装排序对象*/
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

    default Integer updateColumn(Long id, String column, Object value) {
        Map<String, Object> params = new HashMap(1);
        params.put(column, value);
        return this.getMapper().updateFieldsById(params, id);
    }

    default IPage<T> resultHandler(IPage<T> iPage) {
        return iPage;
    }

    default Wrapper<T> handlerPageWrapper(Wrapper<T> pageWrapper) {
        return pageWrapper;
    }

    default Wrapper<T> extensionWrapper(P param, QueryWrapper<T> wrapper) {
        return wrapper;
    }

    /**批量操作前的处理*/
    default void processBatchBeforeOperation(List<T> entities, BaseOperationEnum operationEnum) throws BusinessException {
    }
    /**批量操作后的处理*/
    default void processBatchAfterOperation(List<T> entities, BaseOperationEnum operationEnum) throws BusinessException {
    }

    default void processBeforeOperation(T entity, BaseOperationEnum operationEnum) throws BusinessException {
    }

    default void processAfterOperation(T entity, BaseOperationEnum operationEnum) throws BusinessException {
    }
}
