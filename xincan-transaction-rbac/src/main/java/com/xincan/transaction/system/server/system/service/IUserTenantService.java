package com.xincan.transaction.system.server.system.service;

import cn.com.hatech.common.data.universal.IBaseService;
import com.xincan.transaction.system.server.system.entity.tenant.UserTenant;

public interface IUserTenantService extends IBaseService<UserTenant> {

    int saveUserTenant(String userId, String tenantId);
}
