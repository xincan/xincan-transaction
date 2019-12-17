package com.xincan.transaction.oauth.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录传参实体
 */
@ApiModel(value = "OAuthParam", description = "用户登录传参实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuthParam {

    @ApiModelProperty(value="Basic Auth clientId", dataType = "String", required = true, example = "xincan-transaction-oauth")
    @NotBlank(message = "Basic Auth clientId不能为空")
    private String clientId;

    @ApiModelProperty(value="Basic Auth clientSecret", dataType = "String", required = true, example = "123456")
    @NotBlank(message = "Basic Auth clientSecret不能为空")
    private String clientSecret;

    @ApiModelProperty(value="授权类型", dataType = "String", required = true, example = "password")
    @NotBlank(message = "授权类型不能为空")
    private String grantType;

    @ApiModelProperty(value="授权范围", dataType = "String", required = true, example = "server")
    @NotBlank(message = "授权范围不能为空")
    private String scope;

    @ApiModelProperty(value="用户名", dataType = "String", required = true, example = "user1")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value="密码", dataType = "String", required = true, example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

}
