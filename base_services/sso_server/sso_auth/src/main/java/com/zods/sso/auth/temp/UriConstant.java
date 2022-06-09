package com.zods.sso.auth.temp;

/**
 * @author Wangchao
 * @version 1.0
 * @Description API URI 常量
 * @createDate 2021/1/27 14:24
 */
public interface UriConstant {

    /*****************用户注册中心*************************************************/
    String REGISTER = "/api/register";

    /**
     * 用户
     */
    String USER_ADD = "/user/add";
    String USER_ADD_SUB_ACCOUNT = "/user/addSubAccount";
    String USER_GET_SUB_ACCOUNT = "/user/getSubAccount";
    String USER_RESET_PASSWORD = "/user/resetPassword";
    String USER_SMS_SEND = "/user/smsSend";
    String USER_INFO_QUERY = "/user/detail";
    String USER_UPDATE = "/user/update";
    String USER_ENDISABLE = "/user/enDisable";
    String USER_CHANGE_PWD = "/user/changePwd";
    String USER_QUERY_PAGE = "/user/queryPage";
    String USER_DELETE = "/user/delete";
    String USER_LIST = "/user/list";
    /**
     * 角色
     */
    String ROLE_QUERY_EN_NAME_BY_USER_ID = "/role/queryRoleEnNameByUserId";
    String ROLE_SELECT_BY_PERMISSION_ID = "/role/selectByPermissionId";
    String ROLE_INSERT_OR_UPDATE_AUTH_ROLE = "/role/insertOrUpdateAuthRole";
    String ROLE_ADD = "/role/add";
    String ROLE_UPDATE = "/role/update";
    String ROLE_QUERY_PAGE = "/role/queryPage";
    String ROLE_DETAIL = "/role/detail";
    String ROLE_DELETE = "/role/delete";
    String ROLE_USER_LIST = "/role/user/list";

    /**
     * 菜单
     */
    String MENU_TREE = "/menu/tree";
    String ROLE_MENU = "/query/menus/roleId";
    String CURRENT_USER_MENU = "/currentUser/menus";

    /**
     * 权限
     */
    String PERMISSION_FIND_PERMISSION_BY_USER_ID = "/permission/findPermissionByUserId";
    String PERMISSION_INSERT_OR_UPDATE_AUTH_PERMISSION = "/permission/insertOrUpdateAuthPermission";

    String CLIENT_ADD = "/client/add";

    /**
     * 部门
     */
    String DOMAIN_ADD = "/domain/add";
    String DOMAIN_UPDATE = "/domain/update";
    String DOMAIN_DETAIL = "/domain/detail";
    String DOMAIN_QUERY = "/domain/queryPage";
    String DOMAIN_TREE = "/domain/tree";
    String DOMAIN_DELETE = "/domain/delete";

    /**
     * 行政级别
     */
    String AREA_ADD = "/area/add";
    String AREA_UPDATE = "/area/update";
    String AREA_DETAIL = "/area/detail";
    String AREA_QUERY = "/area/queryPage";
    String AREA_TREE = "/area/tree";
    String AREA_DELETE = "/area/delete";
    String AREA_NEXT_LIST = "/area/next/list";

    /**
     * 根据查询用户信息
     */
    String REGISTER_USER_ADD = "/user/addUser";


    /*****************日志******************************************************/
    String LOG = "/api/log";
    String VISIT_LOG = "/visitLogManage";
    String SYS_LOG = "/systemLogManage";

    /**
     * 添加日志
     */
    String ADD_LOG = "/addLog";
    String ADD_VISIT_LOG = "/addVisitLog";
    String ADD_SYS_LOG = "/addSysLog";

    /*****************文件存储**************************************************/
    String OSS = "/api/oss";

    String UPLOAD_OSS_PATH = "/uploadOssByPath";
    String UPLOAD_OSS = "/uploadOss";
    String UPLOAD_FILES = "/uploadFiles";
    String DELETE_OSS = "/deleteOss";
    String DOWNLOAD_OSS_PATH = "/downloadOss/path";
    String DOWNLOAD_OSS = "/downloadOss";
    String PREVIEW_OSS = "/preview/file";
    String PREVIEW_FILES = "/previewList";
    String DOWNLOAD_OSS_BYTES = "/downloadOssBytes";

    /**
     * 附件
     */
    String UPLOAD_ATTACHMENT = "/iom/attachment/upload";
    String DOWN_ATTACHMENT = "/iom/attachment/download";
    String DELETE_ATTACHMENT = "/iom/attachment/delete";
    String QUERY_ATTACHMETNS = "/iom/attachment/page/query";
    String QUERY_ATTACHMETN_ON = "/iom/attachment/queryOne";

    /*****************权限认证中心**************************************************/
    String AUTH_LOGIN = "/oauth/login";
    String AUTH_LOGOUT = "/oauth/logout";
    String AUTH_REFRESH_TOKEN = "/oauth/refresh";
    String AUTH_SMS_SEND = "/oauth/sms/send";


    /*****************HBASE**************************************************/
    String HBASE = "/api/hbase";

    String GET_ALL_NAME_SPACE = "/getAllNameSpace";
    String CREATE_NAME_SPACE = "/createNameSpace";
    String DELETE_NAME_SPACE = "/deleteNameSpace";
    String CREATE_TABLE = "/createTable";
    String DELETE_TABLE = "/deleteTable";
    String GET_ALL_TABLE = "/getAllTable";

    /*****************规则引擎drools规则管理服务**************************************************/
    String RULE_DROOLS = "/api/rule/drools";
    String RULE_SCENE_FEIGN = "/api/rule/scene";
    String RULE_SCENE = "/scene";
    String RULE_CONFIG_FEIGN = "/api/rule/config";
    String RULE_CONFIG = "/config";
    String RULE_RECORD_FEIGN = "/api/rule/record";
    String RULE_RECORD = "/record";

    String RULE_LIST = "/rule/list";

    String RULE_QUERY_ALL = "/queryAllRules";
    String RULE_QUERY_BY_ID = "/queryRuleById";
    String RULE_QUERY_BY_RULE_TYPE_AND_NAME = "/queryRuleRuleTypeAndName";
    String RULE_UPDATE = "/addOrUpdateRule";
    String RULE_DELETE_BY_ID = "/deleteRuleById";
    String RULE_BOUND = "/bound";
    String RULE_SCENE_ADD_RULES = "/addRules";

    String ALARM_CONFIG_FEIGN = "/api/rule/alarmConfig";
    String ALARM_RECORD_FEIGN = "/api/rule/alarmRecord";
    String ALARM_CONFIG = "/alarmConfig";
    String ALARM_RECORD = "/alarmRecord";
    String ALARM_RECORD_SAME_TYPE = "/alarmRecord/sameType";
    String ALARM_HADLE = "/hadle";

    String ALARM_RECORD_FIND_ONE = "/findOneRecord";

    /*****************数据管理平台服务**************************************************/
    String MANAGER_HOME = "/home";
    String MANAGER_DISTRIBUTION = "/distribution";
    String MANAGER_STATION = "/station";
    String RECEIVE_DATA_MANAGER_STATION = "/receiveData";
    String MANAGER_PRODUCT = "/product";
    String MANAGER_CODE = "/dic";
    String MANAGER_DEVICE = "/device";
    String MANAGER_DEVICE_STATION = "/deviceStation";
    String MANAGER_PROPERTY = "/property";
    String MANAGER_SERVICE = "/service";
    String MANAGER_EVENT = "/event";
    String MANAGER_LABEL = "/label";
    String MANAGER_FEIGN = "/api/iot";
    String MANAGER_DOMAIN = "/domain";
    String MANAGER_AREA = "/area";
    String MANAGER_USER = "/user";
    String MANAGER_CLIENT = "/client";
    String MANAGER_PERMISSION = "/permission";
    String MANAGER_ROLE = "/role";
    String MANAGER_QUERY_CODITIONAL = "/conditional";
    String MANAGER_DEVICE_QUANTITY = "/quantity";
    String MANAGER_DEVICE_SUBSET = "/subset";
    String MANAGER_DEVICE_UNBIND_SUBSET = "/unbind/subset";
    String MANAGER_DEVICE_LAST_RUN_TIME_RECORD = "/lastRunTimeRecord";
    String MANAGER_DEVICE_APP_LOG_HISTORY = "/appLogHistory";
    String MANAGER_PRODUCT_PROPERTY_CONFIG = "/config";
    String MANAGER_VISIT = "/visitLogManage";
    String MANAGER_SYSTEM = "/systemLogManage";

    String DIC_QUERY_TYPE = "/findTypeContentsByTypeCode";
    String DIC_QUERY_OPTION_TYPE_ADD = "/optionType/add";
    String DIC_QUERY_OPTION_TYPE_PAGE = "/optionType/getPageInfo";
    String DIC_QUERY_OPTION_TYPE_DELETE = "/optionType/deleteOne";
    String DIC_QUERY_OPTION_TYPE_UPDATE = "/optionType/update";
    String DIC_QUERY_OPTION_TYPE_FIND = "/optionType/findOne";

    String DEVICE_STATION_PAGE = "/getStationDevicePageInfo";
    String DEVICE_STATION_LIST = "/getStationDevice/list";

    String STATION_BUSINESS = "/stationBusiness";


    /************BASE API **************/
    String ADD = "/add";
    String FIND_ONE = "/findOne";
    String DELETE_ONE = "/deleteOne";
    String UPDATE = "/update";
    String FIND_PAGE = "/findPageInfo";
    String FIND_LIST = "/findList";
    String ENABLE = "/enable";

    String STATION_STATISTICS = "/statistics";
    String STATION_EXPORT = "/export";
    String STATION_IMPORT = "/import";
    String STATION_EXCEL_EXAMPLE = "/excel/example";
    String STATION_LIST = "/station/list";
    String STATION_DATA_DETAIL = "/station/data";
    String STATION_ALARM_LIST = "/station/deviceAlarm";
    String STATION_ALARM_STATISTICS = "/station/alarmStatistics";
    String STATION_BUILD_AREA_TREE = "/area/tree";
    String STATION_BUILD_AREA_ALL_TREE = "/area/allTree";
    String STATION_CITY_AREA_TREE = "/area/cityTree";
    String STATION_WHOLE_AREA_TREE = "/area/wholeTree";
    String UPDATE_EXPIRE_CONFIG = "/update/expireConfig";
    String FIND_EXPIRE_CONFIG = "/find/expireConfig";
    String FIND_DATA_UNKNOW = "/find/dataUnKnow";

    String STATION_SUBJECT_COUNT = "/stationSubjectCount";
    String SELL_STATION_SORT = "/sellStationSort";
    String DEVICE_COUNT = "/deviceCount";
    String STATION_TYPE_COUNT = "/stationTypeCount";
    String SELL_COUNT = "/sellCount";
    String POWER_COUNT = "/powerCount";
    String SERVICE_PERSON_COUNT = "/servicePersonCount";
    String STATION_VIDEO_LIST = "/station/videoInfos";
    String ALARM_COUNT = "/alarmCount";
    String STATION_PROVINCE_CITY_COUNT = "/province/stationCount";

    /***WebSocket API*/
    String WEBSOCKET_API = "/websocket/message";
}