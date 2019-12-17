package com.xincan.transaction.server.user.controller;

import com.xincan.transaction.result.ResponseResult;
import com.xincan.transaction.result.ResultObject;
import com.xincan.transaction.server.user.entity.User;
import com.xincan.transaction.server.user.service.IUserService;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.system.server.user.controller
 * @ClassName: UserController
 * @author: wangmingshuai
 * @Description: 用户信息控制层
 * @Date: 2019/12/17 14:41
 * @Version: 1.0
 */
@Slf4j
@Api(tags = {"用户信息接口"}, description = "UserController")
@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController (IUserService userService) {
        this.userService = userService;
    }

    /**
     * 根据账号查询用户信息
     * @param username
     * @return
     */
    @ApiOperation(value="根据账号查询用户信息",httpMethod="POST",notes="根据账号查询用户信息")
    @PostMapping("/findUserByUsername")
    public ResultObject<User> findUserByUsername(String username) {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return ResponseResult.error("用户不存在");
        }
        return ResponseResult.success(user);
    }
}
