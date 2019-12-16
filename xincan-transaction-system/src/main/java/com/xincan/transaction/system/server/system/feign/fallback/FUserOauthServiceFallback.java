package com.xincan.transaction.system.server.system.feign.fallback;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.alibaba.fastjson.JSONObject;
import com.xincan.transaction.system.server.system.feign.IFUserOauthService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: TODO
 * @className: UserRoleServiceFactory
 * @date: 2019/11/7 10:20
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Component
public class FUserOauthServiceFallback implements IFUserOauthService {

    @Override
    public OAuth2AccessToken userLogin(String authorization, Map<String, String> parameters) {
        return null;
    }

    @Override
    public ResultObject logout() {
        return ResultResponse.error("退出登录失败");
    }
}
