package com.zods.mqtt.sever.register.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${swagger.service.url}")
    private String seriviceUrl;
    @Value("${swagger.service.title}")
    private String seriviceTitle;
    @Value("${swagger.service.des}")
    private String seriviceDes;

    @Bean
    public Docket createRestApi() {
        List<Parameter> parameterList = new ArrayList<>();
        Parameter headerToken = new ParameterBuilder()
                .name("appkey").modelRef(new ModelRef("string"))
                .description("项目key")
                .parameterType("header").required(false).build();
        Parameter registerCode = new ParameterBuilder()
                .name("registerCode").modelRef(new ModelRef("string"))
                .description("请求体token传递,格式'token'")
                .parameterType("query").required(false).build();
        parameterList.add(headerToken);
        parameterList.add(registerCode);
        return new Docket(DocumentationType.SWAGGER_2)
                .host("iot.zods.com/iotHttp")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zods.iot.http.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(seriviceTitle)
                .description(seriviceDes)
                .termsOfServiceUrl(seriviceUrl)
                .version("1.0")
                .build();
    }

}
