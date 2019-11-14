package com.xincan.transaction.oauth.server.controller;

import com.xincan.transaction.oauth.server.entity.User;
import com.xincan.transaction.oauth.server.service.IUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"用户接口"}, description = "UserController")
@RestController
@RequestMapping("/hatech")
public class UserController {

    @Autowired
    IUserRoleService userRoleService;

    @ApiOperation(value="用户登录信息",httpMethod="GET",notes="用户登录信息,未登录为空")
    @ApiImplicitParams({
    })
    @GetMapping(value = "/principal")
    public Mono getUser(Principal principal) {
        return Mono.justOrEmpty(principal);
    }

    @ApiOperation(value="用户注册",httpMethod="POST",notes="注册新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value="用户名", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="password",value="密码", required = true, dataType = "String",paramType = "query"),
    })
    @PostMapping(value = "/registry")
    public ResponseEntity<Mono> createUser(Principal principal, @RequestParam("username") String username, @RequestParam("password") String password) {
        if (principal == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("error", "用户未认证");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Mono.just(res));
        }
        String cryptPassword = (new BCryptPasswordEncoder()).encode(password);
        User user = new User(username, cryptPassword);
        Integer res = userRoleService.saveUser(user);
        if (res == 1) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Mono.just("user registry succeed"));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just("user registry failed"));
    }

}
