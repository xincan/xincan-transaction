package com.xincan.transaction.server.system.controller;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xincan.transaction.server.system.entity.Role;
import com.xincan.transaction.server.system.service.IRoleService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @className: UserController
 * @date: 2019/10/30 16:24
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Slf4j
@Api(tags = {"角色管理"}, description = "RoleController")
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value="查询角色信息",httpMethod="POST",notes="根据参数列表查询角色信息")
    @PostMapping("list")
    public IPage<Role> findRoles(@ApiParam(hidden = true) Role role){

        System.out.println(role.toString());
        return this.roleService.findRoles();

    }

    private int num = 0;
    @ApiOperation(value="根据用户ID查询角色信息",httpMethod="POST",notes="根据参数列表查询角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户ID", dataType = "String",paramType = "query")
    })
    @PostMapping("user/id")
    public ResultObject findRoleByUserId(@RequestParam("userId") String userId, @RequestParam("second") Integer second){
        List<Role> roles = this.roleService.findRoleByUserId(userId, second);
        System.out.println("======================开启重试机制"+(num++)+"=============================");
        try {
            Thread.sleep(second);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(roles.size() > 0){
            log.info("{}", "根据用户ID查询角色信息成功");
            return ResultResponse.success("根据用户ID查询角色信息成功", roles);
        }
        log.info("{}", "根据用户ID查询角色信息失败");
        return ResultResponse.error("根据用户ID查询角色信息失败");
    }



}
