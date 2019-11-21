package com.xincan.transaction.server.system.feign;

import com.alibaba.fastjson.JSONObject;
import com.xincan.transaction.server.system.feign.factory.FRoleServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "xincan-transaction-role", fallbackFactory = FRoleServiceFallbackFactory.class
)
@Component("roleService")
public interface IFRoleService {

    @PostMapping("/role/testFeignInsertRole")
    JSONObject testFeignInsertRole();

}
