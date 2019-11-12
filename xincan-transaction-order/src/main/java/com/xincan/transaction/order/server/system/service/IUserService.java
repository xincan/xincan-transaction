package com.xincan.transaction.order.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.order.server.system.entity.User;

import java.sql.SQLException;
import java.util.Map;

public interface IUserService extends IBaseService<User> {

    Page<User> findAll(String tenantName, Map<String, Object> map);

    void testInsert(String order_name) throws SQLException;
}
