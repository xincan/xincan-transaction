package com.xincan.transaction.order.server.system.controller;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.order.server.system.entity.TenantDatasource;
import com.xincan.transaction.order.server.system.entity.User;
import com.xincan.transaction.order.server.system.service.IOrderService;
import com.xincan.transaction.order.server.system.service.ITenantDatasourceService;
import com.xincan.transaction.order.server.system.service.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * @description: TODO
 * @className: OrderController
 * @date: 2019/10/30 16:24
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Slf4j
@Api(tags = {"订单管理"}, description = "OrderController")
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ITenantDatasourceService tenantDatasourceService;

    @ApiOperation(value = "查询租户数据源信息(obj)", httpMethod = "GET", notes = "根据查询条件分页查询租户数据源，返回对象集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="1", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="limit",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),

            @ApiImplicitParam(name="id",value="租户数据源ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="tenantName",value="租户名称", dataType = "String",paramType = "query"),
    })
    @PreAuthorize("hasRole('ROLE_TENANT')")
    @GetMapping("/selectDataSource/obj")
    public ResultObject findAllDataSource(Principal principal,
                                          @ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        try {
            Page<TenantDatasource> pageInfo = tenantDatasourceService.findAll(principal.getName(), map);
            log.info("{}", "查询租户数据源信息成功");
            return ResultResponse.success("查询租户数据源信息成功", pageInfo);
        } catch (Exception e) {
            log.info("查询租户数据源信息失败", e);
        }
        return ResultResponse.error("查询租户数据源信息失败");
    }

    @ApiOperation(value = "根据租户查询用户表信息(obj)", httpMethod = "GET", notes = "根据查询条件分页查询用户表信息，返回对象集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="1", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="limit",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称",defaultValue="create_time", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortOrder",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="startTime",value="开始时间", dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name="endTime",value="结束时间", dataType = "Date",paramType = "query"),

            @ApiImplicitParam(name="id",value="用户ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="真实名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value="用户性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="age",value="用户年龄", dataType = "Integer",paramType = "query")
    })
    @PreAuthorize("hasRole('ROLE_TENANT')")
    @GetMapping("/selectUser/obj")
    public ResultObject findAllUser(Principal principal,
            @ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        try {
            Page<User> pageInfo = this.userService.findAll(principal.getName(), map);
            log.info("{}", "查询用户信息成功");
            return ResultResponse.success("查询用户信息成功", pageInfo);
        } catch (Exception e) {
            log.info("查询用户信息失败", e);
        }
        return ResultResponse.error("查询用户信息失败");
    }


    @ApiOperation(value = "测试切换数据源插入租户", httpMethod = "GET", notes = "切换数据源插入租户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderName",value="订单名称", dataType = "String",paramType = "query")
    })
    @GetMapping("/test")
    public ResultObject<String> testTransaction(@ApiParam(hidden = true) @RequestParam(required = false)String orderName) {
        try {
            userService.testInsert(orderName);
            return ResultResponse.success("插入成功", "");
        }
        catch (Exception e) {
            log.error("插入失败", e);
        }
        return ResultResponse.error("插入失败");
    }

    @ApiOperation(value="测试分布式事务插入order",httpMethod="POST",notes="测试分布式事务插入order")
    @ApiImplicitParams({
    })
    @PostMapping("testFeignInsertOrder")
    public ResultObject testFeignInsertOrder() {
        try {
            Integer res = orderService.testFeignTransaction();
            return ResultResponse.success("order插入成功", res);
        }
        catch (Exception e) {
            log.error("order插入失败", e);
        }
        return ResultResponse.error("order插入失败");
    }

}
