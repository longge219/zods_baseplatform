package com.zods.sso.auth.config;
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
/**
 * @author Wangchao
 * @version 1.0
 * @Description Oauth2.0资源服务配置
 * @createDate 2020/12/12 14:45
 */
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
        ParameterBuilder accessHeadPar = new ParameterBuilder();
        accessHeadPar.name("Authorization").description("jwt access token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameterList.add(accessHeadPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zods.sso.auth.controller"))
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
