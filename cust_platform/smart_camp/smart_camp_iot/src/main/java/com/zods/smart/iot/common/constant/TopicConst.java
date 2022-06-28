package com.zods.smart.iot.common.constant;

public interface TopicConst {

    /******************************************【kafka主题】*************************************/
    /**
     * 用户人脸数据已采集主题
     */
    String USER_FACE_CAPTURED_TOPIC = "USER_FACE_CAPTURED_TOPIC";
    String USER_FACE_CAPTURED_GROUP = "USER_FACE_CAPTURED_GROUP";
    /**
     * 删除用户主题
     */
    String USER_DELETE_TOPIC = "USER_DELETE_TOPIC";
    String USER_DELETE_GROUP = "USER_DELETE_GROUP";
    String DEVICE_CODE_CHANGED_TOPIC = "DEVICE_CODE_CHANGED_TOPIC";
    /**
     * 设备事件上报主题
     */
    String DEVICE_EVENT_TOPIC = "DEVICE_EVENT_TOPIC";
    String DEVICE_ACCESS_EVENT_GROUP = "DEVICE_ACCESS_EVENT_GROUP";
    String DEVICE_ALARM_EVENT_GROUP = "DEVICE_EVENT_ALARM_GROUP";
    /**
     * 系统级预警主题
     **/
    String SYSTEM_ALARM_TOPIC = "ALARM_DATA";

    /**
     * 环境采集数据主题
     **/
    String ENVIROMENT_TOPIC = "ENVIROMENT_DATA";
    String ENVIROMENT_GROUP = "ENVIROMENT_GROUP";
    String ENVIROMENT_PARAM = "ENVIROMENT_PARAM";

    /**
     * RFID读卡器采集数据主题
     **/
    String RFID_TOPIC = "RFID_DATA";

    /**
     * 人脸下发主题
     **/
    String USER_INDENTIFY_TOPIC = "USER_IDENTIFY_TOPIC";
    String USER_CANCEL_IDENTIFY_TOPIC = "USER_CANCEL_IDENTIFY_TOPIC";
    String USER_INDENTIFY_GROUP = "USER_INDENTIFY_GROUP";
    String USER_CANCEL_IDENTIFY_GROUP = "USER_CANCEL_IDENTIFY_GROUP";

    /**
     * 配置设备设备操作相关主题
     **/
    String DEVICE_OPERATION_TOPIC = "DEVICE_OPERATION_TOPIC";
    String DEVICE_OPERATION_GROUP = "DEVICE_OPERATION_GROUP";

    /**
     * 钥匙柜主题
     */
    String KEY_CABINET_TOPIC = "KEY_CABINET_DATA";
    String KEY_CABINET_GROUP = "KEY_CABINET_GROUP";

    /**
     * UPS主题
     */
    String UPS_TOPIC = "UPS_DATA";
    String UPS_GROUP = "UPS_GROUP";
    String UPS = "UPS";
    String UPS_CHINIESE_NAME = "UPS电池供电";

    /**
     * 门禁设备授权重置
     */
    String ACCESS_AUTH_RESET_TOPIC = "ACCESS_AUTH_RESET_TOPIC";
    String ACCESS_AUTH_RESET_GROUP = "ACCESS_AUTH_RESET_GROUP";

    /**
     * 值班人员门禁授权
     */
    String PLAN_USER_ACCESS_TOPIC = "PLAN_USER_ACCESS_TOPIC";
    String PLAN_USER_ACCESS_GROUP = "PLAN_USER_ACCESS_GROUP";


    /**
     * 设备操作-----------------包括三色灯，探照灯，除湿机相关操作
     */
    String DEVICE_STATUS_OPERATION_DATA = "DEVICE_STATUS_OPERATION_DATA";
    String DEVICE_STATUS_OPERATION_GROUP = "DEVICE_STATUS_OPERATION_GROUP";

    String DEVICE_STATUS_UPDATE_DATA = "DEVICE_STATUS_UPDATE_DATA";
    String DEVICE_STATUS_UPDATE_GROUP = "DEVICE_STATUS_UPDATE_GROUP";

    /**
     * 响应报警-----------------三色灯主题
     */
    String ALARM_LAMP_STATUS_OPERATION_TOPIC = "ALARM_LAMP_STATUS_OPERATION_DATA";
    String ALARM_LAMP_STATUS_OPERATION_GROUP = "ALARM_LAMP_STATUS_OPERATION_GROUP";
    String ALARM_LAMP_STATUS_UPDATE_DATA = "ALARM_LAMP_STATUS_UPDATE_DATA";
    String ALARM_LAMP_STATUS_UPDATE_GROUP = "ALARM_LAMP_STATUS_UPDATE_GROUP";

    /**
     * 电子围栏topic
     */
    String ELECTRIC_FENCE_TOPIC = "ELECTRIC_FENCE_TOPIC";
    String ELECTRIC_FENCE_GROUP = "ELECTRIC_FENCE_GROUP";

    /**
     * 库室红外topic
     */
    String ROOM_INFRARED_TOPIC = "WAREHOUSE_EVENT_TOPIC";
    String ROOM_INFRARED_GROUP = "WAREHOUSE_EVENT_GROUP";

    /**
     * 库室墙体振动topic
     */
    String WALL_VIBRATION_TOPIC = "WALL_VIBRATION_TOPIC";
    String WALL_VIBRATION_GROUP = "WALL_VIBRATION_GROUP";

    /**
     * 下行响应主题
     */
    String RTU_CMD_DOWN_WARDS_RESPONSE_TOPIC = "CMD_DOWN_WARDS_RESPONSE_TOPIC";

    /**
     * 通知广播主题
     */
    String BROAD_CAST_DEVICE_TYPE = "14";
    String BROAD_CAST_TOPIC = "BROAD_CAST_TOPIC";
    String BROAD_CAST_TOPIC_GROUP = "BROAD_CAST_TOPIC_GROUP";

    /**
     * 警戒广播主题
     */
    String ALARM_BROAD_CAST_DEVICE_TYPE = "16";
    String ALARM_BROAD_CAST_TOPIC = "ALARM_BROAD_CAST_TOPIC";
    String ALARM_BROAD_CAST_TOPIC_GROUP = "ALARM_BROAD_CAST_TOPIC_GROUP";

    /**
     * 下行指令响应分组
     */
    String RTU_CMD_DOWN_WARDS_RESPONSE_GROUP = "CMD_DOWN_WARDS_TOPIC_RESPONSE_GROUP";

    /**
     * 弹药、器材借调变化发送kafka主题
     */
    String MATERIAL_SECONDMENT_TOPIC = "MATERIAL_SECONDMENT_TOPIC";

    /******************************************【kafka主题】*************************************/

    /*****************************************【其他预警数据处理所需常量】*************************************/

    /***************************环境数据所需参数常量**/
    String TEM_MSG = "当前温度【%s°C】，%s规则值【%s°C】";
    String HUM_MSG = "当前湿度【%s%%RH】，%s规则值【%s%%RH】";
    String HIGH = "高于";
    String LOW = "低于";
    String EXCEED = "超过";
    String GENERAL_MSG = "当前【name】监测值【%s】，%s规则值【%s】";
    String TRIGGER_MSG = "当前【name】触发，请注意！";
    String DEVICE_MSG = "当前%s的%s已%s";

    String OPEN = "开启";
    String CLOSE = "关闭";

    /**
     * 报警提示模式编码
     **/
    String SYSTEM_ALARM_PROMPT = "1";

    /**
     * UPS
     */
    String UPS_ALARM_STATUS = "1";
    String UPS_NORMAL_STATUS = "0";
    /**
     * 设备类型编码-除湿机
     */
    String DEHUMIDIFIER_TYPE_CODE = "12";
    /**
     * 设备类型编码-探照大灯
     */
    String SEARCHLIGHT_TYPE_CODE = "9";
    /**
     * 三色灯--红灯闪
     */
    String FLASHING_RED_CODE = "01";
    /**
     * 三色灯--黄灯闪
     */
    String FLASHING_YELLOW_CODE = "02";

    /************************************************设备所需参数常量**/
    /**
     * 设备新增
     **/
    String INSERT = "insert";
    /**
     * 设备修改
     **/
    String UPDATE = "update";
    /**
     * 设备删除
     **/
    String DELETE = "delete";

    /**
     * 设备类型
     */
    String rfidType = "11"; //RFID
    String temHumType = "8";//温湿度
    String dynEnv = "5";//动环设备


    /***********************************************设备预警相关常量**/
    /**
     * 遮挡报警
     * */
    String HIDE_ALARM = "hideAlarm";
    /**
     * 红色紧急求助报警
     */
    String RED_EMERGENCE_CALL_HELP = "redEmergenceCallHelp";
    /**
     * 红色紧急求助报警恢复
     */
    String RED_EMERGENCE_CALL_HELP_RESET = "redEmergenceCallHelpReset";
    /**
     * 人脸比对结果，人脸比对回返回设备code以及识别到的人员userId，如果userId为空，表示未在人脸库中匹配到数据，即是陌生人
     */
    String FACE_MATCH = "faceMatch";
    /**
     * 火点检测报警
     */
    String FIRE_DETECTION = "fireDetection";
    /**
     * 烟雾检测报警
     */
    String SMOG_DETECTION = "smogDetection";
    /**
     * 烟火报警
     */
    String FIREWORK_DETECTION = "fireworkDetection";

    /**
     * 烟火报警统一一个名称与前端对齐
     */
    String FIREWORK = "firework";
    /**
     * 人脸侦测报警信息
     */
    String FACE_DETECTION = "faceDetection";
    /**
     * 紧急求助报警
     */
    String EMERGENCE_CALL_HELP = "emergenceCallHelp";
    /**
     * 紧急求助报警恢复
     */
    String EMERGENCE_CALL_HELP_RESET = "emergenceCallHelpReset";
    /**
     * 远程开门
     */
    String OPEN_DOOR_REMOTE = "openDoorRemote";
    /**
     * 正常开门
     */
    String OPEN_DOOR = "openDoor";
    /**
     * 门异常打开
     */
    String ABNORMAL_OPEN_DOOR = "abnormalOpenDoor";
    /**
     * 正常关门
     */
    String CLOSE_DOOR = "closeDoor";
    /**
     * 指纹比对通过
     */
    String FINGERPRINT_COMPARISON_SUCC = "fingerprintComparisonSucc";
    /**
     * 指纹比对失败
     */
    String FINGERPRINT_COMPARISON_FAIL = "fingerprintComparisonFail";
    /**
     * 人脸认证通过
     */
    String FACE_COMPARISON_SUCC = "faceComparisonSucc";
    /**
     * 人脸认证失败
     */
    String FACE_COMPARISON_FAIL = "faceComparisonFail";
    /**
     * 人脸识别失败
     */
    String FACE_RECOGNITION_FAIL = "faceRecognitionFail";
    /**
     * 区域入侵
     **/
    String REGIONAL_INVASION = "regionalInvasion";
    /**
     * 人员聚集
     **/
    String PERSONNEL_GATHERING = "personnelGathering";
    /**
     * 越界侦测
     **/
    String CROSS_BORDER_DETECTION = "crossBorderDetection";
    /**
     * 徘徊侦测
     **/
    String WANDERING_DETECTION = "wanderingDetection";
    /**
     * 门禁识别错误次数
     **/
    String RECOGNITION_ERROR_TIMES = "recognitionErrorTimes";
    String EVENT_TYPE_CHINESE_NAME_RLRZSB = "人脸认证失败";
    String EVENT_TYPE_CHINESE_NAME_RLSBSB = "人脸识别失败";
    String EVENT_TYPE_CHINESE_NAME_ZWBDSB = "指纹比对失败";
    String EVENT_TYPE_CHINESE_NAME_ZWBDTG = "指纹比对通过";
    String EVENT_TYPE_CHINESE_NAME_RLRZTG = "人脸认证通过";
    /**
     * 报警器触发
     **/
    String TRIGGER = "trigger";
    /**
     * 温度
     **/
    String TEM = "tem";
    /**
     * 湿度
     **/
    String HUM = "hum";
    /**
     * 打开密码错误次数
     **/
    String PWD_ERROR_TIMES = "pwdErrorTimes";
    /**
     * 钥匙离位时长
     **/
    String KEY_LEAVE_TIME = "keyLeaveTime";
    /**
     * 门打开时长
     **/
    String DOOR_OPEN_TIME = "doorOpenTime";
    /**
     * 启动时长
     **/
    String START_UPTIME = "startupTime";
    /**
     * 烟感
     **/
    String SMOKE = "smoke";

    /**
     * 电子围栏
     */
    String ELEC_FENCE = "fence";
    String ELEC_FENCE_CHINESE_NAME = "电子围栏报警";

    /**
     * 库室相关事件【1.库室墙体振动 2.库室红外线】
     */
    String ROOM_INFRARED = "roomInfrared";
    String ROOM_INFRARED_CHINESE_NAME = "库室红外线报警";
    String WALL_VIBRATION = "wallVibration";
    String WALL_VIBRATION_CHINESE_NAME = "库室墙体振动报警";

    /****************************************************************************************************/
    /************************通知类广播**********************************/

    /**
     * 语音编码:02
     * 涉及事件:
     * 1.摄像头(聚集，越界，入侵，徘徊) (报警等级三级)
     * 2.一键报警器（ 报警等级三级）
     * 4.电子围栏报警（报警等级三级）
     */
    String CAMERA_NOTICE_CONTENT = "有人员靠近，请注意";
    String CAMERA_NOTICE_CONTENT_CODE = "02";
    /**
     * 语音编码:03  涉及事件:摄像头(火情)
     */
    String CAMERA_NOTICE_FIREWORK_CONTENT = "全体注意，弹药库区发生火情";
    String CAMERA_NOTICE_FIREWORK_CONTENT_CODE = "03";
    /**
     * 语音编码:04
     * 涉及事件:
     * 3.管理主机一键报警器（报警等级三级）
     */
    String SECURITY_NOTICE_CONTENT = "弹药库区发生最高等级安全事件";
    String SECURITY_NOTICE_CONTENT_CODE = "04";
    /**
     * 库室相关事件【1.库室墙体振动 2.库室红外线】
     */
    String WAREHOUSE_EVENT_CONTENT = "有人员进入，请注意";


    /************************警戒类广播**********************************/

    /**
     * 语音编码:01 [最近的警戒广播设备发送内容]
     * 涉及事件:
     * 1.摄像头(聚集，越界，入侵，徘徊) (报警等级三级)
     * 4.电子围栏报警（报警等级三级）
     */
    String CAMERA_WARN_CONTENT = "你已进入警戒区域，请迅速离开";
    String CAMERA_WARN_CONTENT_CODE = "01";


    /****************************************************************************************************/

    /*************************************大屏websocket推送类型******************************************/
    /**
     * 枪械物资进出信息websocket
     */
    String FIRE_WEBSOCKET = "fireWebsocket";
    /**
     * 车辆进出信息websocket推送
     */
    String CAR_WEBSOCKET = "carWebsocket";
    String CAR_WEBSOCKET_DEVICETYPE = "6";
    String FIRE_WEBSOCKET_DEVICETYPE = "2";
    /**
     * 人员进出信息webSocket推送
     */
    String USER_WEBSOCKET = "userWebsocket";
    /**
     * 环境websocket推送
     */
    String ENVIRONMENT_WEBSOCKET = "environmentWebsocket";

    /**
     * 系统级别预警类型
     */
    String SYSTEM_ALARM_TYPE = "1";
    /**
     * 提示级别预警
     */
    String PROMPT_ALARM_TYPE = "2";

    /**
     * 大屏-轮播推送名称
     */
    String WEB_SOCKET_ROTATION_SCREEN_NAME = "screenRotation";

    /**
     * 大屏-设备状态推送名称
     */
    String WEB_SOCKET_DEVICE_SCREEN_NAME = "screenDeviceStatus";

    /**
     * 大屏-报警处理完成推送名称
     */
    String WEB_SOCKET_ALARM_HANDLE_SCREEN_NAME = "screenALarmHandle";

    /**
     * 首页-器材借调推送名称
     */
    String WEB_SOCKET_EQUIP_SECONDMENT_NAME = "equipSecondment";

    /**
     * 大屏-报警处理完成推送名称
     */
    String WEB_SOCKET_ALARM_24HNOPERSON_NAME = "roomNoPerson";

    /**
     * 报警处理完成主题
     */
    String ALARM_HANDLE_TOPIC = "alarmHandleTopic";
    String ALARM_HANDLE_GROUP = "alarmHandleGroup";


    /********************************************rfid http协议**********************************************************/

    String RFID_PUBLISH_CODE = "20001";

    String RFID_PING_CODE = "20003";

    String RFID_TIME_SYNC_CODE = "20002";

    /**物资盘点缓存key*/
    String MATERIAL_CHECK_ROOM_KEY = "com.smart";

    /***视频设备类型编码**/
    String VIDEO_DEVICE_TYPE_OPTION_CODE = "0";

    CharSequence UPS_NULL_VALE = "-";
}
