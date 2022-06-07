package com.zods.mqtt.sever.protocol.channel;
import com.zods.mqtt.sever.protocol.bean.MqttChannel;
import com.zods.mqtt.sever.protocol.bean.WillMeaasge;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import java.util.List;
import java.util.Set;
/**
 * @author jianglong
 * @description MQTT控制报文channel处理
 * @create 2019-09-09
 **/
public interface MqttChannelService {

    /**
     * 登录失败
     */
    public void loginFail(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage, MqttConnectReturnCode mqttConnectReturnCode);

    /**
     * 成功登陆
     */
    public void loginSuccess(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage) throws Exception;

    /**
     * 成功断开连接
     */
    public void closeSuccess(Channel channel, boolean isDisconnect);

    /**
     * 成功订阅消息
     */
    public void suscribeSuccess(String deviceId, Set<String> topics);

    /**
     * 成功取消订阅
     */
    public void unsubscribe(String deviceId, List<String> topics1);

    /**
     * 成功发布消息
     */
    public void publishSuccess(Channel channel, MqttPublishMessage mqttPublishMessage);

    /**
     * 成功qos2发布释放
     */
    public void doPubrel(Channel channel, int mqttMessage);

    /**
     * 成功qos2发布收到
     */
    public void doPubrec(Channel channel, int mqttMessage);

    /**
     * 发送遗嘱消息
     */
    public void sendWillMsg(WillMeaasge willMeaasge);

    /**
     * 根据客户端全局ID查找channel
     */
    public MqttChannel getMqttChannel(String deviceId);

    /**
     * 获取channelId
     */
    public String getDeviceId(Channel channel);

    /**
     * 断电后再登录
     */
    public void repeatLogin(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage) throws Exception;

}
