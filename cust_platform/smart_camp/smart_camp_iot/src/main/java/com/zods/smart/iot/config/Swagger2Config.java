package com.zods.smart.iot.config;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description Swagger配置
 * @createDate 2022-06-24
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
    @Value("${swagger.host}")
    private String swaggerHost;

    // 定义分隔符
    private static final String SPLITOR = ";";

    @Bean
    public Docket createRestApi() {
        ParameterBuilder accessHeadPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        //header中的ticket参数非必填，传空也可以
        accessHeadPar.name("Authorization").required(false).description("jwt access token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        //根据每个方法名也知道当前方法在设置什么参数
        pars.add(accessHeadPar.build());
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage("com.zods.smart.iot"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
        if(StringUtils.isNotBlank(swaggerHost)){
            docket.host(swaggerHost);
        }
        return docket;
    }


    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(SPLITOR)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
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
