package com.zods.sso.gateway.filter;//package com.orieange.gateway.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//
///**
// * @author Wangchao
// * @version 1.0
// * @Description
// * @createDate 2021/1/14 16:38
// */
//@Slf4j
//@Component
//public class GlobalResponseBodyEncodeFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        if (request.getMethod() != HttpMethod.DELETE && request.getMethod() != HttpMethod.PUT && request.getMethod() != HttpMethod.PATCH) {
//            // 如果不是post（新增）、put（全量修改）、patch（部分字段修改）操作，则直接放行
//            return chain.filter(exchange);
//        }
//
//        ServerHttpResponse originalResponse = exchange.getResponse();
//        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
//        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (body instanceof Flux) {
//                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
//                    return super.writeWith(fluxBody.map(dataBuffer -> {
//                        byte[] content = new byte[dataBuffer.readableByteCount()];
//                        dataBuffer.read(content);
//                        //释放掉内存
//                        DataBufferUtils.release(dataBuffer);
//                        // 响应体
//                        String str = new String(content, Charset.forName("UTF-8"));
//                        System.err.println("str:" + str);
//                        return bufferFactory.wrap(str.getBytes(StandardCharsets.UTF_8));
//                    }));
//                }
//                String aa = body.toString();
//                System.err.println("aa" + aa);
//                return super.writeWith(body);
//            }
//        };
//        System.err.println("re:" + decoratedResponse.toString());
//        return chain.filter(exchange.mutate().response(decoratedResponse).build());
//    }
//
//    @Override
//    public int getOrder() {
//        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
//    }
//
//}
//
