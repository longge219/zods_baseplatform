package com.zods.plugins.db.utils;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class MaskUtils {
    public static final String defaultPattern = "(\\w{1})\\w+(\\w{1})";

    public MaskUtils() {
    }

    public static String mask(String source, String reg, String replacement) {
        return source.replaceAll(reg, replacement);
    }

}
