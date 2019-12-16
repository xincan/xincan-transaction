package com.xincan.transaction.system.server.system.feign;

import cn.com.hatech.common.data.result.ResultObject;
import com.alibaba.fastjson.JSONObject;
import com.xincan.transaction.system.server.system.feign.factory.FUserOauthServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "xincan-transaction-oauth"
        ,fallbackFactory = FUserOauthServiceFallbackFactory.class
)
@Component("userOauthService")
public interface IFUserOauthService {

    /**
     * 用户登录
     * @param authorization
     * @param parameters
     * @return
     */
    @PostMapping("/oauth/token")
    OAuth2AccessToken userLogin(@RequestHeader("Authorization") String authorization,
                                @RequestParam Map<String, String> parameters);

    /**
     * 用户退出
     * @return
     */
    @PostMapping("/user/logout")
    ResultObject logout();


}
