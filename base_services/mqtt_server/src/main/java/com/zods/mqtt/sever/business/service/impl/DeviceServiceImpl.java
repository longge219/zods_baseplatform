package com.zods.mqtt.sever.business.service.impl;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zods.mqtt.sever.business.exception.BusinessExceptionUtils;
import com.zods.mqtt.sever.business.service.DeviceService;
import com.zods.mqtt.sever.business.dao.DeviceDao;
import com.zods.mqtt.sever.business.entity.DeviceLogin;
import com.zods.mqtt.sever.business.response.ResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {


    private final DeviceDao deviceDao;

    @Transactional(readOnly = true)
    @Override
    public ResponseModel<String> connectAuth(DeviceLogin deviceLogin) throws Exception {
        log.info("设备登录参数为{}", JSON.toJSONString(deviceLogin));
        BusinessExceptionUtils.isNullOrBlank(deviceLogin.getDeviceId(), "该设备deviceId不能为空");
        BusinessExceptionUtils.isNullOrBlank(deviceLogin.getDeviceApiKey(), "该设备deviceApiKey不能为空");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("device_id", deviceLogin.getDeviceId());
        queryWrapper.eq("device_key", deviceLogin.getDeviceApiKey());
//        Device device = deviceDao.selectOne(queryWrapper);
//        if (device == null) {
//            throw new Exception("没有找到deviceId为" + deviceLogin.getDeviceId() + "的设备");
//        }
//        String deviceApiKey = device.getDeviceApiKey();
//        if (StringUtils.isEmpty(deviceApiKey)) {
//            throw new Exception("该设备deviceApiKey为空,请重新输入28位长度的deviceApiKey");
//        }
//        if (!deviceLogin.getDeviceApiKey().equals(deviceApiKey)) {
//            throw new Exception("该设备apiKey不正确");
//        }
        return new ResponseModel("设备连接到mqtt服务器成功");
    }
}

