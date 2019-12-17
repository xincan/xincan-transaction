package com.xincan.transaction.system.server.system.feign.factory;

import com.xincan.transaction.system.server.system.feign.IFUserOauthService;
import com.xincan.transaction.system.server.system.feign.fallback.FUserOauthServiceFallback;
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
public class FUserOauthServiceFallbackFactory implements FallbackFactory<IFUserOauthService> {

    private final FUserOauthServiceFallback userOauthServiceFallback;

    public FUserOauthServiceFallbackFactory(FUserOauthServiceFallback userOauthServiceFallback) {
        this.userOauthServiceFallback = userOauthServiceFallback;
    }

    @Override
    public IFUserOauthService create(Throwable throwable) {
        return userOauthServiceFallback;
    }
}
