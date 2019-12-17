package com.xincan.transaction.system.server.system.controller;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.xincan.transaction.system.server.system.service.ITenantService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@Api(tags = {"租户接口"}, description = "TenantController")
@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private ITenantService tenantService;

    @ApiOperation(value="租户注册",httpMethod="POST",notes="注册新租户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantName",value="租户名", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="tenantPassword",value="租户密码", required = true, dataType = "String",paramType = "query"),
    })
    @PostMapping("/registry")
    public ResultObject createTenant(@ApiParam(hidden = true) Principal principal,
                                     @RequestParam("tenantName") String tenantName,
                                     @RequestParam("tenantPassword") String tenantPassword) {
        if (StringUtils.isEmpty(tenantName) || StringUtils.isEmpty(tenantPassword)) {
            return ResultResponse.error("租户名或密码不能为空", null);
        }
        try {
            return tenantService.createTenantDatabase(tenantName, tenantPassword);
        }catch (Exception e) {
            log.error("租户数据库创建失败", e);
            return ResultResponse.error("租户数据库创建失败", null);
        }
    }

    /**
     * 注销租户
     * @param principal
     * @param tenantName
     * @return
     */
    @ApiOperation(value="租户注销",httpMethod="POST",notes="注销已创建的租户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="tenantName",value="租户名", required = true, dataType = "String",paramType = "query"),
    })
    @PostMapping("logoff")
    public ResultObject logoffTenant(@ApiParam(hidden = true) Principal principal,
                                     @RequestParam("tenantName") String tenantName) {
        if (StringUtils.isEmpty(tenantName)) {
            return ResultResponse.error("租户名不能为空", null);
        }
        try {
            return tenantService.deleteTenantDatabase(tenantName);
        } catch (Exception e) {
            log.error("租户注销失败", e);
            return ResultResponse.error("租户注销失败", null);
        }
    }

}
