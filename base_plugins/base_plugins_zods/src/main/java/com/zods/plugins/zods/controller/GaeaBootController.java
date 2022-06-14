package com.zods.plugins.zods.controller;
import java.util.List;

import com.zods.plugins.zods.bean.ResponseBean;
import com.zods.plugins.zods.init.InitRequestUrlMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GaeaBootController {
    @Autowired
    private InitRequestUrlMappings initRequestUrlMappings;

    public GaeaBootController() {
    }

    @GetMapping({"/gaea/boot/requestInfos/{scanAnnotation}"})
    public ResponseBean getRequestInfos(@PathVariable("scanAnnotation") Integer scanAnnotation) {
        List<InitRequestUrlMappings.RequestInfo> requestInfos = this.initRequestUrlMappings.getRequestInfos(scanAnnotation);
        return ResponseBean.builder().data(requestInfos).build();
    }
}
