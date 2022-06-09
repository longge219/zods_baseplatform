package com.zods.sso.gateway.handler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.DefaultBlockRequestHandler;
import com.zods.sso.gateway.common.HandlerExceptionEnums;
import com.zods.sso.gateway.common.ResponseModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
/**
 * @author jianglong
 * @version 1.0
 * @Description Sentinel 流控、降级 统一 自定义异常处理
 * @createDate 2022-06-08
 */
@Component
@Qualifier("DefaultBlockRequestHandler")
public class McloudExceptionHandlingWebHandler extends DefaultBlockRequestHandler {

    public McloudExceptionHandlingWebHandler() {
        super();
    }

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
        if (acceptsHtml(exchange)) {
            return htmlErrorResponse(ex);
        }
        // JSON result by default.
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(fromObject(buildErrorResult(ex)));
    }

    /**
     * Reference from {@code DefaultErrorWebExceptionHandler} of Spring Boot.
     */
    private boolean acceptsHtml(ServerWebExchange exchange) {
        try {
            List<MediaType> acceptedMediaTypes = exchange.getRequest().getHeaders().getAccept();
            acceptedMediaTypes.remove(MediaType.ALL);
            MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);
            return acceptedMediaTypes.stream()
                    .anyMatch(MediaType.TEXT_HTML::isCompatibleWith);
        } catch (InvalidMediaTypeException ex) {
            return false;
        }
    }

    private Mono<ServerResponse> htmlErrorResponse(Throwable ex) {
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.TEXT_PLAIN)
                .syncBody("访问太频繁了，请稍后再试!" + ex.getClass().getSimpleName());
    }

    private ResponseModel buildErrorResult(Throwable ex) {
        return new ResponseModel(HttpStatus.TOO_MANY_REQUESTS.value(), "访问太频繁了，请稍后再试!");
    }

}
