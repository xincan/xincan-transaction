package com.xincan.transaction.server.system.feign.fallback;

import com.alibaba.fastjson.JSONObject;
import com.xincan.transaction.server.system.feign.IFRoleService;
import org.springframework.stereotype.Component;

@Component
public class FRoleServiceFallback implements IFRoleService {

    @Override
    public JSONObject testFeignInsertRole() {
        JSONObject result = new JSONObject();
        result.put("code",10000);
        result.put("count", 0);
        result.put("data", 0);
        result.put("msg","数据请求失败FRoleServiceFallback");
        return result;
    }
}
