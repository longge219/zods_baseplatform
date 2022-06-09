package com.zods.sso.auth.temp;


/**
 * @author Wangchao
 * @version 1.0
 * @Description
 * @createDate 2020/10/13 10:00
 */
public class RequestMatcherConstants {

    /**
     * 权限认证中心URI
     */
    public static final String AUTHENTICATION_URL = "/oauth/login";
    public static final String LOGOUT_URL = "/oauth/logout";
    public static final String REFRESH_TOKEN_URL = "/oauth/refresh";
    public static final String OAUTH_CHECK_TOKEN_URL = "/oauth/check_token";
    public static final String OAUTH_TOKEN_URL = "/oauth/token";

    public static final String[] PERMIT_URLS = {
            AUTHENTICATION_URL,
            REFRESH_TOKEN_URL,
            OAUTH_CHECK_TOKEN_URL,
            LOGOUT_URL,
            "/",
            "/csrf",
            "/error",
            "/validate/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/druid/**",
            "/v2/**",
            "/resources/**",
            "/**/*.html",
            "/**/*.js",
            "/**/*.css",
            "/**/*.ico",
            "/favicon.ico",
            "/**/*.jpg",
            "/**/*.png",
            "/**/*.xml",
            "/firware/down/**",
            "/cmd/**/send/gb/message",
            "**/actuator/**",
            "/actuator/**",
            "**/platform-logs/**",
            "/api/auth/oauth/login/sms"
    };

    /**
     * 系统业务模块数据库配置的API权限--对应数据库auth_permission中ename字段
     */
    public static final String SYSTEM_MODULE_AUTHORITY = "System";

    /**
     * 系统业务模块数据库配置的API权限--对应数据库auth_permission中ename字段
     */
    public static final String BUSINESS_MODULE_AUTHORITY = "BusinessOperate";


    /**
     * 注册中心URIS 权限配置, 特别注意：URI只需配置api接口,不加上context-path
     */
    public static final String[] REGISTER_CENTER_URIS = {
            UriConstant.USER_ADD,
            UriConstant.USER_ADD_SUB_ACCOUNT,
            UriConstant.USER_GET_SUB_ACCOUNT,
            UriConstant.USER_RESET_PASSWORD,
            UriConstant.USER_SMS_SEND,
            UriConstant.USER_INFO_QUERY,

            UriConstant.ROLE_QUERY_EN_NAME_BY_USER_ID,
            UriConstant.ROLE_SELECT_BY_PERMISSION_ID,
            UriConstant.ROLE_INSERT_OR_UPDATE_AUTH_ROLE,

            UriConstant.PERMISSION_FIND_PERMISSION_BY_USER_ID,
            UriConstant.PERMISSION_INSERT_OR_UPDATE_AUTH_PERMISSION,

            UriConstant.CLIENT_ADD,
    };

    /**
     * 系统日志 URIS
     */
    public static final String[] SYSTEM_LOG_URIS = {
            UriConstant.ADD_LOG
    };

    /**
     * 运营中心URIS 权限配置, 特别注意：URI只需配置api接口,不加上context-path
     */
    public static final String[] OSS_URIS = {
            "/uploadOssByPath/**",
            "/uploadOss/**",
            "/deleteOss/**",
            "/downloadOss/**",
            "/downloadOss/path/**",
            "/uploadFiles/**",
            "/preview/file/**",
            "/previewList/**",
            "/downloadOssBytes/**",
            "/uploadOssBytes/**"
    };

    /**
     * 规则引擎规则管理服务URIS 权限配置, 特别注意：URI只需配置api接口,不加上context-path
     */
    public static final String[] RULE_URIS = {
            UriConstant.RULE_UPDATE,
            UriConstant.RULE_QUERY_ALL,
            UriConstant.RULE_DELETE_BY_ID,
            UriConstant.RULE_BOUND,
            UriConstant.RULE_DELETE_BY_ID,

            UriConstant.ALARM_CONFIG + UriConstant.ADD,
            UriConstant.ALARM_CONFIG + UriConstant.DELETE_ONE,
            UriConstant.ALARM_CONFIG + UriConstant.FIND_ONE,
            UriConstant.ALARM_CONFIG + UriConstant.UPDATE,
            UriConstant.ALARM_CONFIG + UriConstant.FIND_PAGE,
            UriConstant.ALARM_CONFIG + UriConstant.ENABLE,

            UriConstant.ALARM_RECORD + UriConstant.FIND_ONE,
            UriConstant.ALARM_RECORD + UriConstant.FIND_PAGE,
            UriConstant.ALARM_RECORD + UriConstant.ALARM_HADLE,

            UriConstant.RULE_CONFIG + UriConstant.ADD,
            UriConstant.RULE_CONFIG + UriConstant.DELETE_ONE,
            UriConstant.RULE_CONFIG + UriConstant.FIND_ONE,
            UriConstant.RULE_CONFIG + UriConstant.UPDATE,
            UriConstant.RULE_CONFIG + UriConstant.FIND_PAGE,
            UriConstant.RULE_CONFIG + UriConstant.ENABLE,
            UriConstant.RULE_CONFIG + UriConstant.RULE_LIST,

            UriConstant.RULE_RECORD + UriConstant.FIND_PAGE,

            UriConstant.RULE_SCENE + UriConstant.ADD,
            UriConstant.RULE_SCENE + UriConstant.DELETE_ONE,
            UriConstant.RULE_SCENE + UriConstant.FIND_ONE,
            UriConstant.RULE_SCENE + UriConstant.UPDATE,
            UriConstant.RULE_SCENE + UriConstant.FIND_PAGE,
            UriConstant.RULE_SCENE + UriConstant.ENABLE,
            UriConstant.RULE_SCENE + UriConstant.RULE_SCENE_ADD_RULES,
            UriConstant.RULE_SCENE + UriConstant.RULE_BOUND
    };

    /**
     * 管理产品URIS 权限配置, 特别注意：URI只需配置api接口,不加上context-path
     */
    public static final String[] MANAGER_URIS_TEST = {
            UriConstant.USER_ADD,
            UriConstant.USER_ADD_SUB_ACCOUNT,
            UriConstant.USER_GET_SUB_ACCOUNT,
            UriConstant.USER_RESET_PASSWORD,
            UriConstant.USER_SMS_SEND,
            UriConstant.USER_INFO_QUERY,
            UriConstant.USER_UPDATE,
            UriConstant.USER_ENDISABLE,
            UriConstant.USER_CHANGE_PWD,
            UriConstant.USER_QUERY_PAGE,
            UriConstant.USER_DELETE,
            UriConstant.USER_LIST,

            UriConstant.ROLE_QUERY_EN_NAME_BY_USER_ID,
            UriConstant.ROLE_SELECT_BY_PERMISSION_ID,
            UriConstant.ROLE_INSERT_OR_UPDATE_AUTH_ROLE,
            UriConstant.ROLE_ADD,
            UriConstant.ROLE_UPDATE,
            UriConstant.ROLE_QUERY_PAGE,
            UriConstant.ROLE_DETAIL,
            UriConstant.ROLE_DELETE,
            UriConstant.ROLE_USER_LIST,

            UriConstant.MENU_TREE,
            UriConstant.ROLE_MENU,
            UriConstant.CURRENT_USER_MENU,

            UriConstant.AREA_ADD,
            UriConstant.AREA_UPDATE,
            UriConstant.AREA_DETAIL,
            UriConstant.AREA_QUERY,
            UriConstant.AREA_TREE,
            UriConstant.AREA_DELETE,
            UriConstant.AREA_NEXT_LIST,

            UriConstant.DOMAIN_ADD,
            UriConstant.DOMAIN_UPDATE,
            UriConstant.DOMAIN_DETAIL,
            UriConstant.DOMAIN_QUERY,
            UriConstant.DOMAIN_TREE,
            UriConstant.DOMAIN_DELETE,

            UriConstant.PERMISSION_FIND_PERMISSION_BY_USER_ID,
            UriConstant.PERMISSION_INSERT_OR_UPDATE_AUTH_PERMISSION,
    };

    public static final String[] MANAGER_URIS = {
            UriConstant.MANAGER_DISTRIBUTION + UriConstant.ADD,
            UriConstant.MANAGER_DISTRIBUTION + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_DISTRIBUTION + UriConstant.FIND_ONE,
            UriConstant.MANAGER_DISTRIBUTION + UriConstant.UPDATE,
            UriConstant.MANAGER_DISTRIBUTION + UriConstant.FIND_PAGE,

            UriConstant.MANAGER_PRODUCT + UriConstant.ADD,
            UriConstant.MANAGER_PRODUCT + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.FIND_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.UPDATE,
            UriConstant.MANAGER_PRODUCT + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_PRODUCT + UriConstant.ENABLE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_QUERY_CODITIONAL,

            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_EVENT + UriConstant.ADD,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_EVENT + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_EVENT + UriConstant.FIND_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_EVENT + UriConstant.UPDATE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_EVENT + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_EVENT + UriConstant.ENABLE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_EVENT + UriConstant.MANAGER_QUERY_CODITIONAL,

            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_LABEL + UriConstant.ADD,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_LABEL + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_LABEL + UriConstant.FIND_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_LABEL + UriConstant.UPDATE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_LABEL + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_LABEL + UriConstant.ENABLE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_LABEL + UriConstant.MANAGER_QUERY_CODITIONAL,

            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_PROPERTY + UriConstant.ADD,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_PROPERTY + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_PROPERTY + UriConstant.FIND_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_PROPERTY + UriConstant.UPDATE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_PROPERTY + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_PROPERTY + UriConstant.ENABLE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_PROPERTY + UriConstant.MANAGER_QUERY_CODITIONAL,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_PROPERTY + UriConstant.MANAGER_PRODUCT_PROPERTY_CONFIG,


            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_SERVICE + UriConstant.ADD,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_SERVICE + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_SERVICE + UriConstant.FIND_ONE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_SERVICE + UriConstant.UPDATE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_SERVICE + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_SERVICE + UriConstant.ENABLE,
            UriConstant.MANAGER_PRODUCT + UriConstant.MANAGER_SERVICE + UriConstant.MANAGER_QUERY_CODITIONAL,

            UriConstant.MANAGER_DEVICE + UriConstant.ADD,
            UriConstant.MANAGER_DEVICE + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.FIND_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.UPDATE,
            UriConstant.MANAGER_DEVICE + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_DEVICE + UriConstant.ENABLE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_QUERY_CODITIONAL,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_DEVICE_QUANTITY,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_DEVICE_SUBSET,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_DEVICE_UNBIND_SUBSET,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_DEVICE_LAST_RUN_TIME_RECORD,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_DEVICE_APP_LOG_HISTORY,

            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_EVENT + UriConstant.ADD,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_EVENT + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_EVENT + UriConstant.FIND_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_EVENT + UriConstant.UPDATE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_EVENT + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_EVENT + UriConstant.ENABLE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_EVENT + UriConstant.MANAGER_QUERY_CODITIONAL,

            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_LABEL + UriConstant.ADD,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_LABEL + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_LABEL + UriConstant.FIND_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_LABEL + UriConstant.UPDATE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_LABEL + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_LABEL + UriConstant.ENABLE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_LABEL + UriConstant.MANAGER_QUERY_CODITIONAL,

            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_PROPERTY + UriConstant.ADD,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_PROPERTY + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_PROPERTY + UriConstant.FIND_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_PROPERTY + UriConstant.UPDATE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_PROPERTY + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_PROPERTY + UriConstant.ENABLE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_PROPERTY + UriConstant.MANAGER_QUERY_CODITIONAL,

            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_SERVICE + UriConstant.ADD,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_SERVICE + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_SERVICE + UriConstant.FIND_ONE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_SERVICE + UriConstant.UPDATE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_SERVICE + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_SERVICE + UriConstant.ENABLE,
            UriConstant.MANAGER_DEVICE + UriConstant.MANAGER_SERVICE + UriConstant.MANAGER_QUERY_CODITIONAL,

            UriConstant.MANAGER_CODE + UriConstant.ADD,
            UriConstant.MANAGER_CODE + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_CODE + UriConstant.FIND_ONE,
            UriConstant.MANAGER_CODE + UriConstant.UPDATE,
            UriConstant.MANAGER_CODE + UriConstant.DIC_QUERY_TYPE,
            UriConstant.MANAGER_CODE + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_CODE + UriConstant.DIC_QUERY_OPTION_TYPE_ADD,
            UriConstant.MANAGER_CODE + UriConstant.DIC_QUERY_OPTION_TYPE_PAGE,
            UriConstant.MANAGER_CODE + UriConstant.DIC_QUERY_OPTION_TYPE_DELETE,
            UriConstant.MANAGER_CODE + UriConstant.DIC_QUERY_OPTION_TYPE_UPDATE,
            UriConstant.MANAGER_CODE + UriConstant.DIC_QUERY_OPTION_TYPE_FIND,
            UriConstant.MANAGER_HOME + UriConstant.STATION_DATA_DETAIL,
            UriConstant.MANAGER_HOME + UriConstant.STATION_ALARM_LIST,
            UriConstant.MANAGER_HOME + UriConstant.STATION_ALARM_STATISTICS,
            UriConstant.MANAGER_HOME + UriConstant.STATION_BUILD_AREA_TREE,
            UriConstant.MANAGER_HOME + UriConstant.STATION_BUILD_AREA_ALL_TREE,
            UriConstant.MANAGER_HOME + UriConstant.STATION_WHOLE_AREA_TREE,
            UriConstant.MANAGER_HOME + UriConstant.STATION_SUBJECT_COUNT,
            UriConstant.MANAGER_HOME + UriConstant.SELL_STATION_SORT,
            UriConstant.MANAGER_HOME + UriConstant.DEVICE_COUNT,
            UriConstant.MANAGER_HOME + UriConstant.STATION_TYPE_COUNT,
            UriConstant.MANAGER_HOME + UriConstant.SELL_COUNT,
            UriConstant.MANAGER_HOME + UriConstant.POWER_COUNT,
            UriConstant.MANAGER_HOME + UriConstant.SERVICE_PERSON_COUNT,
            UriConstant.MANAGER_HOME + UriConstant.STATION_VIDEO_LIST,
            UriConstant.MANAGER_HOME + UriConstant.ALARM_COUNT,
            UriConstant.MANAGER_HOME + UriConstant.STATION_PROVINCE_CITY_COUNT,
            UriConstant.MANAGER_HOME + UriConstant.STATION_CITY_AREA_TREE,


            UriConstant.USER_ADD,
            UriConstant.USER_ADD_SUB_ACCOUNT,
            UriConstant.USER_GET_SUB_ACCOUNT,
            UriConstant.USER_RESET_PASSWORD,
            UriConstant.USER_SMS_SEND,
            UriConstant.USER_INFO_QUERY,
            UriConstant.USER_UPDATE,
            UriConstant.USER_ENDISABLE,
            UriConstant.USER_CHANGE_PWD,
            UriConstant.USER_QUERY_PAGE,
            UriConstant.USER_DELETE,
            UriConstant.USER_LIST,

            UriConstant.ROLE_QUERY_EN_NAME_BY_USER_ID,
            UriConstant.ROLE_SELECT_BY_PERMISSION_ID,
            UriConstant.ROLE_INSERT_OR_UPDATE_AUTH_ROLE,
            UriConstant.ROLE_ADD,
            UriConstant.ROLE_UPDATE,
            UriConstant.ROLE_QUERY_PAGE,
            UriConstant.ROLE_DETAIL,
            UriConstant.ROLE_DELETE,
            UriConstant.ROLE_USER_LIST,

            UriConstant.MENU_TREE,
            UriConstant.ROLE_MENU,
            UriConstant.CURRENT_USER_MENU,

            UriConstant.AREA_ADD,
            UriConstant.AREA_UPDATE,
            UriConstant.AREA_DETAIL,
            UriConstant.AREA_QUERY,
            UriConstant.AREA_TREE,
            UriConstant.AREA_DELETE,
            UriConstant.AREA_NEXT_LIST,

            UriConstant.DOMAIN_ADD,
            UriConstant.DOMAIN_UPDATE,
            UriConstant.DOMAIN_DETAIL,
            UriConstant.DOMAIN_QUERY,
            UriConstant.DOMAIN_TREE,
            UriConstant.DOMAIN_DELETE,

            UriConstant.PERMISSION_FIND_PERMISSION_BY_USER_ID,
            UriConstant.PERMISSION_INSERT_OR_UPDATE_AUTH_PERMISSION,
            /***采集数据查询**/
            UriConstant.RECEIVE_DATA_MANAGER_STATION + UriConstant.FIND_PAGE,
            UriConstant.RECEIVE_DATA_MANAGER_STATION + UriConstant.FIND_ONE,

            UriConstant.MANAGER_STATION + UriConstant.ADD,
            UriConstant.MANAGER_STATION + UriConstant.DELETE_ONE,
            UriConstant.MANAGER_STATION + UriConstant.FIND_ONE,
            UriConstant.MANAGER_STATION + UriConstant.UPDATE,
            UriConstant.MANAGER_STATION + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_STATION + UriConstant.FIND_LIST,
            UriConstant.MANAGER_STATION + UriConstant.ENABLE,
            UriConstant.MANAGER_STATION + UriConstant.STATION_STATISTICS,
            UriConstant.MANAGER_STATION + UriConstant.STATION_EXPORT,
            UriConstant.MANAGER_STATION + UriConstant.STATION_IMPORT,
            UriConstant.MANAGER_STATION + UriConstant.STATION_EXCEL_EXAMPLE,
            UriConstant.MANAGER_STATION + UriConstant.STATION_LIST,
            UriConstant.MANAGER_STATION + UriConstant.UPDATE_EXPIRE_CONFIG,
            UriConstant.MANAGER_STATION + UriConstant.FIND_EXPIRE_CONFIG,
            UriConstant.MANAGER_STATION + UriConstant.FIND_DATA_UNKNOW,

            UriConstant.VISIT_LOG + UriConstant.FIND_ONE,
            UriConstant.VISIT_LOG + UriConstant.FIND_PAGE,
            UriConstant.VISIT_LOG + UriConstant.ADD_VISIT_LOG,
            UriConstant.MANAGER_VISIT + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_VISIT + UriConstant.FIND_ONE,
            UriConstant.MANAGER_VISIT + UriConstant.ADD_VISIT_LOG,
            UriConstant.MANAGER_SYSTEM + UriConstant.FIND_PAGE,
            UriConstant.MANAGER_SYSTEM + UriConstant.FIND_ONE,
            UriConstant.MANAGER_SYSTEM + UriConstant.ADD_VISIT_LOG,

            UriConstant.QUERY_ATTACHMETNS,
            UriConstant.QUERY_ATTACHMETN_ON,
            UriConstant.UPLOAD_ATTACHMENT,
            UriConstant.DOWN_ATTACHMENT,
            UriConstant.DELETE_ATTACHMENT,

            UriConstant.WEBSOCKET_API,

            UriConstant.ALARM_RECORD + UriConstant.FIND_ONE,
            UriConstant.ALARM_RECORD + UriConstant.FIND_PAGE,
            UriConstant.ALARM_RECORD + UriConstant.ALARM_HADLE,
            UriConstant.ALARM_RECORD + UriConstant.ALARM_RECORD_SAME_TYPE,
            UriConstant.STATION_BUSINESS + "/processSosAlarm",

            "/notice/run",
            "/notice/type/list",
            "/notice/cfg/add",
            "/notice/cfg/update",
            "/notice/cfg/delete",
            "/notice/cfg/findSetupCfg",
            "/notice/cfg/findOne",
            "/notice/cfg/findPageInfo",
            "/notice/template/add",
            "/notice/template/update",
            "/notice/template/delete",
            "/notice/template/findTemplateCfg",
            "/notice/template/findOne",
            "/notice/template/findPageInfo",
            "/notice/template/findSimpList",
            "/notice/cfg/findSimpList",
            "/notice/cfg/export",
            "/notice/cfg/import",
            "/notice/template/export",
            "/notice/template/import",
            "/notice/record/findPageInfo",

            "/version/save",
            "/version/query/page"
    };


}