package com.xincan.transaction.server.system.feign;

import com.alibaba.fastjson.JSONObject;
import com.xincan.transaction.server.system.feign.factory.FOrderServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "xincan-transaction-order", fallbackFactory = FOrderServiceFallbackFactory.class
)
@Component("orderService")
public interface IFOrderService {

    @PostMapping("/order/testFeignInsertOrder")
    JSONObject testFeignInsertOrder();

}
