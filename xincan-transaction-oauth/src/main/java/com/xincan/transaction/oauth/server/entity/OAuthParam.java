package com.xincan.transaction.oauth.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 用户登录传参实体
 */
@Data
@AllArgsConstructor
@Builder
public class OAuthParam {

    private String headerUserName;

    private String headerPassword;

    private String grantType;

    private String scope;

    private String username;

    private String password;

}
