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

    @Autowired
    private IOrderService orderService;

    @Autowired
    private DataSource dataSource;

    /**
     * 测试
     * @return
     */
    @GetMapping("/test")
    public ResultObject<String> testTransaction() throws SQLException {
        // 开启XA分布式事务
        TransactionTypeHolder.set(TransactionType.XA);
        Connection connection = null;
        try (HintManager hintManager = HintManager.getInstance(); ){
            connection = dataSource.getConnection();
            User user = new User(null, "测试1", 20, 0, new Date());
            Order order = new Order(null, null);
            // 设置不自动提交sql
            connection.setAutoCommit(false);
            hintManager.setDatabaseShardingValue("ds-user");
            userService.insert(user);
            hintManager.setDatabaseShardingValue("ds-order");
            orderService.insert(order);
            connection.commit();
            return ResultResponse.success("插入成功", "");
        }
        catch (Exception e) {
            e.printStackTrace();
            // 事务回滚
            if (connection != null) {connection.rollback();}
        }
        log.info("{}", "插入失败");
        return ResultResponse.error("插入失败");
    }

}
