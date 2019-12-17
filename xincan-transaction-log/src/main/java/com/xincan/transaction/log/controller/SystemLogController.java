package com.xincan.transaction.log.controller;

import com.xincan.transaction.log.entity.SystemLog;
import com.xincan.transaction.log.result.ResponseResult;
import com.xincan.transaction.log.result.ResultObject;
import com.xincan.transaction.log.service.ISystemLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xincan-transaction
 * @description: 系统操作日志控制层
 * @author: JiangXincan
 * @create: 2019/12/16 16:14
 * @version: 1.0
 */
@Api(value = "/test", tags = {"日志管理"})
@RestController
@RequestMapping("system")
public class SystemLogController {

    private ISystemLoginService systemLoginService;

    @Autowired
    public SystemLogController (ISystemLoginService systemLoginService) {
        this.systemLoginService = systemLoginService;
    }

    @ApiOperation(value="添加日志信息",httpMethod="GET",notes="根据参数添加日志信息")
    @GetMapping("/insert")
    public ResultObject<SystemLog> insert(@ApiParam @Validated SystemLog systemLog) {
        System.out.println(systemLog);
        Integer num = this.systemLoginService.insert(systemLog);
        if(num > 0) {
            return ResponseResult.success("添加日志信息成功", systemLog);
        }
        return ResponseResult.success("添加日志信息失败", new SystemLog());
    }

}
