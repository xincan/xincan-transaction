package com.xincan.transaction.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xincan.transaction.server.system.entity.User;

import java.sql.SQLException;
import java.util.Map;

public interface IUserService extends IBaseService<User> {

    void testInsert() throws SQLException;
}
