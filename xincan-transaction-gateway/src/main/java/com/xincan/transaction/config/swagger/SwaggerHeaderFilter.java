package com.xincan.transaction.config.swagger;

import com.xincan.transaction.config.swagger.SwaggerProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * @description: 添加swagger请求头过滤器
 *
 * 【Spring Boot版本超过2.0.6的应该可以跳过这一步，最新源码也更新了。Spring修复了bug给我们添加上了这个Header】
 * 另外，我发现在路由为admin/test/{a}/{b}，在swagger会显示为test/{a}/{b}，缺少了admin这个路由节点。
 * 断点源码时发现在Swagger中会根据X-Forwarded-Prefix这个Header来获取BasePath，将它添加至接口路径与host中间，
 * 这样才能正常做接口测试，而Gateway在做转发的时候并没有这个Header添加进Request，
 * 所以发生接口调试的404错误。解决思路是在Gateway里加一个过滤器来添加这个header。
 *
 * @className: SwaggerHeaderFilter
 * @date: 2019/11/18 13:34
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {

    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (!StringUtils.endsWithIgnoreCase(path, SwaggerProvider.API_URI)) {
                return chain.filter(exchange);
            }
            String basePath = path.substring(0, path.lastIndexOf(SwaggerProvider.API_URI));
            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }
}
