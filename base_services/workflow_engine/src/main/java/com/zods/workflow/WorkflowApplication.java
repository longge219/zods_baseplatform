package com.zods.workflow;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author jianglong
 * @version 1.0
 * @Description 工作流引擎后台服务
 * @createDate 2022-07-28
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.zods.workflow.modules.*.**.dao"})
public class WorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class, args);
    }

}
