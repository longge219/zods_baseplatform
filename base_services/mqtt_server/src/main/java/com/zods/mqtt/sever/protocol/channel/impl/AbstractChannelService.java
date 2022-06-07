package com.zods.mqtt.sever.protocol.channel.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zods.mqtt.sever.protocol.bean.MqttChannel;
import com.zods.mqtt.sever.protocol.bean.RetainMessage;
import com.zods.mqtt.sever.protocol.channel.BaseApi;
import com.zods.mqtt.sever.protocol.channel.MqttChannelService;
import com.zods.mqtt.sever.protocol.common.util.CacheMap;
import com.zods.mqtt.sever.protocol.scan.ScanRunnable;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jianglong
 * @description channel业务处理
 * @create 2019-09-09
 **/
public abstract class AbstractChannelService extends PublishApiSevice implements MqttChannelService, BaseApi {

    public AbstractChannelService(ScanRunnable scanRunnable) {
        super(scanRunnable);
    }

    //channel的登录状态属性
    protected AttributeKey<Boolean> _login = AttributeKey.valueOf("login");

    //channel的客户端唯一标识数据
    public static AttributeKey<String> _deviceId = AttributeKey.valueOf("deviceId");

    //主题消息分隔符
    protected static char SPLITOR = '/';

    //线程池线程管理
    protected ExecutorService executorService = Executors.newCachedThreadPool();

    //全局channel缓存
    public static ConcurrentHashMap<String, MqttChannel> mqttChannels = new ConcurrentHashMap<>();

    //缓存chnnel主题消息
    protected static CacheMap<String, MqttChannel> cacheMap = new CacheMap<>();

    //缓存chnnel保留消息
    protected static ConcurrentHashMap<String, ConcurrentLinkedQueue<RetainMessage>> retain = new ConcurrentHashMap<>();

    //主题消息关联的channel关联
    protected static Cache<String, Collection<MqttChannel>> mqttChannelCache = CacheBuilder.newBuilder().maximumSize(100).build();


    /**
     * 根据客户端全局ID查找channel
     */
    public MqttChannel getMqttChannel(String deviceId) {
        return mqttChannels.get(deviceId);

    }

    /**
     * 获取channelId
     */
    public String getDeviceId(Channel channel) {
        return channel.attr(_deviceId).get();
    }

    /**
     * 根据主题消息查找需要发布的channel集合
     */
    @FunctionalInterface
    interface TopicFilter {
        Collection<MqttChannel> filter(String topic);
    }

    protected Collection<MqttChannel> getChannels(String topic, TopicFilter topicFilter) {
        try {
            return mqttChannelCache.get(topic, () -> topicFilter.filter(topic));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 删除channel的主题消息
     */
    protected boolean deleteChannel(String topic, MqttChannel mqttChannel) {
        mqttChannelCache.invalidate(topic);
        return cacheMap.delete(getTopic(topic), mqttChannel);
    }

    /**
     * 添加channel的主题消息
     */
    protected boolean addChannel(String topic, MqttChannel mqttChannel) {
        Collection<MqttChannel> channels = mqttChannelCache.getIfPresent(topic);
        if (!CollectionUtils.isEmpty(channels)) {
            boolean subscribed = true;
            for (MqttChannel channel : channels) {
                if (channel.getDeviceId().equals(mqttChannel.getDeviceId())) {
                    subscribed = false;
                }
            }
            if (subscribed) {
                channels.add(mqttChannel);
            }
        } else {
            channels = Collections.singletonList(mqttChannel);
        }
        mqttChannelCache.put(topic, channels);
        return cacheMap.putData(getTopic(topic), mqttChannel);
    }

    /**
     * 获取主题消息
     */
    protected String[] getTopic(String topic) {
        return StringUtils.split(topic, SPLITOR);
    }
}
