package com.zods.largescreen.modules.datasettransform.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.datasettransform.controller.dto.DataSetTransformDto;
import com.zods.largescreen.modules.datasettransform.dao.DataSetTransformMapper;
import com.zods.largescreen.modules.datasettransform.dao.entity.DataSetTransform;
import com.zods.largescreen.modules.datasettransform.service.DataSetTransformService;
import com.zods.largescreen.modules.datasettransform.service.TransformStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author jianglong
 * @version 1.0
 * @Description DataSetTransform 数据集数据转换服务实现
 * @createDate 2022-06-22
 */
@Service
public class DataSetTransformServiceImpl implements DataSetTransformService, InitializingBean, ApplicationContextAware {

    private final Map<String, TransformStrategy> queryServiceImplMap = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    private DataSetTransformMapper dataSetTransformMapper;

    @Override
    public GaeaBaseMapper<DataSetTransform> getMapper() {
      return dataSetTransformMapper;
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, TransformStrategy> beanMap = applicationContext.getBeansOfType(TransformStrategy.class);
        //遍历该接口的所有实现，将其放入map中
        for (TransformStrategy serviceImpl : beanMap.values()) {
            queryServiceImplMap.put(serviceImpl.type(), serviceImpl);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**数据转换*/
    @Override
    public List<JSONObject> transform(List<DataSetTransformDto> dataSetTransformDtoList, List<JSONObject> data) {
        if (dataSetTransformDtoList == null || dataSetTransformDtoList.size() <= 0) {
            return data;
        }
        for (DataSetTransformDto dataSetTransformDto : dataSetTransformDtoList) {
            data = getTarget(dataSetTransformDto.getTransformType()).transform(dataSetTransformDto, data);
        }
        return data;
    }


    private TransformStrategy getTarget(String type) {
        return queryServiceImplMap.get(type);
    }
}
