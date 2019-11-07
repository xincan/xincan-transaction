package com.xincan.transaction.server.system.feign.fallback;

import com.alibaba.fastjson.JSONObject;
import com.xincan.transaction.server.system.feign.IFUserRoleService;
import org.springframework.stereotype.Component;

/**
 * @description: TODO
 * @className: UserRoleServiceFactory
 * @date: 2019/11/7 10:20
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Component
public class FUserRoleServiceFallback implements IFUserRoleService {

    @Override
    public JSONObject findRoleByUserId(String userId) {
        JSONObject result = new JSONObject();
        result.put("code",10000);
        result.put("count", 0);
        result.put("data", userId);
        result.put("msg","数据请求失败FUserRoleServiceFallbackFactory");
        return result;
    }

}
