package com.xincan.transaction.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xincan.transaction.server.system.entity.Role;

public interface IRoleService {

    IPage<Role> findRoles();
}
