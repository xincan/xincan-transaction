package com.xincan.transaction.order.server.system.service.impl;

import cn.com.hatech.common.data.page.MybatisPage;
import cn.com.hatech.common.data.result.ResultResponse;
import cn.com.hatech.common.data.universal.AbstractService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.order.server.system.entity.Order;
import com.xincan.transaction.order.server.system.entity.User;
import com.xincan.transaction.order.server.system.mapper.IOrderMapper;
import com.xincan.transaction.order.server.system.mapper.ITenantDatasourceMapper;
import com.xincan.transaction.order.server.system.mapper.IUserMapper;
import com.xincan.transaction.order.server.system.service.IOrderService;
import com.xincan.transaction.order.server.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @description: TODO
 * @className: UserServiceImpl
 * @date: 2019/10/30 16:34
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends AbstractService<User> implements IUserService {

    /**
     * 订单Mapper
     */
    @Resource
    private IOrderMapper orderMapper;

    /**
     * 用户Mapper
     */
    @Resource
    private IUserMapper userMapper;

    /**
     * sharding-jdbc数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 查询不同租户用户表信息
     * @param tenantName
     * @param map
     * @return
     */
    @Override
    public Page<User> findAll(String tenantName, Map<String, Object> map) {
        Page<User> page = MybatisPage.getPageSize(map);
        try {
            HintManager.getInstance().setDatabaseShardingValue(tenantName);
            List<User> userList = this.userMapper.findAll(page, map);
            page.setRecords(userList);
        } finally {
            HintManager.clear();
        }
        return page;
    }

    /**
     * 测试分别插入数据到user表和order表
     * @throws SQLException
     */
    public void testInsert(String orderName) throws SQLException {
        // 开启XA分布式事务
        TransactionTypeHolder.set(TransactionType.XA);
        Connection connection = null;
        try (HintManager hintManager = HintManager.getInstance(); ){
            connection = dataSource.getConnection();
            User user = new User(null, "测试1", 20, 0, new Date());
            Order order = new Order(null, StringUtils.isEmpty(orderName)?null:orderName);
            // 设置不自动提交sql
            connection.setAutoCommit(false);
            // 租户 tenant0 对应数据表user
            hintManager.setDatabaseShardingValue("tenant0");
            // 插入user表
            userMapper.insert(user);
            // 租户 tenant2 对应数据表order
            hintManager.setDatabaseShardingValue("tenant2");
            // 插入order表
            orderMapper.insert(order);
            connection.commit();
        } catch (Exception e) {
            // 事务回滚
            if (connection != null) {connection.rollback();}
            throw e;
        } finally {
            HintManager.clear();
        }

    }

}