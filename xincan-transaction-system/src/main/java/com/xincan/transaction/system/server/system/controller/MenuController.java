package com.xincan.transaction.system.server.system.controller;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.system.server.system.entity.menu.Menu;
import com.xincan.transaction.system.server.system.entity.menu.TreeMenu;
import com.xincan.transaction.system.server.system.service.IMenuService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = {"菜单接口"}, description = "MenuController")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    IMenuService menuService;

    @ApiOperation(value="获取左侧菜单栏",httpMethod="GET",notes="获取左侧菜单栏")
    @ApiImplicitParams({

    })
    @GetMapping("/list")
    public ResultObject listMenu(@ApiParam(hidden = true) Principal principal) {
        List<Menu> menuList = menuService.listMenu();
        if (menuList!=null) {
            return ResultResponse.success("查询菜单成功", menuList);
        }
        return ResultResponse.error("查询菜单失败");
    }

    @ApiOperation(value="获取树形菜单栏",httpMethod="GET",notes="获取树形菜单栏(组装vue-treeSelect)")
    @ApiImplicitParams({

    })
    @GetMapping("/tree")
    public ResultObject treeMenu(@ApiParam(hidden = true) Principal principal) {
        List<TreeMenu> menuTree = menuService.treeMenu();
        if (menuTree!=null) {
            return ResultResponse.success("查询菜单成功", menuTree);
        }
        return ResultResponse.error("查询菜单失败");
    }

    @ApiOperation(value="查询菜单信息",httpMethod="GET",notes="查询分页菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="limit",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", defaultValue="create_time", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortType",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),

    })
    @GetMapping("/page")
    public ResultObject pageMenu(@ApiParam(hidden = true) Principal principal,
                                @ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        Page<Menu> pageInfo = menuService.getMenuPage(map);
        if(pageInfo != null){
            log.info("{}", "查询菜单信息成功");
            return ResultResponse.success("查询菜单信息成功", pageInfo);
        }
        log.info("查询菜单信息失败");
        return ResultResponse.error("查询菜单信息失败");
    }

    @ApiOperation(value="添加菜单",httpMethod="PUT",notes="添加菜单")
    @DynamicParameters(name = "Menu",properties = {
            @DynamicParameter(name="menuName",value="菜单名称", dataTypeClass=String.class),
            @DynamicParameter(name="parentId",value="父节点ID", dataTypeClass=String.class),
            @DynamicParameter(name="icon",value="菜单图标", dataTypeClass=String.class),
            @DynamicParameter(name="path",value="菜单路径", dataTypeClass=String.class),
            @DynamicParameter(name="params",value="菜单参数", dataTypeClass=String.class),
            @DynamicParameter(name="level",value="菜单级别", dataTypeClass=Integer.class),
            @DynamicParameter(name="sequence",value="菜单排序", dataTypeClass=Integer.class)
    })
    @PutMapping
    public ResultObject save(@ApiParam(hidden = true) Principal principal,
                             @RequestBody Map<String,String> map) {
        Menu menu = Menu.builder()
                .menuName(map.get("menuName"))
                .icon(map.get("icon"))
                .path(map.get("path"))
                .params(StringUtils.isEmpty(map.get("params"))?"\"{}\"":map.get("params"))
                .parentId(StringUtils.isEmpty(map.get("parentId"))?-1:Integer.parseInt(map.get("parentId")))
                .level(StringUtils.isEmpty(map.get("level"))?null:Integer.parseInt(map.get("level")))
                .sequence(StringUtils.isEmpty(map.get("sequence"))?null:Integer.parseInt(map.get("sequence")))
                .build();
        Integer res = menuService.save(menu);
        if (res != null && res > 0) {
            return ResultResponse.success("添加菜单成功");
        }
        return ResultResponse.error("添加菜单失败");
    }

    @ApiOperation(value="更新菜单",httpMethod="PATCH",notes="更新菜单")
    @DynamicParameters(name = "Menu",properties = {
            @DynamicParameter(name="id",value="菜单ID", dataTypeClass=String.class),
            @DynamicParameter(name="menuName",value="菜单名称", dataTypeClass=String.class),
            @DynamicParameter(name="parentId",value="父节点ID", dataTypeClass=String.class),
            @DynamicParameter(name="icon",value="菜单图标", dataTypeClass=String.class),
            @DynamicParameter(name="path",value="菜单路径", dataTypeClass=String.class),
            @DynamicParameter(name="params",value="菜单参数", dataTypeClass=String.class),
            @DynamicParameter(name="level",value="菜单级别", dataTypeClass=Integer.class),
            @DynamicParameter(name="sequence",value="菜单排序", dataTypeClass=Integer.class)
    })
    @PatchMapping
    public ResultObject update(@ApiParam(hidden = true) Principal principal,
                               @RequestBody Map<String,String> map) {
        if (StringUtils.isEmpty(map.get("id"))) {
            return ResultResponse.error("菜单ID不存在");
        }
        Menu menu = Menu.builder()
                .id(StringUtils.isEmpty(map.get("id"))?null:Integer.parseInt(map.get("id")))
                .menuName(map.get("menuName"))
                .icon(map.get("icon"))
                .path(map.get("path"))
                .params(map.get("params"))
                .parentId(StringUtils.isEmpty(map.get("parentId"))?-1:Integer.parseInt(map.get("parentId")))
                .level(StringUtils.isEmpty(map.get("level"))?null:Integer.parseInt(map.get("level")))
                .sequence(StringUtils.isEmpty(map.get("sequence"))?null:Integer.parseInt(map.get("sequence")))
                .build();
        int res = menuService.update(menu);
        if (res > 0) {
            return ResultResponse.success("添加菜单成功");
        }
        return ResultResponse.error("添加菜单失败");
    }

    @ApiOperation(value="删除菜单",httpMethod="POST",notes="删除菜单")
    @DynamicParameters(name = "Menu",properties = {
            @DynamicParameter(name="id",value="菜单ID(逗号分隔)", dataTypeClass=String.class),
    })
    @PostMapping("/delete")
    public ResultObject delete(@ApiParam(hidden = true) Principal principal,
                               @RequestBody Map<String,String> map) {
        String id = map.get("id");
        if (StringUtils.isEmpty(id)) {
            return ResultResponse.error("菜单ID为空");
        }
        int res = menuService.deleteByIds(id);
        if (res > 0) {
            return ResultResponse.success("删除菜单成功");
        }
        return ResultResponse.error("删除菜单失败");
    }


}
