package com.xincan.transaction.server.system.feign;

import com.alibaba.fastjson.JSONObject;
import com.xincan.transaction.server.system.feign.factory.FUserRoleServiceFallbackFactory;
import com.xincan.transaction.server.system.feign.fallback.FUserRoleServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "XINCAN-TRANSACTION-ROLE"
//        ,fallback = FUserRoleServiceFallback.class
        ,fallbackFactory = FUserRoleServiceFallbackFactory.class
)
public interface IFUserRoleService {

    @PostMapping("/role/user/id")
    JSONObject findRoleByUserId(@RequestParam("userId") String userId, @RequestParam("second") Integer second);
}
