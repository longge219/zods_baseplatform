package com.zods.largescreen.modules.layer.service.impl;
import com.zods.largescreen.common.curd.mapper.GaeaBaseMapper;
import com.zods.largescreen.modules.layer.dao.LayerMapper;
import com.zods.largescreen.modules.layer.dao.entity.Layer;
import com.zods.largescreen.modules.layer.service.LayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @desc Layer 图层服务接口实现
 * @author jianglong
 * @date 2022-06-24
 **/
@Service
@Slf4j
public class LayerServiceImpl implements LayerService {

    @Autowired
    private LayerMapper layerMapper;

    @Override
    public GaeaBaseMapper<Layer> getMapper() {
        return layerMapper;
    }

}
