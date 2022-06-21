package com.zods.largescreen.base;
import com.zods.largescreen.common.bean.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
@RestController
public class HealthController {

    @GetMapping("health")
    public ResponseBean health() {
        return ResponseBean.builder().build();
    }

}
