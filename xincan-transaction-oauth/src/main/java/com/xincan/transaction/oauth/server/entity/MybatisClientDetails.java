package com.xincan.transaction.oauth.server.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.util.*;

@Data
@TableName("oauth_client_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MybatisClientDetails implements ClientDetails {

    @TableId("client_id")
    private String clientId;

    @TableField("resource_ids")
    private String resourceIds;

    @TableField("client_secret")
    private String clientSecret;

    @TableField("scope")
    private String scope;

    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    @TableField("authorities")
    private String authorities;

    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    @TableField("additional_information")
    private String additionalInformation;

    @TableField("autoapprove")
    private String autoapprove;

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if (StringUtils.isEmpty(webServerRedirectUri)) {
            return null;
        }
        return new HashSet<>(Arrays.asList(webServerRedirectUri.split(",")));
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return !StringUtils.isEmpty(scope) && Boolean.parseBoolean(scope);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        if (StringUtils.isEmpty(additionalInformation)) {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(additionalInformation);
            return JSON.toJavaObject(jsonObject, Map.class);
        }
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (!StringUtils.isEmpty(authorities)) {
            Collection<GrantedAuthority> collection = new HashSet<>();
            Arrays.stream(authorities.split(",")).forEach(t->{
                collection.add(new SimpleGrantedAuthority(t));
            });
            return collection;
        }
        return null;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return StringUtils.isEmpty(authorizedGrantTypes)?null:StringUtils.commaDelimitedListToSet(authorizedGrantTypes);
    }

    @Override
    public Set<String> getScope() {
        return StringUtils.isEmpty(scope)?null:StringUtils.commaDelimitedListToSet(scope);
    }

    @Override
    public Set<String> getResourceIds() {
        return StringUtils.isEmpty(resourceIds)?null:StringUtils.commaDelimitedListToSet(resourceIds);
    }

}
