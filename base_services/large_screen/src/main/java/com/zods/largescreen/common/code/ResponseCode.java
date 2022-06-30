package com.zods.largescreen.common.code;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public final class ResponseCode {

    public static final String SUCCESS_CODE = "200";

    public static final String FAIL_CODE = "500";

    public static final String RECORD_NO_EXIST = "500-0001";

    public static final String INSERT_FAILURE = "Insert.failure";

    public static final String UPDATE_FAILURE = "Update.failure";

    public static final String DELETE_FAILURE = "Delete.failure";

    public static final String UNIQUE_KEY = "500-0005";

    public static final String MULTI_RECORDS = "500-0006";

    public static final String NOT_NULL = "field.not.null";

    public static final String NOT_EMPTY = "field.not.empty";

    public static final String MIN = "field.min";

    public static final String MAX = "field.max";

    public static final String DICT_ERROR = "field.dict.error";

    public static final String USER_PASSWORD_ERROR = "User.password.error";

    /**用户名或者密码不正确*/
    public static final String LOGIN_ERROR = "login.error";

    /**新密码不能和原密码一致*/
    public static final String USER_PASSWORD_CONFIG_PASSWORD_CANOT_EQUAL = "user.password.config.password.canot.equal";

    /**密码和确认密码不一致*/
    public static final String USER_INCONSISTENT_PASSWORD_ERROR = "user.inconsistent.password.error";

    /**旧密码不正确*/
    public static final String USER_OLD_PASSWORD_ERROR = "user.old.password.error";

    /**用户token过期*/
    public static final String USER_TOKEN_EXPIRED = "User.token.expired";

    /**字典项重复*/
    public static final String DICT_ITEM_REPEAT = "Dict.item.code.exist";

    /**数字字典国际化标识不能为null*/
    public static final String DICT_CODE_LOCALE_NULL = "500-00002";

    /**参数为空*/
    public static final String PARAM_IS_NULL = "Rule.execute.param.null";

    /**规则编译不通过*/
    public static final String RULE_CONTENT_COMPILE_ERROR = "Rule.content.compile.error";

    /**规则执行不通过*/
    public static final String RULE_CONTENT_EXECUTE_ERROR = "Rule.content.execute.error";

    /**规则编码已存在*/
    public static final String RULE_CODE_EXIST = "Rule.code.exist";

    /**对应规则内容不存在*/
    public static final String RULE_CONTENT_NOT_EXIST = "Rule.content.not.exist";

    /**对应规则字段值不存在*/
    public static final String RULE_FIELDS_NOT_EXIST = "Rule.fields.not.exist";

    /**规则字段必填*/
    public static final String RULE_FIELD_VALUE_IS_REQUIRED = "Rule.field.value.is.required";

    /**规则字段值类型错误*/
    public static final String RULE_FIELD_VALUE_TYPE_ERROR = "Rule.field.value.type.error";

    /**规则参数校验不通过*/
    public static final String RULE_FIELDS_CHECK_ERROR = "Rule.fields.check.error";

    /**组件未加载*/
    public static final String COMPONENT_NOT_LOAD = "Component.load.check.error";

    public static final String AUTH_PASSWORD_NOTSAME = "1001";

    public static final String OLD_PASSWORD_ERROR = "1003";

    public static final String USER_ONTEXIST_ORGINFO = "1004";

    public static final String USER_ONTEXIST_ROLEINFO = "1005";

    public static final String MENU_TABLE_CODE_EXIST = "1006";

    public static final String USER_CODE_ISEXIST = "1007";

    public static final String ROLE_CODE_ISEXIST = "1008";

    public static final String MENU_CODE_ISEXIST = "1009";

    public static final String ORG_CODE_ISEXIST = "1010";

    public static final String SEARCHNAME_ISEXIST = "1011";

    public static final String SETTINGNAME_ISEXIST = "1012";

    public static final String DICCODE_ISEXIST = "1013";

    public static final String DEVICEID_LENGTH = "1014";

    public static final String USERINFO_EMPTY = "1015";

    public static final String FILE_EMPTY_FILENAME = "2001";

    public static final String FILE_SUFFIX_UNSUPPORTED = "2002";

    public static final String FILE_UPLOAD_ERROR = "2003";

    public static final String FILE_ONT_EXSIT = "2004";

    public static final String LIST_IS_EMPTY = "2005";

    public static final String PUSHCODE_NEED_UNIQUE = "3001";

    public static final String RECEIVER_IS_EMPTY = "3002";

    public static final String DATA_SOURCE_CONNECTION_FAILED = "4001";

    public static final String DATA_SOURCE_TYPE_DOES_NOT_MATCH_TEMPORARILY = "4002";

    public static final String EXECUTE_SQL_ERROR = "4003";

    public static final String INCOMPLETE_PARAMETER_REPLACEMENT_VALUES = "4004";

    public static final String EXECUTE_JS_ERROR = "4005";

    public static final String ANALYSIS_DATA_ERROR = "4006";

    public static final String REPORT_CODE_ISEXIST = "4007";

    /**编码存在重复*/
    public static final String SET_CODE_ISEXIST = "4008";

    public static final String SOURCE_CODE_ISEXIST = "4009";

    public static final String CLASS_NOT_FOUND = "4010";

    public static final String WIDGET_CODE_ISEXIST = "4011";

    public static final String REPORT_SHARE_LINK_INVALID = "report.share.link.invalid";


    public ResponseCode() {
    }
}
