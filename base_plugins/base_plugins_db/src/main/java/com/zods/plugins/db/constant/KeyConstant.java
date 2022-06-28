package com.zods.plugins.db.constant;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public interface KeyConstant {
    String DICT_PREFIX = "gaea:dict:prefix:";
    String USER_LOGIN_TOKEN = "system:login:token:";
    String USER_NICKNAME_KEY = "gaea:user:nickname:${orgCode}";
    String HASH_URL_ROLE_KEY = "gaea:security:url:roles:";
    String USER_ROLE_SET_PREFIX = "gaea:security:user:roles:";
    String AUTHORITY_ALL_MAP_PREFIX_WITH_APP = "gaea:security:authorities:all:";
    String AUTHORITY_ALL_MAP_PREFIX = "gaea:security:authorities:all";
}
