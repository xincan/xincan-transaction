package com.xincan.transaction.oauth.server.controller;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xincan.transaction.oauth.server.entity.OAuthParam;
import com.xincan.transaction.oauth.server.service.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Slf4j
@Api(tags = {"用户接口"}, description = "UserController")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;


    @ApiOperation(value="用户登录",httpMethod="POST",notes="用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="header_username",value="Basic Auth用户名",defaultValue = "xincan-transaction-oauth",required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name="header_password",value="Basic Auth密码",defaultValue = "123456",required = true, dataType = "String",paramType = "header"),

            @ApiImplicitParam(name="grant_type",value="授权类型",defaultValue = "password",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="scope",value="授权范围",defaultValue = "server",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="username",value="用户名",defaultValue = "user1",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="password",value="密码",defaultValue = "123456",required = true, dataType = "String",paramType = "query"),
    })
    @PostMapping("/login")
    public Mono login(@ApiParam(hidden = true) HttpServletRequest request,
                                                   @ApiParam(hidden = true) @RequestParam Map<String, String> parameters) {
        OAuthParam oAuthParam = OAuthParam.builder()
                .headerUserName(request.getHeader("header_username"))
                .headerPassword(request.getHeader("header_password"))
                .grantType(parameters.get("grant_type"))
                .scope(parameters.get("scope"))
                .username(parameters.get("username"))
                .password(parameters.get("password"))
                .build();
        OAuth2AccessToken token = userService.userLogin(oAuthParam).getBody();
        return Mono.just(token);
    }

    @ApiOperation(value="用户退出",httpMethod="POST",notes="用户退出")
    @ApiImplicitParams({
    })
    @PostMapping("/logout")
    public ResultObject logout(@ApiParam(hidden = true)Principal principal) {
        try {
            if (principal != null) {
                userService.userLogout((OAuth2Authentication) principal);
                return ResultResponse.success("退出成功", "");
            }
        }
        catch (Exception e) {
            log.error("", e);
        }
        return ResultResponse.error("退出失败");
    }

}
