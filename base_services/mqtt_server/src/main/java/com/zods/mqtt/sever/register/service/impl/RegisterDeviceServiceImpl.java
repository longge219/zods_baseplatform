package com.zods.mqtt.sever.register.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zods.mqtt.sever.business.response.HandlerExceptionEnums;
import com.zods.mqtt.sever.business.response.ResponseModel;
import com.zods.mqtt.sever.protocol.common.util.UUIDUtils;
import com.zods.mqtt.sever.register.dao.DeviceMonitorTypeDao;
import com.zods.mqtt.sever.register.dao.MonitorContentDao;
import com.zods.mqtt.sever.register.dao.MonitorMethodDao;
import com.zods.mqtt.sever.register.dao.RegisterDeviceDao;
import com.zods.mqtt.sever.register.entity.device.Device;
import com.zods.mqtt.sever.register.entity.device.DeviceMonitorType;
import com.zods.mqtt.sever.register.entity.device.MonitorContent;
import com.zods.mqtt.sever.register.entity.device.MonitorMethod;
import com.zods.mqtt.sever.register.entity.deviceLogin.DeviceLogin;
import com.zods.mqtt.sever.register.entity.deviceRegister.BaseDevice;
import com.zods.mqtt.sever.register.entity.deviceRegister.DeviceRegister;
import com.zods.mqtt.sever.register.entity.deviceRegister.MonitorType;
import com.zods.mqtt.sever.register.service.RegisterDeviceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RegisterDeviceServiceImpl implements RegisterDeviceService {

    private final RegisterDeviceDao registerDeviceDao;

    private MonitorMethodDao monitorMethodDao;

    private MonitorContentDao monitorContentDao;

    private DeviceMonitorTypeDao monitorTypeDao;

    private static final String APP_KEY = "orieange";

    private static final String REGISTER_CODE = "orieange";

    /**
     * 设备注册
     *
     * @param deviceRegister
     * @param registerCode
     * @param appKey
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel registerDevice(DeviceRegister deviceRegister, String registerCode, String appKey) throws Exception {
        log.info("DeviceRegister:{}", deviceRegister);
        // 1.参数校验
        ResponseModel<Device> responseModel = parameterVerify(appKey, registerCode, deviceRegister.getSn());
        if (responseModel.getResult() == 0) {
            // 2.注册
            Device device = responseModel.getData();
            BeanUtils.copyProperties(deviceRegister, device);
            device.setDeviceId(UUIDUtils.getUUIDNumbers(8));
            device.setDeviceApiKey(UUIDUtils.getUUIDChars(28));
            registerDeviceDao.updateById(device);
            // 3.同步监测方法
            registerMonitorType(deviceRegister.getMonitorTypes(),device.getId());
            return new ResponseModel<>(HandlerExceptionEnums.DEVICE_REGISTER_SUCCESS, DeviceLogin.builder().deviceId(device.getDeviceId()).deviceApiKey(device.getDeviceApiKey()).build());
        }
        return responseModel;
    }

    /**同步监测方法*/
    private void registerMonitorType(List<MonitorType> typeList, Long deviceId) throws Exception {
        if (!CollectionUtils.isEmpty(typeList)) {
            QueryWrapper qw = new QueryWrapper();
            qw.eq("device_code",deviceId);
            monitorTypeDao.delete(qw);
            typeList.forEach(monitorType -> {
                QueryWrapper wrapper = new QueryWrapper();
                String sid = monitorType.getSid();
                String type = monitorType.getType();
                String code = type.split("_")[0];
                String method = type.split("_")[1];
                wrapper.eq("code", code);
                wrapper.select("id,code");
                MonitorContent content = monitorContentDao.selectOne(wrapper);
                if(content != null){
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("code",method);
                    queryWrapper.eq("content_id",content.getId());
                    queryWrapper.select("id,code,content_id");
                    MonitorMethod monitorMethod = monitorMethodDao.selectOne(queryWrapper);
                    if(monitorMethod != null){
                        DeviceMonitorType deviceMonitorType = new DeviceMonitorType();
                        deviceMonitorType.setDeviceCode(Long.toString(deviceId));
                        deviceMonitorType.setMonitorTypeCode(type);
                        deviceMonitorType.setSn(Integer.valueOf(sid));
                        monitorTypeDao.insert(deviceMonitorType);
                    }
                }

            });
        }
    }

    /**
     * 查询设备登录参数
     *
     * @param baseDevice
     * @param registerCode
     * @param appKey
     */
    @Override
    public ResponseModel queryDevice(BaseDevice baseDevice, String registerCode, String appKey) throws Exception {
        // 1.参数校验
        ResponseModel<Device> responseModel = parameterVerify(appKey, registerCode, baseDevice.getSn());
        if (responseModel.getResult() == 0) {
            Device device = responseModel.getData();
            return new ResponseModel(HandlerExceptionEnums.DEVICE_REGISTER_SUCCESS, DeviceLogin.builder().deviceId(device.getDeviceId()).deviceApiKey(device.getDeviceApiKey()).build());
        }
        return responseModel;
    }

    /**
     * 参数校验
     *
     * @param appKey
     * @param registerCode
     * @param sn
     * @return
     * @throws Exception
     */
    private ResponseModel<Device> parameterVerify(String appKey, String registerCode, String sn) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>appkey【{}】，registerCode【{}】,sn【{}】<<<<<<<<<<<<<<<<<<<<<<<", appKey, registerCode, sn);
        if (StringUtils.isEmpty(appKey) || !APP_KEY.equals(appKey)) {
            return new ResponseModel(HandlerExceptionEnums.DEVICE_APP_KEY_UNAUTHORIZED);
        }
        if (StringUtils.isEmpty(registerCode) || !REGISTER_CODE.equals(registerCode)) {
            return new ResponseModel(HandlerExceptionEnums.DEVICE_REGISTER_CODE_UNAUTHORIZED);
        }
        if (StringUtils.isEmpty(sn)) {
            return new ResponseModel(HandlerExceptionEnums.DEVICE_SN_UNAUTHORIZED);
        }
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sn", sn);
        List<Device> devices = registerDeviceDao.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(devices)) {
            return new ResponseModel(HandlerExceptionEnums.DEVICE_SN_UNAUTHORIZED);
        }
        return new ResponseModel(HandlerExceptionEnums.DEVICE_REGISTER_SUCCESS, devices.get(0));
    }
}

