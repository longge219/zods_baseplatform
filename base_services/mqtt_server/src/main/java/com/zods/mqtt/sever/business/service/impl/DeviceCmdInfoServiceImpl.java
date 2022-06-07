package com.zods.mqtt.sever.business.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.mqtt.sever.business.config.MinioConfig;
import com.zods.mqtt.sever.business.constant.TopicConstant;
import com.zods.mqtt.sever.business.service.DeviceCmdInfoService;
import com.zods.mqtt.sever.protocol.client.AppMqttClient;
import com.zods.mqtt.sever.protocol.common.util.ByteBufUtil;
import com.zods.mqtt.sever.protocol.common.util.HexStringUtils;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 下发命令记录信息表 服务实现类
 * Liu Sir
 * </p>
 */
@Service
@Slf4j
public class DeviceCmdInfoServiceImpl implements DeviceCmdInfoService {

    @Resource
    private AppMqttClient consoleMqttClient;

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    protected ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 存储下次需要升级的分片位置、大小、总大小、存储路径,md5值,已经升级固件片数
     */
    public ConcurrentHashMap<String, JSONObject> upgradeParameterMap = new ConcurrentHashMap();

    /**
     * 固件mqtt升级分片最大值
     */
    private static final Integer FIRMWARE_SIZE = 65535;

    private static final Long SIZE = 95280L;

    private static final String OBJECT_NAME = "APP - 一体机2021-V1.0.1";

    private static final String MD5 = "c332f635ebf54bceec604d497843d6e4";


    /**
     * 下发命令
     *
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public void sendCmd(JSONObject jsonObject) throws Exception {
        String body = jsonObject.getString("body");
        String deviceId = jsonObject.getString("deviceId");
        executorService.execute(() -> {
            consoleMqttClient.publishMessage(TopicConstant.DOWNWARDS_CMD_TOPIC(deviceId), body.getBytes(), MqttQoS.AT_LEAST_ONCE.value());
        });
    }

    /**
     * 平台下发升级固件主题
     */
    @Override
    public void firmWareUpgrade(JSONObject jsonObject) throws Exception {
        String body = jsonObject.getString("body");
        String deviceId = jsonObject.getString("deviceId");
        if (!StringUtils.isEmpty(body)) {
            JSONObject object = new JSONObject();
            object.put("attachSize", jsonObject.getLong("attachSize"));
            object.put("md5", jsonObject.getString("md5"));
            object.put("attachName", jsonObject.getString("attachName"));
            upgradeParameterMap.put(deviceId, object);
            executorService.execute(() -> {
                consoleMqttClient.publishMessage(TopicConstant.UPGRADE_CMD_TOPIC(deviceId), body.getBytes(), MqttQoS.AT_LEAST_ONCE.value());
            });
        }
    }

    /**
     * 断点发送指定大小固件到设备
     * 获取指定长度固件流
     * 设备在接收到升级指令后，主动响应所支持的传输数据包大小范围（最大值和最小值中间用“,”分隔）
     * $cmd=supportsize&range=512,1536&msgid=2abr65892
     */
    @Override
    public void firmWareSupportSize(JSONObject jsonObject) throws Exception {
        String body = jsonObject.getString("body");
        String deviceId = jsonObject.getString("deviceId");
        if (!StringUtils.isEmpty(body) && !StringUtils.isEmpty(deviceId)) {
            // 1.初始化升级参数
            initializeUpgrade(deviceId, calculateSize(body));
            // 2.首次升级
            log.info("1111111111111111");
            publishFirmware(deviceId);
        }
    }

    /**
     * 分段下载并升级
     */
    @Override
    public void publishFirmware(String deviceId) throws Exception {
        JSONObject jsonObject = upgradeParameterMap.get(deviceId);
        if (jsonObject != null && jsonObject.getInteger("number") <= jsonObject.getLong("size")) {
            log.info("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 正在升级--> 当前升级固件分片信息:{} _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _", jsonObject.toJSONString());
            GetObjectArgs build = GetObjectArgs.builder().bucket(minioConfig.getBucketName()).object(jsonObject.getString("attachName")).offset(jsonObject.getLong("offset")).length(jsonObject.getLong("length")).build();
            InputStream stream = minioClient.getObject(build);
            ByteBuf bytes = buildProtocolBytes(StreamUtils.copyToByteArray(stream), jsonObject);
            log.info("hahahahahahahahahahahahaha");
            if (bytes != null && bytes.readableBytes() != 0) {
                executorService.execute(() -> {
                    consoleMqttClient.publishMessage(TopicConstant.FIRMWARE_CMD_TOPIC(deviceId), ByteBufUtil.copyByteBuf(bytes), MqttQoS.AT_LEAST_ONCE.value());
                });
            }
        } else {
            log.warn(".................................当前设备【{}】不存在，无法升级.....................", deviceId);
        }
    }

    /**
     * 统计下次升级的参数
     */
    @Override
    public void countNextUpgrade(String deviceId) {
        log.info("333333333");
        JSONObject jsonObject = upgradeParameterMap.get(deviceId);
        Long offset = jsonObject.getLong("offset");
        Long length = jsonObject.getLong("length");
        jsonObject.put("number", jsonObject.getInteger("number") + 1);
        jsonObject.put("offset", offset + length);
        log.info("countNextUpgrade--->number:{}offset:{}" , jsonObject.getInteger("number"),jsonObject.get("offset"));
        if (jsonObject.getInteger("number").equals(jsonObject.getInteger("size"))) {
            jsonObject.put("length", jsonObject.getLong("attachSize") - (offset + length));
        }
    }

    /**
     * 删除指定设备历史升级参数
     */
    @Override
    public void removePastUpgradeData(String deviceId) {
        try {
            if (upgradeParameterMap.containsKey(deviceId)) {
                upgradeParameterMap.remove(deviceId);
                log.info("............................删除设备【{}】历史升级记录..........................", deviceId);
            }
        } catch (Exception e) {
            log.error("设备【" + deviceId + "】删除历史mqtt升级记录失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 获取指定设备当前正在升级的数据参数
     */
    @Override
    public JSONObject getUpgradeData(String deviceId) {
        return upgradeParameterMap.get(deviceId);
    }

    /**
     * 初始化升级参数包括分片下载索引、大小、分片包数、存储路径,md5值,已经升级固件片数
     */
    private void initializeUpgrade(String deviceId, Double ceil) throws Exception {
        JSONObject object = upgradeParameterMap.get(deviceId);
        Long attachSize = object.getLong("attachSize");
        long length = attachSize - ceil > 0d ? ceil.longValue() : attachSize;
        object.put("offset", 0L);
        object.put("length", length);
        object.put("size", (attachSize / length) + 1);
        object.put("number", 1);
        upgradeParameterMap.put(deviceId, object);
    }


    /**
     * 计算固件分片大小
     */
    private Double calculateSize(String body) throws Exception {
        String range = body.substring(0, body.indexOf("&msgid")).substring(body.substring(0, body.indexOf("&msgid")).indexOf("range=") + 6);
        Double min = Double.valueOf(range.split(",")[0]);
        Double max = Double.valueOf(range.split(",")[1]);
        Double length = (min + max) / 2;
        return length > FIRMWARE_SIZE ? FIRMWARE_SIZE : length;
    }

    /**
     * 构建固件升级协议指定的字节格式
     */
    private ByteBuf buildProtocolBytes(byte[] bytes, JSONObject jsonObject) throws Exception {

        log.info("iiiiiiiiiiiiiiii:{}",jsonObject.toJSONString());
        if (bytes != null && bytes.length != 0) {
            ByteBuf buffer = Unpooled.buffer(6);
            // 1.当前升级包是第几个
            buffer.writeShort(jsonObject.getInteger("number") - 1);
            // 2.当前升级包长度
            buffer.writeShort(jsonObject.getInteger("length"));
            // 3.总包数
            buffer.writeShort(jsonObject.getInteger("size"));
            // 4.包文件
            log.info("----------当前包序号："+(jsonObject.getInteger("number") - 1)+"--------当前包长度："+(jsonObject.getInteger("length"))+"-------当前包实际长度----------" + bytes.length +"  ，当前包计算长度" + jsonObject.getInteger("length"));
            buffer.writeBytes(bytes);
            if (jsonObject.getInteger("number").equals(jsonObject.getInteger("size"))) {
                log.info("-------------------------------追加md5信息到最后一个固件分片----------------------------------------");
                // 5.最后一个包添加验证信息
                buffer.writeByte(16);
                buffer.writeBytes(HexStringUtils.decodeHex(jsonObject.getString("md5").toCharArray()));
            }
            return buffer;
        }
        return null;
}

}
