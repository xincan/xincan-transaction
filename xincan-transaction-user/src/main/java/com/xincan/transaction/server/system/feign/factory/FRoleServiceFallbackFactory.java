package com.xincan.transaction.server.system.feign.factory;

import com.xincan.transaction.server.system.feign.IFOrderService;
import com.xincan.transaction.server.system.feign.IFRoleService;
import com.xincan.transaction.server.system.feign.fallback.FOrderServiceFallback;
import com.xincan.transaction.server.system.feign.fallback.FRoleServiceFallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FRoleServiceFallbackFactory implements FallbackFactory<IFRoleService> {

    @Autowired
    private FRoleServiceFallback fRoleServiceFallback;

    @Override
    public IFRoleService create(Throwable throwable) {
        log.error("请求失败", throwable);
        return fRoleServiceFallback;
    }
}
