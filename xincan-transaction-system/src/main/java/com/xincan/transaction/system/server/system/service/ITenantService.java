package com.xincan.transaction.system.server.system.service;

import cn.com.hatech.common.data.result.ResultObject;
import cn.com.hatech.common.data.universal.IBaseService;
import com.xincan.transaction.system.server.system.entity.tenant.Tenant;

import java.io.IOException;
import java.sql.SQLException;

public interface ITenantService extends IBaseService<Tenant> {

    int createTenant(String databaseName);

    int deleteTenant(String databaseName);

    int queryCountDataBase(String databaseName);

    void execSqlFile(String databaseName) throws IOException, SQLException;

    ResultObject createTenantDatabase(String databaseName, String tenantPassword) throws Exception;

    ResultObject deleteTenantDatabase(String databaseName) throws Exception;

}
