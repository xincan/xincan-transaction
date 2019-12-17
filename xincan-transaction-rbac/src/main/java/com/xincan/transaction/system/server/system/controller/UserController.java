package com.xincan.transaction.system.server.system.controller;


import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.system.server.system.entity.user.OAuthParam;
import com.xincan.transaction.system.server.system.entity.user.User;
import com.xincan.transaction.system.server.system.service.IUserService;
import com.xincan.transaction.system.server.system.utils.TokenUtils;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;
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
            @ApiImplicitParam(name="header_username",value="Basic Auth用户名",defaultValue = "xincan-transaction-system",required = true, dataType = "String",paramType = "header"),
            @ApiImplicitParam(name="header_password",value="Basic Auth密码",defaultValue = "123456",required = true, dataType = "String",paramType = "header"),

            @ApiImplicitParam(name="grant_type",value="授权类型",defaultValue = "password",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="scope",value="授权范围",defaultValue = "server",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="username",value="用户名",defaultValue = "user1",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="password",value="密码",defaultValue = "123456",required = true, dataType = "String",paramType = "query"),
    })
    @PostMapping(value = "/login")
    public ResultObject login(@ApiParam(hidden = true) HttpServletRequest request,
                      @ApiParam(hidden = true) @RequestParam Map<String, String> parameters) {
        OAuthParam oAuthParam = OAuthParam.builder()
                .headerUserName(request.getHeader("header_username"))
                .headerPassword(request.getHeader("header_password"))
                .grantType(parameters.get("grant_type"))
                .scope(parameters.get("scope"))
                .username(parameters.get("username"))
                .password(parameters.get("password"))
                .build();
        OAuth2AccessToken token = userService.userLogin(oAuthParam);
        if (token != null) {
            return ResultResponse.success("登陆成功", token);
        }
        return ResultResponse.error("登陆失败", null);
    }

    @ApiOperation(value="用户退出",httpMethod="POST",notes="用户退出")
    @ApiImplicitParams({
    })
    @PostMapping("/logout")
    public ResultObject logout(@ApiParam(hidden = true)Principal principal) {
        return userService.userLogout();
    }

    @ApiOperation(value="用户登录信息",httpMethod="GET",notes="用户登录信息,未登录为空")
    @ApiImplicitParams({
    })
    @GetMapping(value = "/principal")
    public Mono principal(@ApiParam(hidden = true) Principal principal) {
        return Mono.justOrEmpty(principal);
    }

    @ApiOperation(value="解析用户token",httpMethod="GET",notes="解析用户登录token")
    @ApiImplicitParams({
    })
    @GetMapping(value = "/token")
    public Mono decodeToken(@ApiParam(hidden = true) Principal principal) throws Exception {
        return Mono.justOrEmpty(TokenUtils.decodeToken(principal));
    }

    @ApiOperation(value="用户注册",httpMethod="POST",notes="注册新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value="用户名", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="password",value="密码", required = true, dataType = "String",paramType = "query"),
    })
    @PostMapping(value = "/registry")
    public ResultObject createUser(@ApiParam(hidden = true) Principal principal,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultResponse.error("用户名或密码不能为空", null);
        }
        return userService.createUser(username, password);
    }

    @ApiOperation(value="查询用户信息",httpMethod="GET",notes="查询分页用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="limit",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", defaultValue="create_time", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortType",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),

    })
    @GetMapping("/page")
    public ResultObject pageMenu(@ApiParam(hidden = true) Principal principal,
                                 @ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        Page<User> pageInfo = userService.getUserPage(map);
        if(pageInfo != null){
            log.info("{}", "查询用户信息成功");
            return ResultResponse.success("查询用户信息成功", pageInfo);
        }
        log.info("查询用户信息失败");
        return ResultResponse.error("查询用户信息失败");
    }

    @ApiOperation(value="更新用户",httpMethod="PATCH",notes="更新用户")
    @DynamicParameters(name = "User",properties = {
            @DynamicParameter(name="id",value="用户ID", dataTypeClass=String.class),
            @DynamicParameter(name="username",value="用户名", dataTypeClass=String.class),
            @DynamicParameter(name="age",value="年龄", dataTypeClass=String.class),
            @DynamicParameter(name="sex",value="性别", dataTypeClass=String.class),
            @DynamicParameter(name="phone",value="手机号", dataTypeClass=String.class),
            @DynamicParameter(name="email",value="邮箱", dataTypeClass=Integer.class),
            @DynamicParameter(name="isAdmin",value="是否管理员", dataTypeClass=Integer.class),
            @DynamicParameter(name="areaId",value="地区ID", dataTypeClass=String.class),
            @DynamicParameter(name="organizationId",value="所属组织", dataTypeClass=String.class),
    })
    @PatchMapping()
    public ResultObject update(@ApiParam(hidden = true) Principal principal,
                               @RequestBody Map<String,String> map) {
        User user = User.builder()
                .id(map.get("id"))
                .username(map.get("username"))
                .age(map.get("age"))
                .sex(Integer.valueOf(map.get("sex")))
                .phone(map.get("phone"))
                .email(map.get("email"))
                .isAdmin(Integer.valueOf(map.get("isAdmin")))
                .areaId(map.get("areaId"))
                .organizationId(map.get("organizationId"))
                .build();
        int res = userService.updateUser(user);
        if(res > 0){
            log.info("{}", "修改用户信息成功");
            return ResultResponse.success("修改用户信息成功", res);
        }
        log.info("修改用户信息失败");
        return ResultResponse.error("修改用户信息失败");
    }

    @ApiOperation(value="更新用户",httpMethod="PATCH",notes="更新用户")
    @DynamicParameters(name = "User",properties = {
            @DynamicParameter(name="id",value="用户ID", dataTypeClass=String.class),
            @DynamicParameter(name="username",value="用户名", dataTypeClass=String.class),
            @DynamicParameter(name="age",value="年龄", dataTypeClass=String.class),
            @DynamicParameter(name="sex",value="性别", dataTypeClass=String.class),
            @DynamicParameter(name="phone",value="手机号", dataTypeClass=String.class),
            @DynamicParameter(name="email",value="邮箱", dataTypeClass=Integer.class),
            @DynamicParameter(name="isAdmin",value="是否管理员", dataTypeClass=Integer.class),
            @DynamicParameter(name="areaId",value="地区ID", dataTypeClass=String.class),
            @DynamicParameter(name="organizationId",value="所属组织", dataTypeClass=String.class),
    })
    @PutMapping()
    public ResultObject save(@ApiParam(hidden = true) Principal principal,
                               @RequestBody Map<String,String> map) {
        User user = User.builder()
                .username(map.get("username"))
                .age(map.get("age"))
                .sex(Integer.valueOf(map.get("sex")))
                .phone(map.get("phone"))
                .email(map.get("email"))
                .isAdmin(Integer.valueOf(map.get("isAdmin")))
                .areaId(map.get("areaId"))
                .organizationId(map.get("organizationId"))
                .build();
        int res = userService.saveUser(user);
        if(res > 0){
            log.info("{}", "添加用户信息成功");
            return ResultResponse.success("添加用户信息成功", res);
        }
        log.info("添加用户信息失败");
        return ResultResponse.error("添加用户信息失败");
    }

    @ApiOperation(value="删除用户",httpMethod="POST",notes="删除用户")
    @DynamicParameters(name = "User",properties = {
            @DynamicParameter(name="id",value="用户ID(逗号分隔)", dataTypeClass=String.class),
    })
    @PostMapping("/delete")
    public ResultObject delete(@ApiParam(hidden = true) Principal principal,
                               @RequestBody Map<String,String> map) {
        String id = map.get("id");
        if (StringUtils.isEmpty(id)) {
            return ResultResponse.error("用户ID为空");
        }
        int res = userService.deleteUserById(id);
        if (res > 0) {
            return ResultResponse.success("删除用户成功");
        }
        return ResultResponse.error("删除用户失败");
    }

}
