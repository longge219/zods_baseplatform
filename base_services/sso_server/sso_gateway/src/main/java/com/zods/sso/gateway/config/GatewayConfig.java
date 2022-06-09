package com.zods.sso.gateway.config;
import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.List;
/**
 * @author jianglong
 * @version 1.0
 * @Description 网关配置
 * @createDate 2022-06-08
 */
@Configuration
public class GatewayConfig {


    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                         ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    public static String NACOS_DATA_ID;

    public static String NACOS_GROUP_ID;

    @Value("${gateway.dynamicRoute.dataId}")
    public void setNacosDataId(String dataId) {
        NACOS_DATA_ID = dataId;
    }

    @Value("${gateway.dynamicRoute.group}")
    public void setNacosGroupId(String group) {
        NACOS_GROUP_ID = group;
    }


    /**-远程地址键解析器*/
    @Bean(value = "remoteAddrKeyResolver")
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**Gateway获取Nacos的权重，在配置加入下面配置*/
    @Bean
    @Scope(value = "prototype")
    public IRule loadBalanceRule() {
        return new NacosRule();
    }

    /**配置SentinelGatewayFilter*/
    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

}
