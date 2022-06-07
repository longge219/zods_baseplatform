package com.zods.mqtt.sever.business.constant;
import java.io.Serializable;
/**
 * @author jianglong
 * @description 设备下行命令
 * @create 2019-09-29
 **/
public class EquipCmdConstant implements Serializable {

    /**
     * 获取设备终端时间
     */
    public static final String REQ_TIME = "$cmd=reqtime";

    /**
     * 校正设备终端时间
     */
    public static final String SET_TIME = "$cmd=settime";

    /**
     * 设置传感器时间相关参数
     */
    public static final String SET_SENSOR_TIME = "$cmd=setsensortime";

    /**
     * 获取传感器时间相关参数
     */
    public static final String GET_SENSOR_TIME = "$cmd=reqsensortime";

    /**
     * 设置传感器属性相关参数
     */
    public static final String SET_SENSOR_ATTR = "$cmd=setsensorattr";

    /**
     * 获取传感器属性相关参数
     */
    public static final String GET_SENSOR_ATTR = "$cmd=getsensorattr";

    /**
     * 获取接入传感器类型
     */
    public static final String GET_SENSOR_TYPE = "$cmd=getsensorID";

    /**
     * 传感器遥测
     */
    public static final String SAMPLE = "$cmd=sample";

    /**
     * 设置工作模式
     */
    public static final String SET_WORK_MODE = "$cmd=setworkmode";

    /**
     * 获取工作模式
     */
    public static final String GET_WORK_MODE = "$cmd=getworkmode";

    /**
     * 设置平台工作参数
     */
    public static final String SET_PLATFORM_PARAM = "$cmd=setplatform";

    /**
     * getplatform&device_id=00001&api_key=h41e4m4rvblr0b4rmnv3p9lv9svo87ub&project_id=001&mqtt_addr=171.221.254.254&mqtt_port=6678&auth_info=XCBD_2022003131&http_addr=api.heclouds.com&http_port=80&controlNo=1
     * 获取平台工作参数
     */
    public static final String GET_PLATFORM_PARAM = "$cmd=getplatform";

    /**
     * 获取设备状态
     */
    public static final String GET_STATUS = "$cmd=getstatus";

    /**
     * http固件升级
     */
    public static final String FIRMWARE_HTTP_UPGRADE = "update";

    /**
     * mqtt固件升级
     */
    public static final String FIRMWARE_MQTT_UPGRADE = "$cmd=upgrade";

    /**
     * 重启设备
     */
    public static final String REBOOT = "$cmd=reboot";

    /**
     * 下发预警喇叭播报内容
     */
    public static final String BROAD_CAST = "$cmd=broadcast";

    /**
     * 地质灾害气象预警
     */
    public static final String METEOROLOGICALEARLYWARNING = "$cmd=meteorologicalearlywarning";

    /**
     * 获取设备指令集版本
     */
    public static final String GET_CMD_VERSION = "$cmd=getcmdversion";

    /**
     * 设置设备链路
     */
    public static final String SET_LINK = "$cmd=setlink";

    /**
     * 设置设备传感器配置
     */
    public static final String SET_SENSOR = "$cmd=setsensor";

    /**
     * 设置设备传感器初始值
     */
    public static final String SET_INITIAL = "$cmd=setInitial";

    /**
     * 获取设备基础属性
     */
    public static final String BASIC = "$cmd=basic";

    /**
     * 设备休眠设置
     */
    public static final String SLEEP = "$cmd=sleep";

    //........................................................................填充命令参数..........................................................................*/

    /**
     * 获取设备终端时间
     * 指令格式：$cmd=reqtime
     * 指令返回结果格式：$cmd=reqtime&time=YYYY-MM-DD HH:mm:ss
     * 如：$cmd=reqtime&time=2019-05-01 13:00:00
     */
    public static final String REQ_TIME(String apikey, String msgid) {
        return "$cmd=reqtime&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 校正设备终端时间
     * 设备端接收到时间校正指令后需完成一次自动校时操作.指令格式：$cmd=settime&server=ntpserver
     * 如：$cmd=settime&server=ntp.ntsc.ac.cn
     * 指令返回结果格式：终端时间校正成功：$cmd=settime&result=succ  终端时间校正失败：$cmd=settime&result=fail
     */
    public static final String SET_TIME(String server, String apikey, String msgid) {
        return "$cmd=settime&server=" + server + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 获取传感器时间相关参数
     * 获取传感器采集间隔、上传间隔、加报间隔三个参数（传感器时间相关参数说明见附录表E.1）时需要指定监测类型编码及传感器ID序号（比如：L2_LF_1），具体说明见附录B。
     * 指令格式：$cmd=reqsensortime&sensor_id=value
     * 指令响应格式：$cmd=reqsensortime&sensor_id=value&sample_intv=value&upload_intv=value&plus_intv=value
     */
    public static final String GET_SENSOR_TIME(String sensorId, String apikey, String msgid) {
        return "$cmd=reqsensortime&sensor_id=" + sensorId + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 设置传感器时间相关参数
     * 设置传感器采集间隔、上传间隔、加报间隔三个参数（传感器时间相关参数说明见附录表E.1）时需要指定监测类型编码及传感器ID序号（比如：L2_LF_1），具体说明见附录B。
     * 指令格式：$cmd=setsensortime&sensor_id=value&sample_intv=value&upload_intv=value&plus_intv=value
     * 指令响应格式：指令设置成功返回格式：$cmd=setsensortime&result=succ
     * 指令设置失败返回格式：$cmd=setsensortime&result=fail
     */
    public static final String SET_SENSOR_TIME(String sensorId, Integer sampleIntv, Integer uploadIntv, Integer plusIntv, String apikey, String msgid) {
        return "$cmd=setsensortime&sensor_id=" + sensorId + "&sample_intv=" + sampleIntv + "&upload_intv=" + uploadIntv + "&plus_intv=" + plusIntv + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 设置传感器属性相关参数
     * 设置传感器阈值、上限值、下限值三个参数（传感器属性参数说明见附录表E.2）时需要指定传感器ID加序号（比如：L2_LF_1），传感器ID具体说明见附录B。
     * 其中上下限值是指正常的数据范围，属性值可以为数字类型或字符串类型，数字类型表示单值类型传感器，字符串类型用来处理多值，每个值用逗号隔开，
     * 比如GNSS结果数据的阈值：”1,2,3”,X轴阈值是1,Y轴阈值2,Z轴是3。
     * 指令格式：$cmd=setsensorattr&sensor_id=value&threshold=value&upper_limit=value&lower_limit=value
     * 指令响应格式：设置传感器属性成功：$cmd=setsensorattr&result=succ
     * 设置传感器属性失败：$cmd=setsensorattr&result=fail
     */
    public static final String SET_SENSOR_ATTRIBUTE(String sensorId, String threshold, String upperLimit, String lowerLimit, String apikey, String msgid) {
        return "$cmd=setsensorattr&sensor_id=" + sensorId + "&threshold=" + threshold + "&upper_limit=" + upperLimit + "&lower_limit=" + lowerLimit + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 获取传感器属性相关参数
     * 获取传感器阈值、上限值、下限值三个参数（传感器属性参数说明见附录表E.2）时需要指定传感器ID加序号（比如：L2_LF_1），传感器ID具体说明见附录B，其中上下限值是指正常的数据范围。
     * 指令格式：$cmd=getsensorattr&sensor_id=value
     * 指令响应格式：$cmd=getsensorattr&sensor_id=value&threshold=value&upper_limit=value&lower_limit=value
     */
    public static final String GET_SENSOR_ATTRIBUTE(String sensorId, String apikey, String msgid) {
        return "$cmd=getsensorattr&sensor_id=" + sensorId + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 获取接入传感器类型
     * 获取接入传感器类型，返回所有安装的传感器类型id加序号（比如：L1_YL_1）。
     * 指令格式：$cmd=getsensorID
     * 指令响应格式：$cmd=getsensorID&sensor_id=value
     */
    public static final String GET_SENSOR_TYPE(String apikey, String msgid) {
        return "$cmd=getsensorID" + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 传感器遥测
     * 传感器实时数据采集，适用于平台对传感器数据实时采集的应用场景，返回监测类型编码、传感器序号、实时数据。
     * 指令格式：$cmd=sample
     * 指令响应格式：采用数据格式类型三上传，若接入多个传感器，返回多条数据流，
     * 如下：单条：$cmd=sample&datastreams={"L2_LF_1":"67.45"}
     * 多条：$cmd=sample&datastreams={"L2_LF_1":"34.56","L2_LF_2":"67.45","L2_LF_3":"12.2"}
     */
    public static final String SAMPLE(String apikey, String msgid) {
        return "$cmd=sample" + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 设置工作模式
     * 指令格式：$cmd=setworkmode&mode=value
     * 工作模式取值：a)0（正常模式）：设备进入正常的数据上报状态；b)1（节能模式）: 设备进入低功耗状态c)2（应急模式）:设备进入该模式后需立即上报数据并且进入数据加报状态
     * 比如：一体化裂缝监测站设备在正常模式下是持续采集，每2小时上报数据。进入应急模式后，设备需立即上报监测数据，并且进入每10分钟上报数据的加报状态；
     * 进入节能模式后，设备无需采集和上报数据，处于低功耗状态指令响应格式：
     * 设置工作模式成功：$cmd=setworkmode&result=succ
     * 设置工作模式失败:$cmd=setworkmode&result=fail
     */
    public static final String SET_WORK_MODE(String mode, String apikey, String msgid) {
        return "$cmd=setworkmode&mode=" + mode + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 获取工作模式
     * 指令格式：$cmd=getworkmode 工作模式取值，0:正常模式，1:节能模式，2:应急模式
     * 指令响应格式：设置工作模式成功：$cmd=getworkmode&mode=value
     * 设置工作模式失败:$cmd=getworkmode&mode=value
     */
    public static final String GET_WORK_MODE(String apikey, String msgid) {
        return "$cmd=getworkmode" + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 重启设备
     * 平台可以远程重启终端。指令格式：$cmd=reboot
     * 指令响应格式：成功：$cmd=reboot&result=succ 失败：$cmd=reboot&result=fai
     */
    public static final String REBOOT(String apikey, String msgid) {
        return "$cmd=reboot" + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * http固件升级
     */
    public static final String FIRMWARE_HTTP_UPGRADE(String url, String md5, String apikey, String msgid) {
        return "$cmd=update&url=" + url + "&md5=" + md5 + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * MQTT固件升级
     * 平台下发指令告知设备，设备根据固件信息通过平台提供的固件获取方式采取响应的操作。
     * 下发的信息中包括固件的MD5值和固件大小（单位：字节），固件获取完成之后，设备可根据提供的固件Md5值和文件大小信息检验文件的有效性，
     * 若无效设备需主动放弃此次固件升级。
     * 指令格式：$cmd=upgrade&md5=value&size=value
     */
    public static final String FIRMWARE_MQTT_UPGRADE(String size, String md5, String apikey, String msgid) {
        return "$cmd=upgrade&md5=" + md5 + "&size=" + size + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 设置平台相关参数
     */
    public static final String SET_PLATFORM_PARAMETER(String equipCode, String apiKey, String projectId, String mqttAddr, String mqttPort, String authInfo, String httpAddr, String httpPort, String regCode, String controlNo, String type, String apikey, String msgid) {
        return "$cmd=setplatform&apikey=" + apiKey + "&msgid=0e54286f91c9&controlNo=" + controlNo + "&type=" + type + "&device_id=" + equipCode + "&api_key=" + apiKey + "&project_id=" + projectId + "&mqtt_addr=" + mqttAddr + "&mqtt_port="
                + mqttPort + "&auth_info=" + authInfo + "&http_addr=" + httpAddr + "&reg_code=" + regCode + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 获取平台相关参数
     */
    public static final String GET_PLATFORM_PARAMETER(String controlNo, String apikey, String msgid) {
        return "$cmd=getplatform&controlNo=" + controlNo + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 获取设备状态
     * 用于下发查询命令给设备，设备主动返回当前设备状态，状态信息应包含供电电压、设备故障报告，并根据设备类型，宜包含温度、湿度、标准无线蜂窝网络信号或北斗信号等。
     * 指令格式：$cmd=getstatus
     * 指令响应格式：有效数据部分采用数据格式类型三上传：$cmd=getstatus&state={"ext_power_volt":24.04,"temp":42.00,"signal_4g":27.0,"sw_version":"1.0.1","4g_on":true}
     */
    public static final String GET_STATUS(String apikey, String msgid) {
        return "$cmd=getstatus" + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 下发预警喇叭播报内容
     * 预警喇叭具备平台远程下发文字转语音播报功能，包括播报遍数及内容，其中内容为GB2312码，具体参数说明见附录表E.3。
     * 指令格式：$cmd=broadcast&b_num=value&b_size=value&b_content=value
     */
    public static final String BROAD_CAST(String num, String value, String message, String apikey, String msgid) {
        return "$cmd=broadcast&b_num=" + num + "&b_size=" + value + "&b_content=" + message + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 地质灾害气象预警
     * 根据数据采集设备所安装的地理位置信息，平台每日定时主动下发一条该位置对应的区域未来 一定时长内（默认 24 小时）的地质灾害气象预警预报数据指令，指令内容主要包括气象预警的红、橙、黄等级、预警有效时长以及该区域经纬度范围，其中最大和最小的经纬度值之间用“,”隔开。数据采集设备接收到指令后可通过自身定位获取的经纬度与指令内容中经纬度范围进行核对，若在该区域中，则需根据气象预警等级及设备自身情况进行采样与上传频率等运行参数调整并回复响应成功，若不在该区域中需回复响应失败。
     * 指令格式：
     * $cmd=meteorologicalearlywarning&level=value&effective_time=value&lon_range=value&lat_r
     * ange=value
     * level 取值，0:红色预警，1:橙色预警，2:黄色预警
     * effective_time 单位为小时(h)
     * 如：
     * $cmd=meteorologicalearlywarninglevel&level=2&lon_range=114.40,114.5&lat_range
     * =30.48,30.50&effective_time=24
     * 指令响应格式 ,设置成功：
     * $cmd=meteorologicalearlywarning&result=succ
     * 指令响应格式，设置失败:
     * $cmd=meteorologicalearlywarning&result=fail
     */
    public static final String METEOROLOGICALEARLYWARNING(String level, String effectiveTime, String lonRange, String latRange, String apikey, String msgid) {
        return "$cmd=meteorologicalearlywarning&level=" + level + "&effective_time=" + effectiveTime + "&lon_range=" + lonRange + "&lat_range=" + latRange + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 获取设备指令集版本
     * 获取设备当前支持的指令集版本。
     * 指令格式：$cmd=getcmdversion
     * 指令响应格式：$cmd=getcmdversion&version=value如: $cmd=getcmdversion&version=1.1
     */
    public static final String GET_CMD_VERSION(String apikey, String msgid) {
        return "$cmd=getcmdversion" + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 平台下发链路配置指令，设备接收到链路配置后，自动通过下发指令中的信息链接平台。
     * address链路节点IP，port链路节点端口，index=-1为链路节点序号，index= -1时，设备默认新增新的链路，其他数字代表修改对应index链路，index不存在时返回平台错误；valid=1链路节点是否有效（1-启用2-停用），id设备ID,key节点APIKEY。
     * 指令格式：
     * $cmd=setlink&address=127.0.0.1&port=8080&index=1&valid=1&id=12345678&key=fnas5ji3fa2suifb89nfveri
     * 设置成功：
     * $cmd=setlink&result=succ&index=1&reason=succ
     * 成功定义：链路配置成功且链接平台成功。
     * index填入终端新增生成的index数值或者修改对应的index数值
     * 成功时reason固定值succ
     * 设置失败：
     * $cmd=setlink&result=fail&index=1&reason=”修改链路编号不存在”
     * 失败：当配置成功，链路链接不成功时也定为不成功
     * index填入终端新增生成的index数值或者修改对应的index数值
     * 失败时reason填写可抓取的原因(如：新增链路过多超过数值，修改链路编号不存在)
     */
    public static final String SET_LINK(String address, String port, Integer index, Integer valid, String deviceId, String deviceApiKey, String apikey, String msgid) {
        return "$cmd=setlink&address=" + address + "&port=" + port + "&index=" + index + "&valid=" + valid + "&id=" + deviceId + "&key=" + deviceApiKey + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 平台获取设备的配置链路信息。
     * 指令格式：
     * $cmd=getlinks
     * 指令响应格式：
     * $cmd=getlinks&links=[{
     * "index":"1",
     * "address":"192.168.1.1",
     * "port":"8080",
     * "valid":"1",  #1:启用，2:停用
     * "status":"1" #1:链接成功，2:链接失败
     * }，{
     * "index":"2",
     * "address":"192.168.1.2",
     * "port":"8081",
     * "valid":"1",#1:启用，2:停用
     * "status":"1" #1:链接成功，2:链接失败
     * } ]
     */
    public static final String GET_LINKS(String apikey, String msgid) {
        return "$cmd=getlinks&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 设置设备传感器配置
     * 该指令适用于传感器与设备连接后，远程配置设备对传感器的识别，平台下发的参数项有波特率，485串口寄存器地址，监测方法，通过设置项，设备可链接串口，读取寄存器地址数据，上传指定接入传感器的监测方法。
     * 设备传感器配置分为1.远程平台配置 2.本地串口工具配置 3.设备自检配置
     * 设备传感器识别逻辑为：优先级：1.远程平台配置 > 2.本地串口工具配置>3.设备自检配置
     * 设备识别若已人工配置参数则以人工配置参数为准，否在自检接口传感器进行自动配置。
     * 指令格式：
     * $cmd=setsensor&index=1&hardware=1&devidID=1&baudrate=9600&funCode=3&register=7&regNum=2&monitor=L1_YL
     * 设置成功：
     * $cmd=setsensor&result=succ
     * 设置失败：
     * $cmd=setsensor&result=fail
     */
    public static final String SET_SENSOR(String index, String hardware, String devidID, String baudrate, String funCode, String register, String regNum, String monitor, String apikey, String msgid) {
        return "$cmd=setsensor&index=" + index + "&hardware=" + hardware + "&devidID=" + devidID + "&baudrate=" + baudrate + "&funCode=" + funCode + "&register=" + register + "&regNum=" + regNum + "&monitor=" + monitor + "&apikey=" + apikey + "&msgid=" + msgid;
    }


    /**
     * 设置设备传感器初始值
     * 指令格式：
     * $cmd=setInitial&monitor=L1_LF_1&value=10
     * 指令响应格式,设置成功：
     * $cmd=setInitial&result=succ
     * 指令响应格式,设置失败：
     * $cmd=setInitial&result=fail
     */
    public static final String SET_INITIAL(String monitor, String value, String apikey, String msgid) {
        return "$cmd=setInitial&monitor=" + monitor + "&value=" + value + "&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 获取设备基础属性
     * 指令格式：
     * $cmd=basic
     * 指令响应格式：
     * $cmd=basic&datastreams={
     * "sn":"3880402",
     * "pn":"20210624",
     * "imei":"123456789",
     * "ccid":"123456789",
     * "iotid":"123456789",
     * "model":"XC-PLF01",
     * "lon":"104.5638",
     * "lat":"30.2564",
     * "type":"L1QJ_1,L3YL_2"，
     * "protocal":"1",
     * "communication":"0"
     * }
     */
    public static final String BASIC(String apikey, String msgid) {
        return "$cmd=getbasic&apikey=" + apikey + "&msgid=" + msgid;
    }

    /**
     * 设置设备进入休眠
     * 设备在未安装到现场的情况下可，设备唤醒模式链接平台后，可进行相关参数配置。配置完后为了节能省电，需要进入休眠模式，待现场安装后唤醒正式工作。
     * 指令格式：
     * $cmd=sleep
     * 设置成功：
     * $cmd=sleep&result=succ
     * 设置失败：
     * $cmd=sleep&result=fail
     */
    public static final String SLEEP(String apikey, String msgid) {
        return "$cmd=setsleep&apikey=" + apikey + "&msgid=" + msgid;
    }
}
