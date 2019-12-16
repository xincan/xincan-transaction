package com.xincan.transaction.server.demo.controller;

import com.xincan.transaction.result.ResponseResult;
import com.xincan.transaction.result.ResultObject;
import com.xincan.transaction.server.demo.entity.User;
import com.xincan.transaction.server.demo.interfaces.CreateValidation;
import com.xincan.transaction.server.demo.interfaces.UpdateValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.server.demo.controller
 * @ClassName: UserController
 * @author: wangmingshuai
 * @Description: 用户管理
 * @Date: 2019/12/12 14:03
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"用户管理"}, description = "UserController")
public class UserController {

    @ApiOperation(value="添加用户信息",httpMethod="POST",notes="根据参数列表添加用户信息")
    @PostMapping("/insert")
    public ResultObject<Object> insert (@Validated(CreateValidation.class) User user) {
        return ResponseResult.success(user);
    }

    @ApiOperation(value="修改用户信息",httpMethod="POST",notes="根据参数列表修改用户信息")
    @PostMapping("/update")
    public ResultObject<Object> update (@RequestBody @Validated(UpdateValidation.class) User user) {
        return ResponseResult.success(user);
    }

}
