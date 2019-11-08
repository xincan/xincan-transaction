package com.xincan.transaction.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xincan.transaction.server.system.entity.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    IPage<Role> findRoles();

    List<Role> findRoleByUserId(String userId, Integer second);
}
