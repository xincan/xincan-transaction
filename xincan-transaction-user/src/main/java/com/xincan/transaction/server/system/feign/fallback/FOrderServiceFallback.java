package com.xincan.transaction.server.system.feign.fallback;

import com.alibaba.fastjson.JSONObject;
import com.xincan.transaction.server.system.feign.IFOrderService;
import org.springframework.stereotype.Component;

@Component
public class FOrderServiceFallback implements IFOrderService {

    @Override
    public JSONObject testFeignInsertOrder() {
        JSONObject result = new JSONObject();
        result.put("code",10000);
        result.put("count", 0);
        result.put("data", 0);
        result.put("msg","数据请求失败FOrderServiceFallback");
        return result;
    }
}
