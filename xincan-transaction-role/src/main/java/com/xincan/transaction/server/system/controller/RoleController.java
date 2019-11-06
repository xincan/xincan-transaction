package com.xincan.transaction.server.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xincan.transaction.server.system.entity.Role;
import com.xincan.transaction.server.system.service.IRoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: TODO
 * @className: UserController
 * @date: 2019/10/30 16:24
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Api(tags = {"角色管理"}, description = "RoleController")
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value="查询角色信息",httpMethod="POST",notes="根据参数列表查询角色信息")
    @PostMapping(value = "list")
    public IPage<Role> findUsers(@ApiParam(hidden = true) Role role){

        System.out.println(role.toString());
        return this.roleService.findRoles();

    }



}
