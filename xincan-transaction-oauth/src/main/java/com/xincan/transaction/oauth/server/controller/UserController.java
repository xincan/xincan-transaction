package com.xincan.transaction.oauth.server.controller;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xincan.transaction.oauth.server.entity.OAuthParam;
import com.xincan.transaction.oauth.server.service.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/login")
    public Mono login(OAuthParam oAuthParam) {
        ResponseEntity<OAuth2AccessToken> token = userService.userLogin(oAuthParam);
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
