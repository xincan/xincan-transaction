package com.xincan.transaction.server.system.feign.factory;

import com.xincan.transaction.server.system.feign.IFUserRoleService;
import com.xincan.transaction.server.system.feign.fallback.FUserRoleServiceFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description: TODO
 * @className: UserRoleServiceFactory
 * @date: 2019/11/7 10:20
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Component
public class FUserRoleServiceFallbackFactory implements FallbackFactory<IFUserRoleService> {

    private final FUserRoleServiceFallback userRoleServiceFallback;

    public FUserRoleServiceFallbackFactory(FUserRoleServiceFallback userRoleServiceFallback) {
        this.userRoleServiceFallback = userRoleServiceFallback;
    }

    @Override
    public IFUserRoleService create(Throwable throwable) {
        throwable.printStackTrace();
        return userRoleServiceFallback;
    }
}
