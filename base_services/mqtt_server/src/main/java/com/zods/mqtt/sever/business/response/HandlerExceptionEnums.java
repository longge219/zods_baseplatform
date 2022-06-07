package com.zods.mqtt.sever.business.response;
import com.zods.mqtt.sever.business.exception.ExceptionEnums;

public enum HandlerExceptionEnums implements ExceptionEnums {


    SUCCESS(200, "succ"),
    PARAMETER_ERROR(400, "参数异常,请检查参数格式或者参数类型"),
    NOT_PERMISSION(403, "没有权限"),
    ERROR(500, "后台服务异常,请检查日志"),
    TASK_PROCESSED(417, "该任务已被处理或已丢失"),
    BUSSINESS_EXCETION(416, "业务异常"),
    METHOD_NOT_ALLOWED(405, "方法不允许"),
    BAD_REQUEST(400, "无效的请求"),
    NOT_FOUND(404, "资源不存在"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    SYS_TOKEN_EXPIRE(421, "登陆信息已过期"),
    SYS_LSH_NOT_EMPTY(423, "请求流水号不能为空"),
    SYS_SIGN_NOT_EMPTY(424, "请求签名信息不能为空"),
    SYS_LSH_UNVALID(425, "请求流水号验证未通过"),
    REQUEST_TIMEOUT(408, "请求超时"),
    SYS_SIGN_UNVALID(426, "请求签名验证未通过"),
    RESCODE_REFTOKEN_MSG(1006, "refresh token(有返回数据)"),
    JWT_ERRCODE_NULL(4000, "not access token in the head of http"),
    JWT_ERRCODE_EXPIRE(4001, "access token is expired,please refresh token"),
    JWT_ERRCODE_FAIL(4002, "token验证不通过"),
    JWT_ERRCODE_ERROR(4003, "token验证异常"),
    REFRESH_TOKEN_ERRCODE_EXPIRE(4004, "refresh token is expired ,please login again"),
    NOT_HAVE_THIS_ROLE(4005, "this account doesn't have some roles for this http request"),
    JWT_NOT_REFRESH_TOKEN(4006, "not refresh token in the head of http"),
    NOT_TIMESTAMP_PARAM(4007, "not timestamp param in the head of http"),
    ERROR_TIMESTAMP_PARAM(4008, "timestamp param in the head of http not correct"),

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
    PHONE_EMPTY(10, "手机号为空"),
    PHONE_NOT_EXISTS(11, "手机号不存在"),
    SMS_CODE_EMPTY(12, "手机验证码为空"),
    SMS_CODE_EXPIRED(13, "手机验证码过期"),
    SMS_CODE_ERROR(14, "手机验证码错误"),

    /*********************物联网设备注册通信状态***************************/
    DEVICE_REGISTER_SUCCESS(0, "succ"),
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
