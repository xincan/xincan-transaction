package com.xincan.transaction.server.system.controller;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.result.ResultResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.Order;
import com.xincan.transaction.server.system.entity.User;
import com.xincan.transaction.server.system.service.IOrderService;
import com.xincan.transaction.server.system.service.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
@Api(tags = {"订单管理"}, description = "OrderController")
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private IUserService userService;

    /**
     * 测试
     * @return
     */
    @ApiOperation(value = "测试切换数据源插入租户", httpMethod = "GET", notes = "切换数据源插入租户")
    @ApiImplicitParams({
    })
    @GetMapping("/test")
    public ResultObject<String> testTransaction() throws SQLException {
        try {
            userService.testInsert();
            return ResultResponse.success("插入成功", "");
        }
        catch (Exception e) {
            log.error("插入失败", e);
        }
        return ResultResponse.error("插入失败");
    }

}
