package com.zods.plugins.zods.utils;


import org.apache.commons.lang3.StringUtils;

public class GaeaMaskUtils {
    public static final String defaultPattern = "(\\w{1})\\w+(\\w{1})";

    public GaeaMaskUtils() {
    }

    public static String mask(String source, String reg, String replacement) {
        return source.replaceAll(reg, replacement);
    }

    public static void main(String[] args) {
        String mask = "(\\w{1})\\w+(\\w{1})";
        String format = "(\\d{2,6}-?)\\d{4}";
        String mask1 = mask("121111", mask, "$1***$2");
        System.out.println(mask1);
        String s = StringUtils.leftPad("11", 4, '*');
        System.out.println(s);
    }
}
