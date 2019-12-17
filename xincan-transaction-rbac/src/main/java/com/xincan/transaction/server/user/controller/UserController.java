package com.xincan.transaction.server.user.controller;

import cn.com.hatech.common.data.result.ResultObject;
import com.xincan.transaction.result.ResponseResult;
import com.xincan.transaction.server.user.entity.User;
import com.xincan.transaction.server.user.service.IUserService;
import com.xincan.transaction.server.user.vo.UserLoginAccountTypeVo;
import groovy.util.logging.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
     * 根据用户登陆类型查询用户信息
     * @param userLoginAccountType
     * @return
     */
    @ApiOperation(value="根据登陆类型查询用户信息",httpMethod="GET",notes="根据登陆类型查询用户信息")
    @GetMapping("/findUserByLoginAccountType")
    public User insert(@ApiParam @Validated UserLoginAccountTypeVo userLoginAccountType) {
        return userService.findUserByLoginAccountType(userLoginAccountType);
    }
}
