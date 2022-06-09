package com.zods.sso.gateway.common;


public enum HandlerExceptionEnums implements ExceptionEnums {


    SUCCESS(200, "成功"),
    FAILURE(202, "接受和处理、但处理未完成"),
    /**
     * 系统异常 500 服务器的内部错误
     */
    EXCEPTION(500, "服务器开小差，请稍后再试"),
    BAD_REQUEST(400, "参数异常,请检查参数格式或者参数类型"),
    NOT_PERMISSION(403, "没有权限"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    BUSSINESS_EXCETION(416, "业务异常"),

    /**
     * Sentinel 流控/降級异常信息
     */
    BLOCKED_FLOW(429, "访问太频繁了，请稍后再试!"),

    /**
     * 用户认证中心
     */
    AUTH_ERROR(4000, "认证异常:"),
    AUTH_LOGIN_ERROR(4001, "登陆出错"),
    // 关于token认证相关都统一为403
    AUTH_TOKEN_EXPIRED(403, "令牌过期"),
    AUTH_TOKEN_INVALID(403, "令牌无效"),
    AUTH_REFRESH_TOKEN_ERROR(403, "刷新令牌无效"),
    AUTH_REFRESH_TOKEN_EXPIRED(403, "刷新令牌过期"),
    AUTH_JWT_TOKEN_FAIL(403, "令牌验证不通过"),
    AUTH_JWT_REFRESH_TOKEN_FAIL(403, "刷新令牌不通过"),

    AUTH_NOT_SUPPORTED_CLIENT(4005, "不支持的客户端"),
    AUTH_PASS_NOT_INCORE(4006, "用户名或密码错误"),
    AUTH_EMPTY_USER(4007, "用户名或密码为空"),
    AUTH_NO_VALIDATE_CODE(4008, "验证码为空"),
    AUTH_VALIDATE_CODE_ERROR(4009, "验证码错误"),
    AUTH_VALIDATE_CODE_EXPIRED(4010, "验证码过期"),
    AUTH_USER_REPEAT_LOGIN(4011, "用户已登录,请勿重复登录"),
    AUTH_USER_NOT(4012, "用户不存在"),

    AUTH_USER_LOGOUT(4013, "用户已注销"),
    AUTH_PHONE_EMPTY(4014, "手机号为空"),
    AUTH_PHONE_NOT_EXISTS(4015, "手机号不存在"),
    AUTH_SMS_CODE_EMPTY(4016, "手机验证码为空"),
    AUTH_SMS_CODE_EXPIRED(4017, "手机验证码过期"),
    AUTH_SMS_CODE_ERROR(4018, "手机验证码错误"),
    AUTH_SMS_CODE_FORMATE_ERROR(4019, "手机号格式不正确"),

    /**
     * 导入导出
     */
    DATA_IS_NULL(4901, "请选择数据导出"),

    IMPORT_FAIL(4902, "未知错误，导入失败"),

    /**
     * rpc调用异常
     */
    RPC_ERROR(5000, "呀，网络出问题啦!"),

    /**
     * SQL异常
     */
    SQL_ERROR(600, "执行数据库语句时出错"),

    HANDLER_CALL_LOGIN_ERROR(-1, "登陆出错"),
    HANDLER_CALL_INVALIDE_TOKEN(-2, "令牌无效"),
    HANDLER_CALL_TOKEN_EXPIRED(-3, "令牌过期"),
    HANDLER_CALL_INVALIDE_REFRESHTOKEN(-4, "刷新令牌无效"),
    HANDLER_CALL_REFRESHTOKEN_EXPIRED(-5, "刷新令牌过期"),

    HANDLER_CALL_PASS_NOT_INCORE(1, "用户名或密码错误"),
    HANDLER_CALL_EMPTY_USER(2, "用户名或密码为空"),
    HANDLER_CALL_NO_VALIDATECODE(3, "验证码为空"),
    HANDLER_CALL_VALIDATECODE_ERROR(4, "验证码错误"),
    HANDLER_CALL_VALIDATECODE_EXPIRED(5, "验证码过期"),
    HANDLER_CALL_NO_ENABLED(6, "用户已注销"),
    HANDLER_CALL_NOT_LOGIN(7, "用户未登陆"),
    PHONE_EMPTY(10, "手机号为空"),
    PHONE_NOT_EXISTS(11, "手机号不存在"),
    SMS_CODE_EMPTY(12, "手机验证码为空"),
    SMS_CODE_EXPIRED(13, "手机验证码过期"),
    SMS_CODE_ERROR(14, "手机验证码错误"),

    /*********************物联网设备注册通信状态***************************/
    DEVICE_REGISTER_SUCCESS(0, "success"),
    DEVICE_REGISTER_CODE_UNAUTHORIZED(1, "registerCode unauthorized"),
    DEVICE_APP_KEY_UNAUTHORIZED(2, "appkey unauthorized"),
    DEVICE_SN_UNAUTHORIZED(3, "sn unauthorized"),
    DEVICE_BODY_INCOMPLETE_PARAMETER(4, "body parameter incomplete"),

    /***********************物联网设备下行状态***************************/
    EQUIP_RESPONSE_SUCCESS(1, "设备响应成功"),
    EQUIP_RESPONSE_UNSUCCESS(2, "设备响应失败"),
    CMD_SEND_SUCCESS(3, "命令发送成功"),
    CMD_SEND_UNSUCCESS(4, "命令发送失败"),
    CMD_TIME_OUT(5, "命令发送超时"),

    /*************************传感器状态定义*********************************/
    CHANNEL_ERROR_START(0, "无错误"),
    CHANNEL_POWER_ERR(-1, "供电异常"),
    CHANNEL_DATA_ERR(-2, "传感器数据异常"),
    CHANNEL_NO_DATA(-3, "采样间隔内没有采集到数据"),

    /*************************拾音反馈状态定义********************************/
    SOUND_STATE_NO(0, "无"),
    SOUND_STATE_LOW(1, "低"),
    SOUND_STATE_MID(2, "中"),
    SOUND_STATE_HIGH(3, "高");

    public int code;
    public String message;

    HandlerExceptionEnums(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public final int getCode() {
        return code;
    }

    @Override
    public final String getMessage() {
        return message;
    }
}
