package com.zods.largescreen.common.utils;
import com.zods.largescreen.common.annotation.DtoSkip;
import com.zods.largescreen.common.cache.CacheHelper;
import com.zods.largescreen.common.constant.Enabled;
import com.zods.largescreen.common.curd.dto.BaseDTO;
import com.zods.largescreen.common.holder.UserContentHolder;
import com.zods.largescreen.common.annotation.Formatter;
import com.zods.largescreen.common.annotation.FormatterType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @author jianglong
 * @version 1.0
 * @Description 对象bean转换工具类
 * @createDate 2022-06-20
 */
public abstract class GaeaBeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(GaeaBeanUtils.class);

    public GaeaBeanUtils() {
    }

    public static <T> T copyAndFormatter(Object source, T target) {
        Field[] declaredFields = target.getClass().getDeclaredFields();
        List<Field> fields = new ArrayList(declaredFields.length);
        List<String> skipFields = new ArrayList();
        List<Field> formatterTypeFields = new ArrayList();
        Field[] superDeclaredFields = declaredFields;
        int var7 = declaredFields.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Field field = superDeclaredFields[var8];
            if (field.isAnnotationPresent(DtoSkip.class)) {
                skipFields.add(field.getName());
            } else if (field.isAnnotationPresent(FormatterType.class)) {
                formatterTypeFields.add(field);
            } else {
                fields.add(field);
            }
        }
        skipFields.addAll((Collection)formatterTypeFields.stream().map(Field::getName).collect(Collectors.toList()));
        BeanUtils.copyProperties(source, target, (String[])skipFields.toArray(new String[0]));
        superDeclaredFields = target.getClass().getSuperclass().getDeclaredFields();
        fields.addAll(Arrays.asList(superDeclaredFields));
        formatterHandler(source, target, fields);
        Iterator var19 = formatterTypeFields.iterator();

        while(var19.hasNext()) {
            Field field = (Field)var19.next();

            try {
                PropertyDescriptor sourcePropertyDescriptor = new PropertyDescriptor(field.getName(), source.getClass());
                PropertyDescriptor targetPropertyDescriptor = new PropertyDescriptor(field.getName(), target.getClass());
                Object fieldSource = sourcePropertyDescriptor.getReadMethod().invoke(source);
                if (fieldSource != null) {
                    Method writeMethod = targetPropertyDescriptor.getWriteMethod();
                    switch(((FormatterType)field.getAnnotation(FormatterType.class)).type()) {
                        case OBJECT:
                            Object fieldTarget = field.getType().newInstance();
                            copyAndFormatter(fieldSource, fieldTarget);
                            writeMethod.invoke(target, fieldTarget);
                            break;
                        case LIST:
                            Type genericType = field.getGenericType();
                            ParameterizedType parameterizedType = (ParameterizedType)genericType;
                            Class fieldTargetClass = (Class)parameterizedType.getActualTypeArguments()[0];
                            List fieldTargetList = copyList((List)fieldSource, fieldTargetClass);
                            writeMethod.invoke(target, fieldTargetList);
                    }
                }
            } catch (Exception var18) {
                logger.error("FormatterType处理异常", var18);
            }
        }

        return target;
    }

    public static <D> List<D> copyList(List<?> sourceList, Class<? extends BaseDTO> targetClass) {
        return (List)sourceList.stream().map((source) -> {
            try {
                D target = (D)targetClass.newInstance();
                copyAndFormatter(source, target);
                return target;
            } catch (Exception var3) {
                logger.error("集合翻译失败,目标类：" + targetClass.getName(), var3);
                return null;
            }
        }).collect(Collectors.toList());
    }

    private static <T> void formatterHandler(Object source, T target, List<Field> fields) {
        Locale locale = LocaleContextHolder.getLocale();
        String language = locale.getLanguage();
        Map<String, Object> params = UserContentHolder.getContext().getParams();
        fields.stream().parallel().filter((field) -> {
            return field.isAnnotationPresent(Formatter.class);

        }).forEach((field) -> {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), target.getClass());
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(target);
                if (result instanceof Boolean) {
                    result = (Boolean)result ? Enabled.YES.getValue() : Enabled.NO.getValue();
                }

                if (result != null) {
                    Formatter annotation = (Formatter)field.getAnnotation(Formatter.class);
                    CacheHelper cacheHelper = (CacheHelper) ApplicationContextUtils.getBean(CacheHelper.class);
                    String key;
                    String hashKey;
                    if (StringUtils.isBlank(annotation.key())) {
                        hashKey = annotation.dictCode();
                        key = "gaea:dict:prefix:" + language + ":" + hashKey;
                    } else {
                        key = formatKey(annotation.key(), annotation.replace(), params, source);
                    }

                    hashKey = result.toString();
                    String dictValue = cacheHelper.hashGetString(key, hashKey);
                    if (StringUtils.isBlank(dictValue)) {
                        dictValue = cacheHelper.hashGetString(key, hashKey.toLowerCase());
                    }

                    if (StringUtils.isNotBlank(dictValue)) {
                        PropertyDescriptor descriptorTarget = new PropertyDescriptor(field.getName(), target.getClass());
                        if (StringUtils.isBlank(annotation.targetField())) {
                            Method writeMethod = descriptorTarget.getWriteMethod();
                            writeMethod.invoke(target, dictValue);
                        } else {
                            descriptorTarget = new PropertyDescriptor(annotation.targetField(), target.getClass());
                            if (descriptorTarget != null) {
                                descriptorTarget.getWriteMethod().invoke(target, dictValue);
                            }
                        }
                    }
                }
            } catch (Exception var15) {
                var15.printStackTrace();
            }

        });
    }

    public static String formatKey(String key, String[] replaceArray, Map<String, Object> params, Object source) {
        if (key.contains("${")) {
            Map<String, Object> keyPatternMap = new HashMap(2);
            String[] var5 = replaceArray;
            int var6 = replaceArray.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String fieldName = var5[var7];

                try {
                    Object value = params.get(fieldName);
                    if (null == value) {
                        Field declaredField = source.getClass().getDeclaredField(fieldName);
                        declaredField.setAccessible(true);
                        value = declaredField.get(source);
                    }

                    keyPatternMap.put(fieldName, value);
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
}
