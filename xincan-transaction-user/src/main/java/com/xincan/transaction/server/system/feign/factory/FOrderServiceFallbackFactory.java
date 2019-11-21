package com.xincan.transaction.server.system.feign.factory;

import com.xincan.transaction.server.system.feign.IFOrderService;
import com.xincan.transaction.server.system.feign.fallback.FOrderServiceFallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FOrderServiceFallbackFactory implements FallbackFactory<IFOrderService> {

    @Autowired
    private FOrderServiceFallback fOrderServiceFallback;

    @Override
    public IFOrderService create(Throwable throwable) {
        log.error("请求失败", throwable);
        return fOrderServiceFallback;
    }
}
