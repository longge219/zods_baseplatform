package com.zods.largescreen.runner;
import com.zods.largescreen.modules.accessauthority.service.AccessAuthorityService;
import com.zods.largescreen.modules.dict.service.GaeaDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
/**
 * @author jianglong
 * @version 1.0
 * @Description 启动加载器
 * @createDate 2022-06-20
 */
public class ApplicationInitRunner implements ApplicationRunner {

    @Autowired
    private GaeaDictService gaeaDictService;

    @Autowired
    private AccessAuthorityService accessAuthorityService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //1、数据字典刷新
        // gaeaDictService.refreshCache(null);

        //2. 建立url权限拦截体系
        accessAuthorityService.scanGaeaSecurityAuthorities();
    }
}
