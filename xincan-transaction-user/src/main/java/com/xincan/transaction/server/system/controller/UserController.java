package com.xincan.transaction.server.system.controller;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.User;
import com.xincan.transaction.server.system.feign.IFOrderService;
import com.xincan.transaction.server.system.feign.IFUserRoleService;
import com.xincan.transaction.server.system.service.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @className: UserController
 * @date: 2019/10/30 16:24
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Slf4j
@Api(tags = {"员工管理"}, description = "UserController")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFUserRoleService userRoleService;

    @Autowired
    private IFOrderService orderService;

    @ApiOperation(value="添加员工信息",httpMethod="POST",notes="根据参数列表添加员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="真实名称", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value="用户性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="age",value="用户年龄", dataType = "Integer",paramType = "query")
    })
    @PostMapping("/insert")
    public ResultObject<Object> insert(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        User user = JSON.parseObject(JSON.toJSONString(map), User.class);
        int num = this.userService.insert(user);
        if(num>0){
            log.info("{}", "添加员工信息成功");
            return ResultResponse.success("添加员工成功",user);
        }
        log.info("{}", "添加员工信息失败");

        return ResultResponse.error("添加员工失败");
    }

    @ApiOperation(value="修改员工信息",httpMethod="POST", notes="根据员工ID，修改参数列表中对应的员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="员工ID", dataType = "String", required = true,paramType = "query"),
            @ApiImplicitParam(name="name",value="真实名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value="用户性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="age",value="用户年龄", dataType = "Integer",paramType = "query")
    })
    @PostMapping("/update")
    public ResultObject<Object> update(@ApiParam(hidden = true) @RequestParam Map<String,Object> map){
        User user = JSON.parseObject(JSON.toJSONString(map), User.class);
        int num = this.userService.update(user);
        if(num>0){
            log.info("{}", "修改员工成功");
            return ResultResponse.success("修改员工成功", user);
        }
        log.info("{}", "修改员工失败");
        return ResultResponse.error("修改员工失败",user);
    }

    @ApiOperation(value="删除员工详细信息",httpMethod = "POST", notes="根据用户ID，删除员工详细信息（支持批量删除ID用','英文逗号隔开）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工ID(多个用英文、号隔开)", required = true, dataType = "String", paramType="query")
    })
    @PostMapping("/delete")
    public ResultObject<Object> delete(@RequestParam  String id) {
        int num = this.userService.deleteByIds(id);
        if(num > 0){
            log.info("{}", "删除员工成功");
            return  ResultResponse.success("删除员工成功", id);
        }
        log.info("{}", "删除员工失败");
        return ResultResponse.error("删除员工失败", id);
    }

    @ApiOperation(value = "查询员工信息(obj)", httpMethod = "GET", notes = "根据查询条件分页查询员工信息，返回对象集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="size",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortOrder",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="startTime",value="开始时间", dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name="endTime",value="结束时间", dataType = "Date",paramType = "query"),

            @ApiImplicitParam(name="id",value="用户ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="真实名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value="用户性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="age",value="用户年龄", dataType = "Integer",paramType = "query")
    })
    @GetMapping("/select/obj")
    public ResultObject findAll(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        Page<User> pageInfo = this.userService.findAll(map);
        if(pageInfo != null){
            log.info("{}", "查询员工信息成功");
            return ResultResponse.success("查询员工成功", pageInfo);
        }
        log.info("{}", "查询员工信息失败");
        return ResultResponse.error("查询员工失败");
    }

    @ApiOperation(value = "查询员工信息(map)", httpMethod = "GET", notes = "根据查询条件分页查询员工信息，返回list map集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="当前页数", defaultValue="0", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="size",value="每页条数", defaultValue="10", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="sortName",value="排序字段名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sortOrder",value="排序规则(ASC,DESC)，默认DESC", defaultValue = "DESC",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="startTime",value="开始时间", dataType = "Date",paramType = "query"),
            @ApiImplicitParam(name="endTime",value="结束时间", dataType = "Date",paramType = "query"),

            @ApiImplicitParam(name="id",value="用户ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value="真实名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value="用户性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="age",value="用户年龄", dataType = "Integer",paramType = "query")
    })
    @GetMapping("/select/map")
    public ResultObject findAllMap(@ApiParam(hidden = true) @RequestParam Map<String,Object> map) {
        Page<Map<String , Object>> pageInfo = this.userService.findAllMap(map);
        if(pageInfo != null){
            log.info("{}", "查询员工信息成功");
            return ResultResponse.success("查询员工成功", pageInfo);
        }
        log.info("{}", "查询员工信息失败");
        return ResultResponse.error("查询员工失败");
    }

    @ApiOperation(value = "查询员工详细信息", httpMethod = "GET", notes = "根据员工ID查询员工详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="员工ID", dataType = "String",paramType = "query")
    })
    @GetMapping("/select/id")
    public ResultObject<User> selectById(@ApiParam(hidden = true) @RequestParam String id) {
        User user = this.userService.selectById(id);
        if(user != null){
            log.info("{}", "查询员工信息成功");
            return ResultResponse.success("查询员工成功", user);
        }
        log.info("{}", "查询员工信息失败");
        return ResultResponse.error("查询员工失败");
    }


    @ApiOperation(value = "批量插入员工信息one", httpMethod = "GET", notes = "批量插入员工信息,第一种")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="真实名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value="用户性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="age",value="用户年龄", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="num",value="插入条数", dataType = "Integer",paramType = "query")
    })
    @GetMapping("/insert/batch/one")
    public ResultObject insertBatchOne(@ApiParam(hidden = true) @RequestParam Map<String, Object> map) {
        Integer num = this.userService.insertBatchOne(map);
        if(num > 0){
            log.info("{}", "批量插入员工信息成功");
            return ResultResponse.success("批量插入员工信息成功", num);
        }
        log.info("{}", "批量插入员工信息失败");
        return ResultResponse.error("批量插入员工信息失败");
    }

    @ApiOperation(value = "批量插入员工信息two", httpMethod = "GET", notes = "批量插入员工信息,第二种特别注意：mysql默认接受sql的大小是1048576(1M)（可通过调整MySQL安装目录下的my.ini文件中[mysqld]段的＂max_allowed_packet = 1M＂）")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="真实名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value="用户性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="age",value="用户年龄", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="num",value="插入条数", dataType = "Integer",paramType = "query")
    })
    @GetMapping("/insert/batch/two")
    public ResultObject insertBatchTwo(@ApiParam(hidden = true) @RequestParam Map<String, Object> map) {
        JSONObject result = this.userService.insertBatchTwo(map);
        if(result != null){
            log.info("{}", "批量插入员工信息成功");
            return ResultResponse.success("批量插入员工信息成功", result);
        }
        log.info("{}", "批量插入员工信息失败");
        return ResultResponse.error("批量插入员工信息失败");
    }


    @ApiOperation(value="根据用户ID查询角色信息",httpMethod="POST",notes="通过OpenFeign远程声明式事务，根据用户ID查询对应的角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户ID", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="second",value="openfeign超时秒数", dataType = "Integer",paramType = "query")
    })
    @PostMapping("id")
    public ResultObject findRoleByUserId(@RequestParam("userId") String userId, @RequestParam("second") Integer second){
        JSONObject result = this.userRoleService.findRoleByUserId(userId, second);
        if(!StringUtils.isEmpty(result)){

            if(result.getInteger("code") == 10000){
                log.info("{}", result.getString("msg"));
                return ResultResponse.error(result.getString("msg"), result.get("data"));
            }

            log.info("{}", "根据用户ID查询角色信息成功");
            return ResultResponse.success("根据用户ID查询角色信息成功", result.get("data"));
        }
        log.info("{}", "根据用户ID查询角色信息失败");
        return ResultResponse.error("根据用户ID查询角色信息失败");
    }


    @ApiOperation(value="上传用户头像",httpMethod="POST",notes="上传用户头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value="用户名称", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value="用户性别", dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name="age",value="用户年龄", dataType = "Integer",paramType = "query")
    })
    @PostMapping(value = "upload", headers = "content-type=multipart/form-data")
    public boolean uploadImage(@ApiParam(hidden = true) User user,
                                 @ApiParam(name = "attach",value = "上传文件", required = true) MultipartFile attach){
        System.out.println(user.toString());

        System.out.println(attach.getOriginalFilename());
        return true;

    }

    @ApiOperation(value="测试分布式事务",httpMethod="POST",notes="测试分布式事务")
    @ApiImplicitParams({
    })
    @GetMapping("testFeignTransaction")
    public ResultObject testFeignTransaction() {
        try {
            userService.testFeignTransaction();
            return ResultResponse.success("测试分布式事务成功");
        }
        catch (Exception e){
            log.error("测试分布式事务回滚",e);
            return ResultResponse.error("测试分布式事务回滚");
        }
    }


}
