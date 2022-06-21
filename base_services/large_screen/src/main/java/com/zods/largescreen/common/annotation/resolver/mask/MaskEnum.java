package com.zods.largescreen.common.annotation.resolver.mask;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public enum MaskEnum {
    IPHONE("(\\d{3})\\d{4}(\\d{4})", "$1****$2"),
    TEL("(\\d{2,6}-?)\\d{4}", "$1****"),
    EMAIL("(\\w{0,4})\\w+(@\\w+)", "$1*****$2"),
    ACCOUNT_NO("(\\d{4})\\d+(\\d{4})", "$1****$2"),
    CARD_ID("(\\d{6})\\d+(\\d{4})", "$1****$2"),
    COMMON("(\\w{%s})\\w+(\\w{%s})", "$1****$2");

    private String pattern;

    private String replacement;

    private MaskEnum(String pattern, String replacement) {
        this.pattern = pattern;
        this.replacement = replacement;
    }

    public String getPattern() {
        return this.pattern;
    }

    public String getReplacement() {
        return this.replacement;
    }
}
