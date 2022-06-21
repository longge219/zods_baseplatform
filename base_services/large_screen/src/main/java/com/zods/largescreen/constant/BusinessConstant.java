package com.zods.largescreen.constant;
/**
 * @author jianglong
 * @version 1.0
 * @Description 常量
 * @createDate 2022-06-20
 */
public interface BusinessConstant {

    String LEFT_BIG_BOAST = "{";
    String RIGTH_BIG_BOAST = "}";
    String LEFT_MIDDLE_BOAST = "[";
    String RIGHT_MIDDLE_BOAST = "]";

    /**
     * 字典项重复
     */
    String DICT_ITEM_EXIST_GROUP = "dictItemExist";

    /**
     * 所有url的权限码缓存key
     */
    String GAEA_SECURITY_AUTHORITIES= "gaea:security:authorities:all";

    /**
     * 用户登录的token缓存key
     */
    String GAEA_SECURITY_LOGIN_TOKEN = "gaea:security:login:token:%s";

    /**
     * 用户登录的主信息缓存信息
     */
    String GAEA_SECURITY_LOGIN_USER = "gaea:security:login:user:%s";
}
