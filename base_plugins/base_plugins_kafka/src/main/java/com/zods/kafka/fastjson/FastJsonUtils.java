package com.zods.kafka.fastjson;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: json工具类 支持各种数据类型转换
 * @Author: jianglong
 * @date 2019-09-24
 */
public class FastJsonUtils {

    /**
     * 默认日期格式（年月日时分秒）
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * json字符串转对象
     *
     * @param str   字符串
     * @param clazz 需要转成想要的对象
     * @param <T>   返回相应对象
     * @return
     */
    public static <T> T jsonToObject(String str, Class<T> clazz) {
        try {
            return JSON.parseObject(str, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象
     *
     * @param obj   字符串
     * @param clazz 需要转成想要的对象
     * @param <T>   返回相应对象
     * @return
     */
    public static <T> T jsonToObject(Object obj, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            return JSON.parseObject(gson.toJson(obj), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象
     *
     * @param str   字符串
     * @param clazz 需要转成想要的对象
     * @param <T>   返回相应对象
     * @return
     */
    public static <T> T jsonToObject(JSONObject str, Class<T> clazz) {
        try {
            return JSON.parseObject(str.toJSONString(), clazz);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 对象转json字符串，默认不执行进行日期转换
     *
     * @param obj 对象
     * @return
     */
    public static String objectTojson(Object obj) {
        return objectTojson(obj, false);
    }

    /**
     * 对象转json字符串，使用默认日期转换
     *
     * @param obj           对象
     * @param useDateFormat 自定义时间格式
     * @return
     */
    public static String objectTojson(Object obj, boolean useDateFormat) {
        return objectTojson(obj, useDateFormat, DEFAULT_DATE_FORMAT);
    }

    /**
     * 自定义日期格式
     *
     * @param obj
     * @param dateFormat
     * @return
     */
    public static String objectTojson(Object obj, String dateFormat) {
        return objectTojson(obj, true, dateFormat);

    }

    /**
     * 对象转字符串，总处理方法，不对外开放
     *
     * @param obj           javabean对象
     * @param useDateFormat
     * @param dateFormat
     * @return
     */
    private static String objectTojson(Object obj, boolean useDateFormat, String dateFormat) {
        if (useDateFormat) {
            return JSON.toJSONStringWithDateFormat(obj, dateFormat);
        }
        return JSON.toJSONString(obj);

    }

    /**
     * json格式解析为List集合，不解决格式时间问题
     *
     * @param str   json字符串
     * @param clazz 要转换的对象
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonTolist(String str, Class<T> clazz) {
        return JSON.parseArray(str, clazz);
    }

    /**
     * 对象转map
     *
     * @param object
     * @return
     */
    public static Map<String, String> objectToMapStr(Object object) {
        Map<String, String> dataMap = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                dataMap.put(field.getName(), String.valueOf(field.get(object)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dataMap;
    }

    /**
     * JSON字符串转map
     *
     * @param extension
     * @return
     */
    public static Map<String, String> jsonToMapStr(String extension) {
        Map<String, String> dataMap = new HashMap<>();
        try {
            JSONObject json = JSON.parseObject(extension);
            if (null != json) {
                for (String key : json.keySet()) {
                    dataMap.put(key, json.getString(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}
