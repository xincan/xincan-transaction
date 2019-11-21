package com.xincan.transaction.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xincan.transaction.server.system.entity.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService extends IBaseService<Role> {

    IPage<Role> findRoles();

    List<Role> findRoleByUserId(String userId, Integer second);

    Integer testFeignTransaction();

}
