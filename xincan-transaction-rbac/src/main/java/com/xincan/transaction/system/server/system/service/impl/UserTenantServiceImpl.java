package com.xincan.transaction.system.server.system.service.impl;

import cn.com.hatech.common.data.universal.AbstractService;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xincan.transaction.system.server.system.entity.tenant.UserTenant;
import com.xincan.transaction.system.server.system.mapper.tenant.IUserTenantMapper;
import com.xincan.transaction.system.server.system.service.IUserTenantService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userTenantService")
public class UserTenantServiceImpl extends AbstractService<UserTenant> implements IUserTenantService {

    @Resource
    private IUserTenantMapper userTenantMapper;

    /**
     * 主数据库名称
     */
    @Value("${spring.datasource.name}")
    private String mainDataSource;

    @Override
    public int saveUserTenant(String userId, String tenantId) {
        try {
            HintManager.getInstance().setDatabaseShardingValue(mainDataSource);
            UserTenant userTenant = UserTenant.builder()
                    .userId(userId)
                    .tenantId(tenantId)
                    .build();
            return userTenantMapper.insert(userTenant);
        }
        finally {
            HintManager.clear();
        }
    }

}
