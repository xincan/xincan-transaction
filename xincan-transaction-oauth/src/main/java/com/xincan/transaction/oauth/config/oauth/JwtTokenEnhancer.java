package com.xincan.transaction.oauth.config.oauth;

import com.alibaba.fastjson.JSON;
import com.xincan.transaction.oauth.server.entity.Tenant;
import com.xincan.transaction.oauth.server.entity.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * jwt token增强器
 * token中加入租户信息
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();
        // 用户对应租户名称
        List<String> tenantNames = user.getTenants().stream().map(Tenant::getTenantName).collect(Collectors.toList());
        // 注意添加的额外信息，不能和已有的json对象中的key重名
        additionalInfo.put("tenant", tenantNames);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
