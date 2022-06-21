package com.zods.largescreen.common.controller;
import com.zods.largescreen.common.bean.ResponseBean;
import com.zods.largescreen.common.init.InitRequestUrlMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
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
